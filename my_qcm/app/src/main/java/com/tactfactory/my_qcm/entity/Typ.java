package com.tactfactory.my_qcm.entity;

import java.sql.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 */
public class Typ {

    private int id_server;
    private String name;
    private Date created_at;

    public Typ(int id_server, String name, Date created_at) {
        this.id_server = id_server;
        this.name = name;
        this.created_at = created_at;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
