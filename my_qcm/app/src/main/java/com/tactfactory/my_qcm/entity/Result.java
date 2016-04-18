package com.tactfactory.my_qcm.entity;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity class for Result
 */
public class Result {

    private int id;
    private int id_server_user;
    private  int id_server_mcq;
    private ArrayList<Integer> list_id_server_answer;

    /**
     * Constructor
     * @param id
     * @param id_server_user
     * @param id_server_mcq
     */
    public Result(int id, int id_server_user, int id_server_mcq) {
        this.id = id;
        this.id_server_user = id_server_user;
        this.id_server_mcq = id_server_mcq;
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

    public ArrayList<Integer> getList_id_server_answer() {
        return list_id_server_answer;
    }

    public void setList_id_server_answer(ArrayList<Integer> list_id_server_answer) {
        this.list_id_server_answer = list_id_server_answer;
    }
}
