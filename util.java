package com.example.root.jsonusingvolley;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by root on 15/1/18.
 */

public class util {
    private static NetworkInfo networkInfo;
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try{
            networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected()){
            return true;
        }
        networkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
