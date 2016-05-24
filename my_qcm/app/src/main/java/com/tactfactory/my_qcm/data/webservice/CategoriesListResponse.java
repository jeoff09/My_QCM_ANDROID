package com.tactfactory.my_qcm.data.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.tactfactory.my_qcm.entity.Categ;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ProtoConcept GJ on 24/05/2016.
 */
public class CategoriesListResponse {

    @SerializedName("categories")
    List<Categ> categories;

    public CategoriesListResponse() {
        categories = new ArrayList<Categ>();
    }

    public static CategoriesListResponse parseJSON(String response) {
         String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        Gson gson =  gsonBuilder.create();

        CategoriesListResponse categoriesResponse = gson.fromJson(response, CategoriesListResponse.class);
        return categoriesResponse;
    }
}
