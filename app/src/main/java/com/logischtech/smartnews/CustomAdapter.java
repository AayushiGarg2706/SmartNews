package com.logischtech.smartnews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.logischtech.smartnews.Helpers.InternalStorage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Models.DashboardArticle;
import Models.User;

import static com.logischtech.smartnews.InternetStatus.context;

/**
 * Created by Aayushi.Garg on 06-11-2017.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

  // private ArrayList<Arraylist> dataSet;
  private Activity activity;
    private OnRecyclerViewItemClickListener listener;
   // private Context mcontext;
    private Context ApplicationContext = null;

    private DashboardArticle[] data;
     public DashboardArticle arti;
    Object fromStorage = null;
    private int pos;
    private static LayoutInflater inflater=null;



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        ImageView imageViewIcon;
        RelativeLayout articlelayout;
        ProgressBar p1;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.articlelayout=(RelativeLayout)itemView.findViewById(R.id.articleclick);
            this.textViewName = (TextView) itemView.findViewById(R.id.tv);
            //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.topart);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){




                    }


                }
            });


        }
    }

    public CustomAdapter(Activity a, Context appContext, DashboardArticle[] dashboardArticles) {

//        this.dataSet = data;

        activity = a ;
        ApplicationContext = appContext;
          this.data = dashboardArticles ;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article1item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final TextView textViewName = holder.textViewName;
        // TextView textViewVersion = holder.textViewVersion;
        ImageView imageViewIcon = holder.imageViewIcon;
         arti = new DashboardArticle();
        arti = data[listPosition];
        textViewName.setText(arti.getTitle());




        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int j = 0;j<=listPosition;j++){
                    String url = data[j].getUrl();
                    Intent i = new Intent(activity, ArticleDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);

                    i.putExtras(bundle);

                    activity.startActivity(i);

                }



//                if(listPosition == 0)
//
//                {
//                    String url = data[0].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//
//                }
//                else if(listPosition==1){
//                    String url = data[1].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//                }
//                else if(listPosition == 2){
//                    String url = data[2].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//                }
//                else if(listPosition == 3){
//                    String url = data[3].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//                }
//                else if(listPosition==4){
//                    String url = data[4].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//                }
//                else if(listPosition == 5){
//                    String url = data[5].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//                }
//                else if(listPosition == 6){
//                    String url = data[6].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//
//                }
//                else if(listPosition == 7){
//                    String url = data[7].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//
//                }
//                else if(listPosition == 8){
//                    String url = data[8].getUrl();
//                    Intent i = new Intent(activity, ArticleDetail.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", url);
//
//                    i.putExtras(bundle);
//
//                    activity.startActivity(i);
//
//
//                }
//
//
//
//
//




            }
        });

        //  imageView.setImageBitmap(getBitmapfromUrl(arti.getUrlToImage()));
        String img = arti.getUrlToImage();
      //  imageViewIcon.setImageBitmap(getBitmapfromUrl(img));
        BitmapWorkerTask task = new BitmapWorkerTask(imageViewIcon);
        task.execute(arti.getUrlToImage());


    }


    @Override
    public int getItemCount() {
        return data.length;
    }

    private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private ProgressDialog progress = new ProgressDialog(activity);

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
        @Override
        protected void onPreExecute(){
//            this.progress.setMessage("Please Wait");
//            this.progress.show();
//            this.progress.setCancelable(false);



        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
//            if (progress.isShowing()) {
//                progress.dismiss();
//
//            }



            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }



        }
    }
}
