package com.tactfactory.my_qcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for class Typ
 */
public class Typ {

    private int id;
    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int id_server;

    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    private String name;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date updated_at;

    /**
     * Contructor
     * @param id
     * @param id_server
     * @param name
     * @param updated_at
     */
    public Typ(int id,int id_server, String name, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.name = name;
        this.updated_at = updated_at;
    }

    /**
     * Get id_server for the Typ
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Return the id of Typ
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of Typ
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the id_server of Typ
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     * Return the name of the Typ
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Name of the Typ
     * @param name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Return the date of the updated_at
     * @return updated_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set the date of last update for the Typ
     * @param created_at
     */
    public void setUpdated_at(Date created_at) {
        this.updated_at = created_at;
    }
}
