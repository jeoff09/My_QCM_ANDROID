package com.tactfactory.my_qcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity class for Team
 */
public class Team {

    private int id;
    private int id_server;
    private String name;
    private ArrayList<Mcq> mcqs;
    private Date updated_at;

    /**
     * Constructor don't make have to mandatory the List of Mcqs
     * @param id
     * @param id_server
     * @param name
     * @param updated_at
     */
    public Team(int id,int id_server, String name, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.name = name;
        this.updated_at = updated_at;
    }

    /**
     * Return Team id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of Team
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return name of Team
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the Team
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return id_server For the Team
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Set the id_server for the Team
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     * Return date of last_update
     * @return udapted_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set the date of last_update
     * @param updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * Return the mcqs of Team
     * @return mcqs
     */
    public ArrayList<Mcq> getMcqs() {
        return mcqs;
    }

    /**
     * Set the mcqs for the Team
     * @param mcqs
     */
    public void setMcqs(ArrayList<Mcq> mcqs) {
        this.mcqs = mcqs;
    }
}
