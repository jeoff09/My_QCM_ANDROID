package com.tactfactory.my_qcm.entity;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 */
public class Result {

    private int id;
    private int id_server_user;
    private  int id_server_mcq;
    private Array list_id_server_answer;

    public Result(int id, int id_server_user, int id_server_mcq, Array list_id_server_answer) {
        this.id = id;
        this.id_server_user = id_server_user;
        this.id_server_mcq = id_server_mcq;
        this.list_id_server_answer = list_id_server_answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_server_user() {
        return id_server_user;
    }

    public void setId_server_user(int id_server_user) {
        this.id_server_user = id_server_user;
    }

    public int getId_server_mcq() {
        return id_server_mcq;
    }

    public void setId_server_mcq(int id_server_mcq) {
        this.id_server_mcq = id_server_mcq;
    }

    public Array getList_id_server_answer() {
        return list_id_server_answer;
    }

    public void setList_id_server_answer(Array list_id_server_answer) {
        this.list_id_server_answer = list_id_server_answer;
    }
}