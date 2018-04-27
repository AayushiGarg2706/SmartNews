package com.logischtech.smartnews;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.logischtech.smartnews.Helpers.InternalStorage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    Object fromStorage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            fromStorage= InternalStorage.readObject(getApplicationContext(), "User");
           } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        if (fromStorage != null){
            Intent in = new Intent(getApplicationContext(),Dashboard.class);
            startActivity(in);

        }
        else{


            Intent mainIntent = new Intent(getApplicationContext(), Login.class);
            startActivity(mainIntent);



        }




    }
}
