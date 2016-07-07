package com.tactfactory.my_qcm.data.webservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.Header;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jeoffrey on 19/05/2016.
 */
public class ConnectionWSAdapter {

    String response;
    MyQCMConstants myQCMConstants;

     public void ConnectionRequest (String url,String username, String password, final CallBack callback){
        AsyncHttpClient asyncHttpClient = new  AsyncHttpClient();
         System.out.println("login = " +username+
                 "pwd  = " + password);
         asyncHttpClient.setConnectTimeout(60000);
         asyncHttpClient.setTimeout(600000);
        RequestParams params = new  RequestParams();
        params.put(myQCMConstants.CONST_VALUE_LOGIN, username);
        params.put(myQCMConstants.CONST_VALUE_PWD, password);
        asyncHttpClient.post(url + ".json",params, new TextHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                callback.methods(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                callback.methods(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                response ="false";
                callback.methods(response);
            }
        });
    }

    public interface CallBack{
         void methods(String reponse);
    }

    public void connectionErrorMessage(Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Erreur dans la connexion");
        alertDialog.setMessage("Impossible d'effectuer la connexion.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
