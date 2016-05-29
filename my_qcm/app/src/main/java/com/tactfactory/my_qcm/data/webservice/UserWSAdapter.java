package com.tactfactory.my_qcm.data.webservice;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.entity.User;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ProtoConcept GJ on 29/05/2016.
 */
public class UserWSAdapter {


    String response;

    /**
     * Get user information json flow
     * @param username
     * @param url
     * @param callback
     */
    public void getUserInformationRequest(String username, String url, final CallBack callback){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(MyQCMConstants.CONST_CONNECT_TIMEOUT);
        asyncHttpClient.setTimeout(MyQCMConstants.CONST_SET_TIMEOUT);
        RequestParams params = new RequestParams();
        params.put(MyQCMConstants.CONST_USERNAME, username);

        asyncHttpClient.post(url + ".json" , params, new TextHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                User user = JsonToItem(response);
                //TODO: Insert / update user in database
                callback.methods(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
                callback.methods(response);
            }
        });
    }


    public interface CallBack{
        void methods(String reponse);
    }

    /**
     * Construct User from user flow
     * @param response
     * @return User
     */
    public static User JsonToItem(String response){
        //Date format
        String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

        //Deserialize Webservice user information flow
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();

        User user = gson.fromJson(response, User.class);
        return user;
             }
}
