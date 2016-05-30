package com.tactfactory.my_qcm.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by jeoffrey on 02/04/2016.
 * Entity for Answer
 */
public class Answer {

    private int id;

    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int id_server;

    @SerializedName("ans")
    @Expose(serialize = true, deserialize = true)
    private String ans;

    @SerializedName("is_true")
    @Expose(serialize = true, deserialize = true)
    private Boolean isTrue;

    private Question question;

    @SerializedName("updated_at")
    @Expose(serialize = true, deserialize = true)
    private Date updated_at;

    /**
     * Constructor
     * @param id
     * @param id_server
     * @param ans
     * @param isTrue
     * @param updated_at
     */
    public Answer(int id, int id_server, String ans, Boolean isTrue, Date updated_at) {
        this.id = id;
        this.id_server = id_server;
        this.ans = ans;
        this.isTrue = isTrue;
        this.updated_at = updated_at;
    }

    /**
     *  Return the Answer id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Id of Answer
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the Answer id_server
     * @return id_server
     */
    public int getId_server() {
        return id_server;
    }

    /**
     * Set the id_server Of the AnswerS
     * @param id_server
     */
    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    /**
     * Return the value of the Answer
     * @return ans
     */
    public String getAns() {
        return ans;
    }

    /**
     * Set the value of the Answer
     * @param ans
     */
    public void setAns(String ans) {
        this.ans = ans;
    }

    /**
     * Return id this Answer is true
     * @return isTrue
     */
    public Boolean getIsTrue() {
        return isTrue;
    }

    /**
     * Set the value isTrue or not
     * @param isTrue
     */
    public void setIsTrue(Boolean isTrue) {
        this.isTrue = isTrue;
    }

    /**
     * Question associate with The Answer
     * @return question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Set the question associate With Answer
     * @param question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Return the Date of Last Update
     * @return updated_at
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * Set date of Last_update
     * @param updated_at
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }


}
