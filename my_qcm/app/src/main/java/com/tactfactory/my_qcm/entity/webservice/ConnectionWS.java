package com.tactfactory.my_qcm.entity.webservice;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jeoffrey on 19/05/2016.
 */
public class ConnectionWS {

    String response;
    MyQCMConstants myQCMConstants;

    static public boolean isURLReachable(Context context,String serverUrl) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL(serverUrl);
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setConnectTimeout(10 * 1000);
                urlc.connect();

                if (urlc.getResponseCode() == 200) {
                    Log.wtf("Connection", "Success !");
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

     public void ConnectionRequest (String url,String username, String password, final CallBack callback){
        AsyncHttpClient asyncHttpClient = new  AsyncHttpClient();
         asyncHttpClient.setConnectTimeout(60000);
         asyncHttpClient.setTimeout(600000);
        RequestParams params = new  RequestParams();
        params.put(myQCMConstants.CONST_VALUE_LOGIN, username);
        params.put(myQCMConstants.CONST_VALUE_PWD, password);
        asyncHttpClient.post(url +".json",params, new TextHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                System.out.println("On success = " + responseString);
                callback.methods(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
                callback.methods(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                String str = null;
                try {
                     str = new String(responseBytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("On failure = " + str);
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
