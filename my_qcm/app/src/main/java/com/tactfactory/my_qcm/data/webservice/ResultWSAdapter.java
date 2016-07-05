package com.tactfactory.my_qcm.data.webservice;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.entity.Result;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jeoffrey on 04/07/2016.
 */
public class ResultWSAdapter {
    Context context;
    String response;
    MyQCMConstants myQCMConstants;

    public ResultWSAdapter(Context context) {
        this.context = context;
    }

    public void sendResultRequest(String result, String url, final CallBack callback)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(myQCMConstants.CONST_CONNECT_TIMEOUT);
        asyncHttpClient.setTimeout(myQCMConstants.CONST_SET_TIMEOUT);
        RequestParams params = new RequestParams();
        params.put(myQCMConstants.CONST_VALUE_RESULT, result);
        asyncHttpClient.post(url + ".json", params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                System.out.println("On success = " + responseString);
                callback.methods("true");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
                callback.methods("false");
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
                response = "false";
                callback.methods(response);
            }
        });
    }

    public String resultToJSON(Result result)
    {
        String resultJSON;

        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<Result>(){}.getType();


        resultJSON = gson.toJson(result, collectionType);


        return resultJSON;
    }

    public interface CallBack{
        void methods(String response);
    }
}
