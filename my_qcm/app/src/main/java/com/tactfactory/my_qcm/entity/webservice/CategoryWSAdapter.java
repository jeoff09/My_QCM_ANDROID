package com.tactfactory.my_qcm.entity.webservice;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tactfactory.my_qcm.entity.Categ;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class CategoryWSAdapter {

    private static final String BASE_URL ="http://192.168.1.14/qcm/web/app_dev.php/api/categoriesusers";
    private static final String ENTITY = "category";
    private static final int VERSION = 1;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String ID = "id";
    private static final String IDSERVER = "idServer";
    private static final String NAME = "name";
    private static final String UPDATEDAT = "updatedAt";

    public static void getCategory(int idServer,AsyncHttpResponseHandler handler) {

        String url = String.format("%s/%s",BASE_URL,idServer);
        client.get(url,handler);
    }

    public static void getAll() {

    }

    public static void post(Categ item,AsyncHttpResponseHandler responseHandler) throws JSONException {
        RequestParams params = CategoryWSAdapter.ItemToParams(item);
        String url = String.format("%s/%s",BASE_URL,ENTITY);
        client.post(url,params,responseHandler);

    }

    public static void put(Categ item,AsyncHttpResponseHandler responseHandler) {

        RequestParams params = CategoryWSAdapter.ItemToParams(item);
        String url = String.format("%s/%s",BASE_URL,ENTITY);
        client.put(url, params, responseHandler);
    }

    public static void delete(Categ item,AsyncHttpResponseHandler responseHandler) {

        String url = String.format("%s/%s/%s",BASE_URL,ENTITY,item.getId());
        client.post(url,responseHandler);

    }

    public static Categ jsonToItem(JSONObject json) throws JSONException {

        Date updatedAt = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String name = json.getString(NAME);
        int idServer = json.getInt(IDSERVER);
        String stringUpdatedAt =  json.getString(UPDATEDAT);
        try {
            updatedAt = simpleDateFormat.parse(stringUpdatedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Categ item = new Categ(idServer,name,updatedAt);
        return item;
    }
    public static JSONObject itemToJson(Categ item) throws JSONException {
        JSONObject result = new JSONObject();
        if(item.getName() != null) {
            result.put(NAME,item.getName());
        }
        if(item.getId_server() != 0) {
            result.put(IDSERVER,item.getId_server());
        }
        if(item.getUpdated_at() != null) {
            result.put(UPDATEDAT,item.getUpdated_at());
        }
        return result;
            }
    public static RequestParams ItemToParams(Categ item) {
        RequestParams params = new RequestParams();
        params.put(IDSERVER,item.getId_server());
        params.put(NAME,item.getName());
        params.put(UPDATEDAT,item.getUpdated_at());
        return params;
            }
    }