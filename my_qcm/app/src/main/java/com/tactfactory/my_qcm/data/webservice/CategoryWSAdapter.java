package com.tactfactory.my_qcm.data.webservice;


import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.entity.Categ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cz.msebera.android.httpclient.Header;


public class CategoryWSAdapter {

    String response;
    MyQCMConstants myQCMConstants;

    public void getCategoryRequest (Integer user_id, String url, final CallBack callback) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(60000);
        asyncHttpClient.setTimeout(600000);
        RequestParams params = new RequestParams();
        params.put(myQCMConstants.CONST_VALUE_ID_USER, user_id);

        asyncHttpClient.post(url + ".json", params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                response = responseString;

                ArrayList<Categ> categories = responseToList(response);
                for(Categ categ:categories) {
                    System.out.println("On success = " + categ.getName());
                }

                callback.methods(categories);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                ArrayList<Categ> categories = new ArrayList<Categ>();
                System.out.println("On failure");
                callback.methods(categories);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                String str = null;
                ArrayList<Categ> categories = new ArrayList<Categ>();
                try {
                    str = new String(responseBytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("On failure = " + str);
                response = "false";
                callback.methods(categories);
            }
        });
    }

    public interface CallBack{
        void methods(ArrayList<Categ> categories);
    }

    public  ArrayList<Categ> responseToList(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Categ>>(){}.getType();

        ArrayList<Categ> categories = new ArrayList<Categ>();
        categories = (ArrayList<Categ>) gson.fromJson(response, collectionType);

        return categories;


    }


    }