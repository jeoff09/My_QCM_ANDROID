package com.tactfactory.my_qcm.data.webservice;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.data.CategSQLiteAdapter;
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
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;


/**
 * Manage get categ with webservice and (Update, insert, delete in Local DB)
 */
public class CategoryWSAdapter {

    String response;
    MyQCMConstants myQCMConstants;
    Context context;

    public CategoryWSAdapter(Context context) {
        this.context = context;
    }


    /**
     * Get categ in webservice for update
     * @param user_id
     * @param url
     */
    public void getCategoryRequest (Integer user_id, String url) {
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

                ManageCategDB(categories);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
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

            }
        });
    }

    /**
     * Get categ in webService for the first connection
     * @param userId
     * @param url
     * @param callback
     */
    public void getCategoryRequest(int userId,String url,final CallBack callback ){
        //Set connection to Web service
        //-----------------------------
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(MyQCMConstants.CONST_CONNECT_TIMEOUT);
        asyncHttpClient.setTimeout(MyQCMConstants.CONST_SET_TIMEOUT);

        //Set param to build post request
        //-------------------------------
        RequestParams params = new RequestParams();
        params.put(MyQCMConstants.CONST_VALUE_ID_USER, userId);
        RequestHandle post = asyncHttpClient.post(url +  ".json", params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                System.out.println("response = " + response);
                if(response  == null)
                {
                    System.out.println("No answer");
                    response = "isEmpty";
                }
                callback.methods(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = "OnFailure";
                System.out.println("getCatgeoryRequest - FirstConnection :" + response);
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
                System.out.println("getCatgeoryRequest - FirstConnection - On failure 2 = " + str);
                response = "OnFailure";
                callback.methods(response);
            }
        });
    }

    public  ArrayList<Categ> responseToList(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Categ>>(){}.getType();

        ArrayList<Categ> categories = new ArrayList<Categ>();
        categories = (ArrayList<Categ>) gson.fromJson(response, collectionType);

        return categories;


    }
    public void ManageCategDB (ArrayList<Categ> response)
    {
        if (response.isEmpty() == false) {
            // get the list of categ in Flux and add on listView
            ArrayList<Categ> list = response;
            ArrayList<String> listResult = null;

            // Call the AsyncTask to add Categ on the DB and returns the list of result
            try {
                new ManageCategDBTask().execute(list).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Static method Response
     * @param response
     * @return
     */
    public static ArrayList<Categ> ResponseToList(String response) {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Categ>>(){}.getType();
        //ArrayList<Category> categories = new ArrayList<Category>();
        ArrayList<Categ> categories = (ArrayList<Categ>) gson.fromJson(response, collectionType);
        return categories;
    }

    /**
     * Asyntask To Manage Categories get on the Flow in the DB
     */
    public class ManageCategDBTask extends AsyncTask<ArrayList<Categ>,Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<Categ>... params) {
            ArrayList<Categ> categories =  new ArrayList<Categ>();
            ArrayList<String> results = new ArrayList<String>();
            categories = params[0];

            CategSQLiteAdapter categSQLiteAdapter = new CategSQLiteAdapter(context);
            categSQLiteAdapter.open();
            ArrayList<Categ> categoriesDB = categSQLiteAdapter.getAllCateg();

            for(Categ categ : categories)
            {
                Categ tempCateg ;
                //Try to find a Categ with this id_server
                tempCateg = categSQLiteAdapter.getCategById_server(categ.getId_server());

                //If Categ not exist on Mobile DB
                if(tempCateg == null)
                {
                    //Add categ on the DB
                    long result = categSQLiteAdapter.insert(categ);
                    results.add(String.valueOf(result));
                }
                else
                {
                    if (categ.getUpdated_at().compareTo(tempCateg.getUpdated_at()) > 0) {
                        System.out.println("Commparaison de date mise à jour : date du flux  " + categ.getUpdated_at() + " Date de la BDD" + tempCateg.getUpdated_at());
                        long result = categSQLiteAdapter.update(categ);
                        results.add(String.valueOf(result));
                    }
                }

            }
            //delete check is existe on the DB but not
            if(categoriesDB != null) {
                for (Categ categorie : categoriesDB) {
                    Boolean isExist = false;
                    for (Categ categ : categories) {
                        if (categ.getId_server() == categorie.getId_server()) {
                            isExist = true;
                        }
                    }

                    if (isExist == false) {
                        long result = categSQLiteAdapter.delete(categorie);
                    }
                }
            }
            categSQLiteAdapter.close();


            return null;
        }

        @Override
        protected void onPostExecute( Void Zvoid) {
            super.onPostExecute(Zvoid);
        }
    }

    public interface CallBack{
        void methods(String reponse);
    }


    public void CategoryErrorMessage(Context context, int idError){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Message d'erreur");
        if (idError == 1) {
            alertDialog.setMessage("Impossible de récupérer le flux de données." +
                    "Êtes-vous sûr d'avoir une connexion au réseau ?");
        }else if (idError == 2) {
            alertDialog.setMessage("Aucune Categorie n'est paramétrée pour votre compte." +
                    "Veuillez contacter votre administrateur");
        } else if (idError == 3) {
            alertDialog.setMessage("Attention ! votre base de données n'est pas complète" +
                    "Veuillez contacter votre administrateur");
        }

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    }