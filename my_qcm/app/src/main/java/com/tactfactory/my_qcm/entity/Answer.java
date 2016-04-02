package com.tactfactory.my_qcm.entity;

import java.sql.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 */
public class Answer {

    private int id_server;
    private String ans;
    private Boolean isTrue;
    private Question question;
    private Date updated_at;

    public Answer(int id_server, String ans, Boolean isTrue, Question question, Date updated_at) {
        this.id_server = id_server;
        this.ans = ans;
        this.isTrue = isTrue;
        this.question = question;
        this.updated_at = updated_at;
    }

    public long getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public Boolean getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(Boolean isTrue) {
        this.isTrue = isTrue;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
