package com.tactfactory.my_qcm.entity;


import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for class user
 */
public class User {

    private int id;
    private int id_server;
    private String username;
    private  String pwd;
    private String email;
    private Date last_login;
    private Date updated_at;
    private ArrayList<Mcq> mcqs;
    private ArrayList<Team> teams;

    /**
     * Constructor don't make have to mandatory the List of Mcqs and teams
     * @param id
     * @param id_server
     * @param username
     * @param email
     * @param pwd
     * @param last_login
     * @param updated_at
     */
    public User(int id, int id_server, String username, String email, String pwd, Date last_login, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.username = username;
        this.email = email;
        this.pwd = pwd;
        this.last_login = last_login;
        this.updated_at = updated_at;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ArrayList<Mcq> getMcqs() {
        return mcqs;
    }

    public void setMcqs(ArrayList<Mcq> mcqs) {
        this.mcqs = mcqs;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
