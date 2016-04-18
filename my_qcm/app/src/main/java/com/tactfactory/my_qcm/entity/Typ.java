package com.tactfactory.my_qcm.entity;

import java.util.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for class Typ
 */
public class Typ {

    private int id;
    private int id_server;
    private String name;
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

    public int getId_server() {
        return id_server;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date created_at) {
        this.updated_at = created_at;
    }
}
