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
     * @param id_server_user
     * @param id_server_mcq
     */
    public Result( int id_server_user, int id_server_mcq) {
        this.id_server_user = id_server_user;
        this.id_server_mcq = id_server_mcq;
    }

    /**
     * Return id of the Result
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id for the Result
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the id_server_user
     * @return id_server_user
     */
    public int getId_server_user() {
        return id_server_user;
    }

    /**
     * Set the id_server_user for the Result
     * @param id_server_user
     */
    public void setId_server_user(int id_server_user) {
        this.id_server_user = id_server_user;
    }

    /**
     * Return  id_server_mcq for the
     * @return
     */
    public int getId_server_mcq() {
        return id_server_mcq;
    }

    /**
     * Set the id_server_mcq for the Result
     * @param id_server_mcq
     */
    public void setId_server_mcq(int id_server_mcq) {
        this.id_server_mcq = id_server_mcq;
    }

    /**
     *  Return list_id_server_answer for the Result
     * @return list_id_server_answer
     */
    public ArrayList<Integer> getList_id_server_answer() {
        return list_id_server_answer;
    }

    /**
     * Set the list of id_server_answer
     * @param list_id_server_answer
     */
    public void setList_id_server_answer(ArrayList<Integer> list_id_server_answer) {
        this.list_id_server_answer = list_id_server_answer;
    }
}
