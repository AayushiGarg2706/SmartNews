package com.logischtech.smartnews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;


import Models.Article;

import Models.ArticleAdapter;
import Models.SearchArticleResponse;

public class ArticleDescription extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView arTitle,arDesc;
    ImageView arImage ;
     public Bitmap bmp = null;
    ImageView btncastdevice ;
    private Activity articledescription;

    int color2;
  public ProgressDialog progressDialog;
    ImageView btncollapse ;
    private DrawerLayout drawable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_description);
        RelativeLayout actionbar=(RelativeLayout) findViewById(R.id.dashframe1);
        actionbar.bringToFront();
       articledescription = this;

        arTitle = (TextView)findViewById(R.id.artitle);
        arDesc = (TextView)findViewById(R.id.articledesc);
        arImage = (ImageView)findViewById(R.id.articleimg);

        //arDesc = (TextView)findViewById(R.id.ardescription);
        final String Title = getIntent().getStringExtra("Title");
        String Language = "1";
        new HttpArticleScanRequest().execute(Title,Language);
        color2= getIntent().getIntExtra("color", color2);
        if (color2 == 1){
           // btncastdevice.setImageResource(R.drawable.on);
          //  btncastdevice.setEnabled(false);
        }
        else {
          //  btncastdevice.setImageResource(R.drawable.off);
        }


        btncollapse = (ImageView)findViewById(R.id.btncollapse1);
        btncollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawable = (DrawerLayout) findViewById(R.id.activity_article_description);
                if (drawable.isDrawerOpen(GravityCompat.START)) {

                    //  btncollapse.setImageResource(R.drawable.menubar);
                    drawable.closeDrawer(GravityCompat.START);
                } else {
                    //  btncollapse.setImageResource(R.drawable.navclick);

                    drawable.openDrawer(GravityCompat.START);
                }

            }
        });








//        btncastdevice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(v == btncastdevice ){
//                    btncastdevice.setImageResource(R.drawable.on);
//                }
//
//
//
//                Intent gotocast = new Intent(ArticleDescription.this,CastToDevice.class);
//                startActivity(gotocast);
//
//            }
//        });
        setNavigationViewListner();






    }




    private void setNavigationViewListner() {
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_drawer2) ;
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            finish();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.dashboard :
                Intent i = new Intent(ArticleDescription.this,Dashboard.class);
                startActivity(i);

                break;
            case R.id.connectcastdv :
                Intent ic = new Intent(ArticleDescription.this,QRcodescanner.class);
                startActivity(ic);
                break;
            case R.id.scanarticle :
                Intent ins = new Intent(ArticleDescription.this,ArticleScanner.class);
                startActivity(ins);
                break;

            case R.id.scanadd:

                Intent inn = new Intent(ArticleDescription.this,ScanAdd.class);
                startActivity(inn);
                break;



            case R.id.secret :
                Intent secret = new Intent(ArticleDescription.this,Secret_News_Choice.class);
                startActivity(secret);
                break;

        }



        return false;
    }

//    @Override
//    protected void onStop() {
//
//        super.onStop();
//
////        if (progressDialog != null) {
////            progressDialog.dismiss();
////            progressDialog = null;
////        }
//
//    }
//

    private class HttpArticleScanRequest extends AsyncTask<Object, Object,SearchArticleResponse > {
        private ProgressDialog progress = new ProgressDialog(ArticleDescription.this
        );

        @Override
        protected SearchArticleResponse doInBackground(Object... params) {
            try {
                Object Title = params[0];
                Object Language = params[1];
                // String url = "http://52.187.186.166/smartnews/api/SmartNewApi/GetArticleByTitle?title=test&language=1";
                String url = "http://52.187.186.166/smartnews/api/SmartNewApi/GetArticleByTitle?" + "title=" + Title + "&" + "language=" + Language;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                SearchArticleResponse apiResponse = restTemplate.getForObject(url, SearchArticleResponse.class);
                return apiResponse;
            }
            catch (Exception ex) {

                this.progress.setMessage("");

            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            this.progress.setMessage("Please Wait");
            this.progress.show();
        }

        @Override
        protected void onPostExecute(SearchArticleResponse getArticleScan) {
            super.onPostExecute(getArticleScan);
            if (progress.isShowing()) {
                progress.dismiss();
            }


            arDesc.setText(getArticleScan.getArticle().Description);
            arTitle.setText(getArticleScan.getArticle().Title);


            BitmapWorkerTask task = new BitmapWorkerTask(arImage);
            task.execute(getArticleScan.getArticle().UrlToImage);

            ListView relaednews = (ListView)findViewById(R.id.relatednews);

            Utility.setListViewHeightBasedOnChildren(relaednews);
            ArticleAdapter adapters=new ArticleAdapter(articledescription, getArticleScan.RelevantNews);
            relaednews.setAdapter(adapters);






        }
    }

    private class CustomResponseErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {

        }
    }

    private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String data;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            data = params[0];
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(data)
                        .getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
