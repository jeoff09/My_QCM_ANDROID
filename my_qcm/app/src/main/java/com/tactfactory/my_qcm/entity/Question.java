package com.tactfactory.my_qcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for Class Question
 */
public class Question {

    private int id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int id_server;

    @SerializedName("que")
    @Expose(serialize = true, deserialize = true)
    private String ques;

    @SerializedName("media")
    @Expose(serialize = true, deserialize = true)
    private Media media;

    @SerializedName("answers")
    @Expose(serialize = true, deserialize = true)
    private ArrayList<Answer> answers;

    private Mcq mcq;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date updated_at;

    /**
     * Constructor don't make have to mandatory the List of Answer
     * @param id
     * @param id_server
     * @param ques
     * @param updated_at
     */
    public Question(int id, int id_server, String ques, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.ques = ques;
        this.updated_at = updated_at;
    }

    /**
     * Return value of the Question
     * @return
     */
    public String getQues() {
        return ques;
    }

    /**
     * Set the value of the question
     * @param ques
     */
    public void setQues(String ques) {
        this.ques = ques;
    }

    /**
     * Return the id of the Question
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the Question
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the id_server for the Question
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Set the id_server fot the question
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     * Return the Media of the Question
     * @return media
     */
    public Media getMedia() {
        return media;
    }

    /**
     * Set the Media of Question
     * @param media
     */
    public void setMedia(Media media) {
        this.media = media;
    }

    /**
     * Return the answers of the Question
     * @return answers
     */
    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    /**
     * Set the list of answer for the Question
     * @param answers
     */
    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    /**
     * Return the mcq of the Question
     * @return mcq
     */
    public Mcq getMcq() {
        return mcq;
    }

    /**
     * Set the Mcq of the Question
     * @param mcq
     */
    public void setMcq(Mcq mcq) {
        this.mcq = mcq;
    }

    /**
     * Return the updated_at for the Question
     * @return updated_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set the updated_at for the Question
     * @param updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

        @Override
        /**
         * Pass the ques and mcq when call to show in the list
         */
    public String toString() {
        return ques;
    }
}
