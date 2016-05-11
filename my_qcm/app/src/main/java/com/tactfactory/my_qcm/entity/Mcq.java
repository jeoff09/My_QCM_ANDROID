package com.tactfactory.my_qcm.entity;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for MCQ
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

    /**
     * Constructor don't make have to mandatory the List of Question
     * @param id
     * @param id_server
     * @param name
     * @param duration
     * @param categ
     * @param updated_at
     */
    public Mcq(int id, int id_server, String name, int duration,Categ categ, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.name = name;
        this.duration = duration;
        this.category = categ;
        this.updated_at = updated_at;
    }

    /**
     * Return Id of the Mcq
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Se the id of the Mcq
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *  return id_server of the Mcq
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Set the Id_server of the Mcq
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     * Return name of the Mcq
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the Mcq
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return date_end of the Mcq
     * @return dateEnd
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * Set the Date_end of the Mcq
     * @param dateEnd
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * Return the dateStart of the Mcq
     * @return dateStart
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * Set the dateStart of the Mcq
     * @param dateStart
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * Return Duration of the Mcq
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set the duration of the Mcq
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Return category of the Mcq
     * @return category
     */
    public Categ getCategory() {
        return category;
    }

    /**
     * Set the category of Mcq
     * @param category
     */
    public void setCategory(Categ category) {
        this.category = category;
    }

    /**
     * Return the questions of the Mcq
     * @return questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Set the questions of the Mcq
     * @param questions
     */
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    /**
     * Return date of last_update of the Mcq
     * @return updated_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set the date of the last_update
     * @param updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    /**
     * Override method to show the name in the List
     */
    public String toString() {
        return name;
    }
}
