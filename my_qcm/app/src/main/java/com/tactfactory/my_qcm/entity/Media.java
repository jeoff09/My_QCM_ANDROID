package com.tactfactory.my_qcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for MEDIA
 */
public class Media {


    private int id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int id_server;

    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    private String name;

    @SerializedName("url")
    @Expose(serialize = true, deserialize = true)
    private String url;

    @SerializedName("typ")
    @Expose(serialize = true, deserialize = true)
    private Typ typ;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date updated_at;

    /**
     * Constructor
     * @param id
     * @param id_server
     * @param name
     * @param url
     * @param typ
     * @param updated_at
     */
    public Media(int id, int id_server, String name, String url, Typ typ, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.name = name;
        this.url = url;
        this.typ = typ;
        this.updated_at = updated_at;
    }

    /**
     * Return id for the Media
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the Media
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the id_server
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Set the id_server for the Media
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     * Return name of the Media
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of media
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the Url of the Media
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url of the Media
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return typ of the Media
     * @return typ
     */
    public Typ getTyp() {
        return typ;
    }

    /**
     * Set the Typ of the media
     * @param typ
     */
    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
