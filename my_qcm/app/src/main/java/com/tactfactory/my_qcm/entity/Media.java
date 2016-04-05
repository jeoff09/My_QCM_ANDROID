package com.tactfactory.my_qcm.entity;

import java.sql.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 */
public class Media {

    private int id;
    private int id_server;
    private String name;
    private String url;
    private Typ typ;
    private Date updated_at;

    public Media(int id, int id_server, String name, String url, Typ typ, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.name = name;
        this.url = url;
        this.typ = typ;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Typ getTyp() {
        return typ;
    }

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
