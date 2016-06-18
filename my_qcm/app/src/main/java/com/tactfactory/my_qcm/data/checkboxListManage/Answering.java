package com.tactfactory.my_qcm.data.checkboxListManage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ProtoConcept GJ on 18/06/2016.
 * Public class used when the user come to a COntent Fragement Check if he already complete this question Before
 * and set the Answer in list
 */
public class Answering {


    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    private int id;

    @SerializedName("question_position")
    @Expose(serialize = true, deserialize = true)
    private int question_position;

    @SerializedName("answers_id")
    @Expose(serialize = true, deserialize = true)
    private ArrayList<Integer> answers_id;

    public Answering() {

    }

    public int getQuestion_position() {
        return question_position;
    }

    public void setQuestion_position(int question_position) {
        this.question_position = question_position;
    }

    public ArrayList<Integer> getAnswers_id() {
        return answers_id;
    }

    public void setAnswers_id(ArrayList<Integer> answers_id) {
        this.answers_id = answers_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
