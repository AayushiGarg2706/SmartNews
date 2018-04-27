package com.logischtech.smartnews.ApiControllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.logischtech.smartnews.Dashboard;
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

import Models.SocialLoginType;
import Models.User;

/**
 * Created by Aayushi.Garg on 21-09-2017.
 */

public class HttpSocailLogins extends AsyncTask<Object,String, User> {
    private ProgressDialog progress = null;
    private Context ApplicationContext = null;
    private Activity currentScreen = null;

    public HttpSocailLogins(Activity screen, Context appContext)
    {

        progress = new ProgressDialog(screen);
        ApplicationContext = appContext;
        currentScreen = screen;
    }



    @Override
    protected User doInBackground(Object... params) {

        try{
            int id = (int) params[0];
            String PartnerId = (String) params[1];
            String name = (String) params[2];
            String Email = (String)params[3];
            User user = new User();
            user.LoginType = (SocialLoginType) params[4];



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

                InternalStorage.writeObject(ApplicationContext, "User", response);

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

    @Override
    protected void onPreExecute() {
        this.progress.setMessage("Please Wait");
        this.progress.show();
        this.progress.setCancelable(false);

    }

    @Override
    protected void onPostExecute(User users) {
        super.onPostExecute(users);
        if (progress.isShowing()) {
            progress.dismiss();





        }
        Intent intent = new Intent(currentScreen, Dashboard.class);
        currentScreen.startActivity(intent);




    }

}

