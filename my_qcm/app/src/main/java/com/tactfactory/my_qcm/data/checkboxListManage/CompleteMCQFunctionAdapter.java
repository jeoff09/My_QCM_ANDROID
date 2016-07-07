package com.tactfactory.my_qcm.data.checkboxListManage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.entity.Mcq;
import com.tactfactory.my_qcm.entity.Question;
import com.tactfactory.my_qcm.entity.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ProtoConcept GJ on 18/06/2016.
 * Class Managing Serilialize/deserialize list on get Actual Question qand answers linked
 */
public class CompleteMCQFunctionAdapter {


    /**
     *  Deserialize json to list of questions
     * @param response
     * @return list of question
     */
    public ArrayList<Question> responseToListQuestion(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Question>>(){}.getType();

        ArrayList<Question> questions = new ArrayList<Question>();
        questions = (ArrayList<Question>) gson.fromJson(response, collectionType);

        return questions;
    }

    /**
     *  Deserialize json to list of answer
     * @param response
     * @return list of answer
     */
    public ArrayList<Answer> responseToListAnswer(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Answer>>(){}.getType();

        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers = (ArrayList<Answer>) gson.fromJson(response, collectionType);

        return answers;
    }


    /**
     * How to now the actual question
     * @param questions
     * @param positionInList
     * @return question
     */
    public Question questionShow (ArrayList<Question> questions, int positionInList)
    {
        Question question = null;
        if(questions != null)
        {
            if(positionInList != -1)
            {
                question = questions.get(positionInList);
            }
            else {
                System.out.println("questionShow : position = 0");
            }

        }
        else {
            System.out.println("questionShow : List of question is empty");
        }

        return question;
    }


    /**
     * SReturn the answer list of selected question
     * @param answers
     * @param question
     * @return List Answer to show
     */
    public ArrayList<Answer> answersShow (ArrayList<Answer> answers, Question question)
    {
        ArrayList<Answer> answerShow = new ArrayList<>();

        if(answers != null)
        {
            if(question != null)
            {
                for (Answer answer : answers)
                {
                    System.out.println("Answer is selected = " + answer.isSelected());
                    if(answer.getQuestion().getId_server() == question.getId_server())
                    {
                        answerShow.add(answer);
                    }
                }
            }else {
                System.out.println("answersQuestion : Question is null");
            }
        }
        else {
            System.out.println("answersQuestion : List of answer is empty");
        }

        return answerShow;
    }

    /**
     * List question to json to send beetween content questionnaire
     * @param questions
     * @return jsonString
     */
    public String listQuestionsToJSON(ArrayList<Question> questions)
    {
        String questionsJSON;

        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Question>>(){}.getType();


        questionsJSON = gson.toJson(questions, collectionType);


        return questionsJSON;
    }

    /**
     * List answer to Json to send beetween content questionnaire
     * @param answers
     * @return jsonString
     */
    public String listAnswersToJSON(ArrayList<Answer> answers)
    {
        String answersJSON;

        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Answer>>(){}.getType();


        answersJSON = gson.toJson(answers, collectionType);


        return answersJSON;
    }

}
