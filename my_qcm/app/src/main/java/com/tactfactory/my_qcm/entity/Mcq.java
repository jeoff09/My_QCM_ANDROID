package com.tactfactory.my_qcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 */
public class Mcq {

    private int id;
    private int id_server;
    private String name;
    private Date dateEnd;
    private Date dateStart;
    private int duration;
    private Categ category;
    private ArrayList<Question> questions;
    private  Date updated_at;

    public Mcq(int id, int id_server, String name, int duration,Categ categ, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.name = name;
        this.duration = duration;
        this.category = categ;
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

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Categ getCategory() {
        return category;
    }

    public void setCategory(Categ category) {
        this.category = category;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
