package com.logischtech.smartnews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Aayushi.Garg on 23-10-2017.
 */

public class InternetStatus {
    private static InternetStatus instance = new InternetStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;


    public static InternetStatus getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
//            connectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//            WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
//
//            connected = networkInfo != null  &&
//                    networkInfo.isConnected() ;
//
//           return connected;
            connectivityManager = (ConnectivityManager) context
                   .getSystemService(Context.CONNECTIVITY_SERVICE);
//
            // Check for network connections
            if ( connectivityManager.getNetworkInfo(0).getState() ==
                    NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(0).getState() ==
                            NetworkInfo.State.CONNECTING ||
                    connectivityManager.getNetworkInfo(1).getState() ==
                            NetworkInfo.State.CONNECTING ||
                    connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
                return true;
            }
            else   if (
                    connectivityManager.getNetworkInfo(0).getState() ==
                            NetworkInfo.State.DISCONNECTED ||
                            connectivityManager.getNetworkInfo(1).getState() ==
                                    NetworkInfo.State.DISCONNECTED  ) {


                return false;
            }

            return true;




        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());

           return false;
        }
       // return false;
    }
}