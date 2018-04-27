package com.logischtech.smartnews;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;

public class Secret_News extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SELECTED_PICTURE = 1;

    ImageView vi;
    private Button btn;
    private ImageView imageview;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2, Videos =3;
    ImageLoader imageLoader;
    Handler handler;
    ViewSwitcher viewSwitcher;
    GridView gridGallery;
    GridView gridGallery1;
    GalleryAdapter adapter;
    private Button btngallery;
    TextView imgtext1;
    ImageView imgclose1;
    TextView videotxt;
    ImageView videoimg;
    final int RequestWritePermissionID = 1001;

    private   EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret__news);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.frame1);
        relativeLayout.bringToFront();
      //   vi = (ImageView)findViewById(R.id.selectedimg);
        imageview = (ImageView) findViewById(R.id.iv);
        imgtext1 = (TextView)findViewById(R.id.imgtext1);
        imgclose1 = (ImageView)findViewById(R.id.close1);
        imgclose1.setVisibility(View.GONE);
        imgtext1.setVisibility(View.GONE);
        imageview.setVisibility(View.GONE);
         videotxt = (TextView)findViewById(R.id.videotxt);
        videotxt.setVisibility(View.GONE);
        videoimg=(ImageView)findViewById(R.id.videoimg);
        videoimg.setVisibility(View.GONE);
        ImageView  btncollapse = (ImageView)findViewById(R.id.secret_upload_menu);

        btncollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawable = (DrawerLayout) findViewById(R.id.activity_secret__news);
                if (drawable.isDrawerOpen(GravityCompat.START)) {

                    //  btncollapse.setImageResource(R.drawable.menubar);
                    drawable.closeDrawer(GravityCompat.START);
                } else {
                    //  btncollapse.setImageResource(R.drawable.navclick);

                    drawable.openDrawer(GravityCompat.START);
                }

            }
        });
        setNavigationViewListner();




        title = (EditText)findViewById(R.id.sharetitle);
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(title, InputMethodManager.SHOW_FORCED);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   displayKeyboard();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

            }
        });

        title.setFocusableInTouchMode(true);
        EditText text = (EditText)findViewById(R.id.ed_type);





        initImageLoader();
        init();



   }

    private void setNavigationViewListner() {
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_drawersecret) ;
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case RequestWritePermissionID : {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Intent galleryIntent = new Intent(Action.ACTION_MULTIPLE_PICK);
////
                    startActivityForResult(galleryIntent, GALLERY);
                }
            }

        }
    }


    private void displayKeyboard() {
        if (title != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInputFromWindow(title.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
    private void init() {
        handler = new Handler();
        gridGallery = (GridView) findViewById(R.id.gridGallery);
        gridGallery.setFastScrollEnabled(true);

       // gridGallery1 = (GridView) findViewById(R.id.gridGallery1);
      //  gridGallery1.setFastScrollEnabled(true);

        adapter = new GalleryAdapter(getApplicationContext(), imageLoader);
        adapter.setMultiplePick(false);
        gridGallery.setAdapter(adapter);
      //  gridGallery1.setAdapter(adapter);

        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
      //  viewSwitcher1 = (ViewSwitcher) findViewById(R.id.viewSwitcher1);


        viewSwitcher.setDisplayedChild(1);
     //   viewSwitcher1.setDisplayedChild(1);
        btngallery =  (Button)findViewById(R.id.btngallery);

        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        gridGallery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }

        });






    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera",
                "Select video from gallery" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case  2 :
                                choosevideoFromGallery();

                        }
                    }
                });
        pictureDialog.show();
    }



    public void choosePhotoFromGallary() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(Secret_News.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                return;
            }
   Intent galleryIntent = new Intent(Action.ACTION_MULTIPLE_PICK);
////
   startActivityForResult(galleryIntent, GALLERY);

        }
        catch (Exception e){

            e.printStackTrace();
        }



        }




    public void choosevideoFromGallery(){
        Intent in = new Intent(Intent.ACTION_PICK);
        in.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(in,Videos);

    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            String[] all_path = data.getStringArrayExtra("all_path");

            ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

            for (String string : all_path) {
                CustomGallery item = new CustomGallery();
                item.sdcardPath = string;


//                TextView imgtext = (TextView)findViewById(R.id.imgtext);
//                imgtext.setText(picnme);





                dataT.add(item);

            }

            viewSwitcher.setDisplayedChild(0);
            adapter.addAll(dataT);


        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

            imageview.setImageBitmap(thumbnail);
            imageview.setVisibility(View.VISIBLE);
            imgtext1.setVisibility(View.VISIBLE);
            imgclose1.setVisibility(View.VISIBLE);
            saveImage(thumbnail);
            Toast.makeText(Secret_News.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

        else if(requestCode == Videos){


                Uri selectedVideo = data.getData();
                String s = getRealPathFromURI(selectedVideo);
                String result = s.substring(s.lastIndexOf('/') + 1).trim();
               // adapter.add(result);

                videotxt.setVisibility(View.VISIBLE);
                videotxt.setText(result);
                videoimg.setVisibility(View.VISIBLE);
//
// String[] all_path = data.getStringArrayExtra("all_path");

//            String[] all_path = data.getStringArrayExtra("all_path");
//
//            ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();
//
//            for (String string : all_path) {
//                CustomGallery item = new CustomGallery();
//                item.sdcardPath = string;
//
//                dataT.add(item);
//            }
//
//            viewSwitcher1.setDisplayedChild(0);
//            adapter.addAll(dataT);










        }








    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.connectcastdv:
                Intent i = new Intent(Secret_News.this, QRcodescanner.class);
                startActivity(i);
                break;
            case R.id.scanarticle:
                Intent in = new Intent(Secret_News.this, ArticleScanner.class);
                startActivity(in);
                break;

            case R.id.scanadd:

                Intent inn = new Intent(Secret_News.this, ScanAdd.class);
                startActivity(inn);
                break;


            case R.id.dash :

                Intent ind = new Intent(Secret_News.this, Dashboard.class);
                startActivity(ind);
                break;






        }






        return false;
    }
}
