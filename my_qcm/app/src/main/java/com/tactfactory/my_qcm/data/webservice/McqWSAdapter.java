package com.tactfactory.my_qcm.data.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Mcq;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ProtoConcept GJ on 26/05/2016.
 */
public class McqWSAdapter {

    String response;
    MyQCMConstants myQCMConstants;

    public void getMcqRequest (Integer user_id_server,Integer categ_id_server ,String url, final CallBack callback)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(60000);
        asyncHttpClient.setTimeout(600000);
        RequestParams params = new RequestParams();
        params.put(myQCMConstants.CONST_VALUE_ID_USER, user_id_server);
        params.put(myQCMConstants.CONST_VALUE_ID_CATEG,categ_id_server);

        asyncHttpClient.post(url + ".json", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
                callback.methods(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;

                ArrayList<Mcq> mcqs = responseToList(response);
                for(Mcq mcq:mcqs) {
                    System.out.println("On success = " + mcq.getName());
                }

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
                response = "false";
                callback.methods(response);
            }
        });
    }

    public interface CallBack{
        void methods(String reponse);
    }

    public ArrayList<Mcq> responseToList(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Mcq>>(){}.getType();

        ArrayList<Mcq> mcqs = new ArrayList<Mcq>();
        mcqs = (ArrayList<Mcq>) gson.fromJson(response, collectionType);

        return mcqs;
    }
}
