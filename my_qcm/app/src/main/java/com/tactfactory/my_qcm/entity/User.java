package com.tactfactory.my_qcm.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for class user
 */
public class User {

    private int id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int id_server;

    @SerializedName("username")
    @Expose(serialize = true, deserialize = true)
    private String username;

    private  String pwd;

    @SerializedName("email")
    @Expose(serialize = true, deserialize = true)
    private String email;

    @SerializedName("last_login")
    @Expose(serialize = true, deserialize = true)
    private Date last_login;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date updated_at;
    private ArrayList<Mcq> mcqs;
    private ArrayList<Team> teams;

    /**
     * Constructor don't make have to mandatory the List of Mcqs and teams
     * @param id_server
     * @param username
     * @param email
     * @param pwd
     * @param last_login
     * @param updated_at
     */
    public User( int id_server, String username, String email, String pwd, Date last_login, Date updated_at) {
        this.id_server = id_server;
        this.username = username;
        this.email = email;
        this.pwd = pwd;
        this.last_login = last_login;
        this.updated_at = updated_at;
    }

    /**
     * Return pwd of the User
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * set pwd of the User
     * @param pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * Return list of Mcqs for the User
     * @return mcqs
     */
    public ArrayList<Mcq> getMcqs() {
        return mcqs;
    }

    /**
     * Set the list of Mcqs For the User
     * @param mcqs
     */
    public void setMcqs(ArrayList<Mcq> mcqs) {
        this.mcqs = mcqs;
    }

    /**
     * Return the List of Team for the User
     * @return teams
     */
    public ArrayList<Team> getTeams() {
        return teams;
    }

    /**
     * Set the list of Teams for the User
     * @param teams
     */
    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    /**
     * Return id of User
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id of User
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return id_server of the User
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Set the id_server of the User
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     * Return Username of the User
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the UserName of the User
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return Email of the User
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the User
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return Date of  last Login
     * @return last_login
     */
    public Date getLast_login() {
        return last_login;
    }

    /**
     * Set the Date of last_login
     * @param last_login
     */
    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    /**
     * Return the Date of last_update for the user
     * @return updated_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set the date of last updated_at for the User
     * @param updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
