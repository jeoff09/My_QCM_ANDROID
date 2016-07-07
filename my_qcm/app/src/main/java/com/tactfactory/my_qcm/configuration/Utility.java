package com.tactfactory.my_qcm.configuration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jeoffrey on 05/07/2016.
 */
public class Utility {

    static public boolean CheckInternetConnection(Context context) {
        //Get connectivity manager object to check connection
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check for network connection
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
