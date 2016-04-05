package com.tactfactory.my_qcm.entity;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 */
public class Question {

    private int id;
    private int id_server;
    private Media media;
    private ArrayList<Answer> answers;
    private Mcq mcq;
    private Date updated_at;

    public Question(int id, int id_server, Mcq mcq, Date updated_at, ArrayList<Answer> answers) {
        this.id = id;
        this.id_server = id_server;
        this.mcq = mcq;
        this.updated_at = updated_at;
        this.answers = answers;
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

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public Mcq getMcq() {
        return mcq;
    }

    public void setMcq(Mcq mcq) {
        this.mcq = mcq;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
