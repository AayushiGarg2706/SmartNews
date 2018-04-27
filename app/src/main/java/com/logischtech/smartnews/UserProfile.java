package com.logischtech.smartnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.logischtech.smartnews.Helpers.InternalStorage;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

import Models.SocialLoginType;
import Models.User;

public class UserProfile extends AppCompatActivity {
    private ShareDialog shareDialog;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shareDialog = new ShareDialog(this);
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder().build();
                shareDialog.show(shareLinkContent);
            }
        });
        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String PicUrl =getIntent().getStringExtra("imageUrl");
        String PartnerId  = getIntent().getStringExtra("id");
        String Email = "aaushi007@gmail.com";
        String PhoneNumber= "";
        SocialLoginType socialLoginType = SocialLoginType.Facebook;

        int Id = 0;
        new HttpFBlogin().execute(Id,PartnerId,name,Email,socialLoginType,PicUrl ,PhoneNumber);




//        Bundle bundle = getIntent().getExtras();
//        String name = bundle.get("name").toString();
//        String surname = bundle.get("surname").toString();
//        String imageUrl = bundle.get("imageUrl").toString();
//      TextView nameView = (TextView)findViewById(R.id.nameAndsurname);
//        TextView ids = (TextView)findViewById(R.id.fb_id) ;
//        ids.setText(id);
//
//        nameView.setText("" + name + " " + surname);


         logout  =(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent login = new Intent(UserProfile.this,Login.class);
                startActivity(login);
                finish();

            }
        });


     //   new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);


    }

//    public class DownloadImage extends AsyncTask<String,Void,Bitmap> {
//        ImageView bmImage;
//        public DownloadImage(ImageView bmImage) {
//            this.bmImage = bmImage;
//
//        }
//
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            String urlDisplay = params[0];
//            Bitmap mIcon11 = null;
//            try{
//
//                InputStream input = new java.net.URL(urlDisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(input);
//
//              }
//             catch (Exception ex){
//                 Log.e("Error", ex.getMessage());
//                 ex.printStackTrace();
//
//
//                }
//            return mIcon11;
//
//
//        }
//
//        protected void onPostExecute(Bitmap result){
//            bmImage.setImageBitmap(result);
//
//        }
//    }

    private class HttpFBlogin extends AsyncTask<Object,String,User>   {
        private ProgressDialog progress = new ProgressDialog(UserProfile.this);

        @Override
        protected User doInBackground(Object... params) {
            try{
                int id = (int) params[0];
                String PartnerId = (String) params[1];
                String name = (String) params[2];
                String Email = (String)params[3];
                User user = new User();
                user.LoginType = SocialLoginType.Facebook;



              //  String user.LoginType = (String) params[4];
                String PicUrl =(String)params[5];
                String PhoneNumber = (String)params[6];
                String url = "http://52.187.186.166/smartnews/api/SmartNewApi/Login";

                RestTemplate restTemplate = new RestTemplate();
                HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
                HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
                JSONObject request = new JSONObject();
                request.put("id",id);
                request.put("PartnerId",PartnerId);
                request.put("name",name);
                request.put("Email",Email);
                request.put("LoginType",user.LoginType);

                request.put("PicUrl ",PicUrl);
                request.put("PhoneNumber",PhoneNumber);
              //  request.put("url",url);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
                restTemplate.getMessageConverters().add(formHttpMessageConverter);
                restTemplate.getMessageConverters().add(stringHttpMessageConverternew);
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                try {
                    User response = restTemplate.postForObject(url, entity, User.class);

                    InternalStorage.writeObject(UserProfile.this, "User", response);

                    return response;





                } catch (Exception ex) {
                    return null;





                }












            }
            catch (Exception ex){
                this.progress.setMessage("");


            }
            return null;
        }
    }
}
