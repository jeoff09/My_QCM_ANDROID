package com.tactfactory.my_qcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for Categ
 */
public class Categ {

    private int id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int id_server;

    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    private String name;

    private ArrayList<Mcq> mcqs;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date updated_at;

    /**
     * Constructor , don't make have to mandatory the List of Mcqs
     * @param id_server
     * @param name
     * @param updated_at
     */
    public Categ( int id_server, String name, Date updated_at) {
        this.id_server = id_server;
        this.name = name;
        this.updated_at = updated_at;
    }

    /**
     * Return id of Categ
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of Categ
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return id_server of the Categ
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Set the id_server of the Categ
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getMcqs() {
        return mcqs;
    }

    public void setMcqs(ArrayList<Mcq> mcqs) {
        this.mcqs = mcqs;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return name;
    }
}
