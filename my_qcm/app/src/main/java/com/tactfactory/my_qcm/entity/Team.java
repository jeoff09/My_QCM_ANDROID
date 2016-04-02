package com.tactfactory.my_qcm.entity;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 */
public class Team {

    private int id_server;
    private String name;
    private ArrayList<Mcq> mcqs;
    private Date updated_at;

    public Team(int id_server, String name, Date updated_at) {
        this.id_server = id_server;
        this.name = name;
        this.updated_at = updated_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<Mcq> getMcqs() {
        return mcqs;
    }

    public void setMcqs(ArrayList<Mcq> mcqs) {
        this.mcqs = mcqs;
    }
}
