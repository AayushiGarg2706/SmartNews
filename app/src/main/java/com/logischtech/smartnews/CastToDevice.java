package com.logischtech.smartnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.logischtech.smartnews.Helpers.InternalStorage;
import com.nostra13.universalimageloader.utils.L;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import Models.GetArticleScan;
import Models.User;

public class CastToDevice extends AppCompatActivity {
    static int color=1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_to_device);

       Object  fromStorage = null;
        try{

          //  fromStorage = InternalStorage.readObject(this,"GetArticleScan");
            fromStorage = InternalStorage.readObject(getApplicationContext(),"User");
            User u = (User) fromStorage;
            String articlekey =  "a9088f26-fc63-4b43-93ce-5446cdd4f14d";
            Long userid = u.Id;
                    String language = "1";
            new HttpCastDeviceRequest().execute(userid,articlekey,language);


        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        GetArticleScan result  = (GetArticleScan) fromStorage;
//          String articlekeys = result.ArticleKey;



    }


    private class HttpCastDeviceRequest extends AsyncTask<Object,Object,String> {
        public ProgressDialog progress = new ProgressDialog(CastToDevice.this);

        @Override
        protected String doInBackground(Object... params) {
            try{
                Long userid = (Long) params[0];
                Object articlekey = params[1];
                String language = "1";


                //http://52.187.186.166/smartnews/api/SmartNewApi/CastArticle?userId=1&articleKey=002311FB-8682-488C-B6FA-59662D866E5D&language=1       String url = "http://52.187.186.166/smartnews/api/SmartNewApi/ConnectToCastingConnection?" + "userId=" + userid + "&" + "castingId="  + castingid ;
               String url = "http://52.187.186.166/smartnews/api/SmartNewApi/CastArticle?" + "userId=" + userid + "&" + "articleKey=" + articlekey + "&" + "language=" + language;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setErrorHandler(new CustomResponseErrorHandler());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                String response = restTemplate.getForObject(url, String.class);
             //   InternalStorage.writeObject(CastToDevice.this,"String",response);
                return response.toString();


            }

            catch (Exception ex){
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
        protected void onPostExecute(String string){
            super.onPostExecute(string);
            if (progress.isShowing()) {
                progress.dismiss();
            }
            Intent gotodesc = new Intent(CastToDevice.this,ArticleDescription.class);

             gotodesc.putExtra("color", color);
            startActivity(gotodesc);


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
}
