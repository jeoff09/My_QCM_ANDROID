package com.tactfactory.my_qcm.view.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.data.AnswerSQLiteAdapter;
import com.tactfactory.my_qcm.data.QuestionSQLiteAdapter;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.entity.Question;
import com.tactfactory.my_qcm.entity.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 */
public class QuestionnaireActivity  extends AppCompatActivity {
    QuestionSQLiteAdapter questionSQLiteAdapter;
    AnswerSQLiteAdapter answerSQLiteAdapter;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    Result result;
    String questionsJson;
    String answersJson;
    String resultJson;
    String answeringJson;
    int questionsPositionList;
    int naviguationValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        // Get element pass in params
        Intent intent = getIntent();
        int id_mcq = intent.getIntExtra("id_mcq", 0);
        int id_user = intent.getIntExtra("id_user",0);

        // Get the list of Questions and linked answer transform to json
        questionSQLiteAdapter = new QuestionSQLiteAdapter(QuestionnaireActivity.this);
        answerSQLiteAdapter = new AnswerSQLiteAdapter(QuestionnaireActivity.this);
        questionSQLiteAdapter.open();
        questions = new ArrayList<>();
        questions = questionSQLiteAdapter.getAllQuestionById_server_MCQ(id_mcq);
        questionsJson = listQuestionsToJSON(questions);

        answers = new ArrayList<>();
        answerSQLiteAdapter.open();

        if (questions != null){
            for (Question question : questions) {
                ArrayList<Answer> tempList = answerSQLiteAdapter.getAllAnswerById_server_question(question.getId_server());
                if(tempList != null)
                {
                    for (Answer answer : tempList) {
                        answer.setQuestion(question);
                    }
                    if (tempList.size() != 0) {
                        answers.addAll(tempList);
                    }
                }
            }
        }

        answersJson = listAnswersToJSON(answers);

        // Question start in the item 0
         questionsPositionList = 0;

        // value of the button next = 1 & previous = 0
         naviguationValue = 1;

        // Add element on bundle
        Bundle bundleContent = new Bundle();
        bundleContent.putString("list_question" , questionsJson);
        bundleContent.putString("list_answer", answersJson);
        bundleContent.putInt("questions_position", questionsPositionList);
        bundleContent.putInt("navigation_value",naviguationValue);
        bundleContent.putInt("id_user", id_user);
        Bundle bundleSubHeader = new Bundle();
        bundleSubHeader.putInt("id_mcq", id_mcq);
        bundleSubHeader.putString("name_question", "BoB");

        // set the fragment initially for the sub Header with is bundle
        SubHeaderQuestionnaireFragment fragmentHeader = new SubHeaderQuestionnaireFragment();
        FragmentTransaction fragmentTransactionHeader =
                getSupportFragmentManager().beginTransaction();
        fragmentHeader.setArguments(bundleSubHeader);
        fragmentTransactionHeader.replace(R.id.header_questionnaire, fragmentHeader);
        fragmentTransactionHeader.commit();

        // set the fragment initially for the content of my questionnaire with is bundle
        ContentQuestionnaireFragment fragmentContent = new ContentQuestionnaireFragment();
        FragmentTransaction fragmentTransactionContent =
                getSupportFragmentManager().beginTransaction();
        fragmentContent.setArguments(bundleContent);
        fragmentTransactionContent.replace(R.id.content_questionnaire, fragmentContent);
        fragmentTransactionContent.commit();
    }
    // overrideBack button to prevent the user from leaving the questionnaire
    @Override
    public void onBackPressed() {
    }

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

    public String resultToJSON(Result result)
    {
        String resultJSON;

        //Format of the recup Date
        String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<Result>(){}.getType();


        resultJSON = gson.toJson(result, collectionType);


        return resultJSON;
    }

}
