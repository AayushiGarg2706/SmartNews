package com.logischtech.smartnews.ApiControllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.logischtech.smartnews.ArticleDetail;
import com.logischtech.smartnews.Dashboard;
import com.logischtech.smartnews.Helpers.InternalStorage;
import com.logischtech.smartnews.QRRequest;
import com.logischtech.smartnews.Secret_News;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import Models.User;

/**
 * Created by Aayushi.Garg on 14-11-2017.
 */

public class HttpQrrequest extends AsyncTask<Object,Object,String> {
    private ProgressDialog progress = null;
    private Context ApplicationContext = null;
    private Activity currentScreen = null;


    public HttpQrrequest(Activity screen, Context appContext)
    {

        progress = new ProgressDialog(screen);
        ApplicationContext = appContext;
        currentScreen = screen;
    }


    @Override
    protected String doInBackground(Object... params) {
        try{
            Object fromStorage = null;

            fromStorage = InternalStorage.readObject(ApplicationContext,"User");
            User u = (User) fromStorage;





            Long userid= u.Id;
            Object castingid = params[1];
            //  http://52.187.186.166/smartnews/api/SmartNewApi/ConnectToCastingConnection?userId=1&castingId=7dd73bb3-d3a2-41a3-baf7-854f61c873a8
            String url = "http://52.187.186.166/smartnews/api/SmartNewApi/ConnectToCastingConnection?" + "userId=" + userid + "&" + "castingId="  + castingid ;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setErrorHandler(new CustomResponseErrorHandler());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            String response = restTemplate.getForObject(url, String.class);
            return response.toString();

        }

        catch (Exception ex){
            this.progress.setMessage("");

        }
        return null;    }

    @Override
    protected void onPreExecute() {
        this.progress.setMessage("");
        this.progress.show();

    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        if (progress.isShowing()) {
            progress.dismiss();
        }
        if (string == "true"){



//            Intent gotodashboard = new Intent(currentScreen,ArticleDetail.class);
//            currentScreen.startActivity(gotodashboard);
                 currentScreen.finish();
        }
        else {

            new AlertDialog.Builder(currentScreen)

                    .setTitle("Error")
                    .setMessage("Something goes wrong . Please try again . ")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            currentScreen.finish();                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


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
