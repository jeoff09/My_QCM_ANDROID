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
 */
public class CompleteMCQFunctionAdapter {


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

    public Result responseToResult(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<Result>(){}.getType();

        Result result = new Result();
        result = (Result) gson.fromJson(response, collectionType);

        return result;
    }
    public String answeringsToJSON(ArrayList<Answering> answerings)
    {
        String answeringJSON;

        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Answering>>(){}.getType();


        answeringJSON = gson.toJson(answerings, collectionType);


        return answeringJSON;
    }

    public ArrayList<Answering> responseToListAnswering(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Answering>>(){}.getType();

        ArrayList<Answering> answerings = new ArrayList<Answering>();
        answerings = (ArrayList<Answering>) gson.fromJson(response, collectionType);

        return answerings;
    }

    public Question questionShow (ArrayList<Question> questions, int positionInList)
    {
        Question question = null;
        System.out.println("Nombre de question dans la liste =" +questions.size());
        if(questions != null)
        {
            if(positionInList != 0)
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

    public ArrayList<Answer> answersShow (ArrayList<Answer> answers, Question question)
    {
        ArrayList<Answer> answerShow = new ArrayList<>();

        if(answers != null)
        {
            if(question != null)
            {
                for (Answer answer : answers)
                {
                    System.out.println(" question  = "+ question.getQues());
                    System.out.println("Answer question idserver = " + answer.getQuestion().getId_server());
                    if(answer.getQuestion().getId_server() == question.getId_server())
                    {
                        answerShow.add(answer);
                    }else {
                        System.out.println("answersQuestion : Answer not linked to this question");
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
}
