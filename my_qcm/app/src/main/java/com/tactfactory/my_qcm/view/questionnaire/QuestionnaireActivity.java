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
import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Mcq;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        // Get element pass in params
        Intent intent = getIntent();
        int id_mcq = intent.getIntExtra("id_mcq", 0);

        // Get the list of Questions and linked answer transform to json
        questionSQLiteAdapter = new QuestionSQLiteAdapter(QuestionnaireActivity.this);
        answerSQLiteAdapter = new AnswerSQLiteAdapter(QuestionnaireActivity.this);
        questionSQLiteAdapter.open();
        questions = new ArrayList<>();
        questions = questionSQLiteAdapter.getAllQuestionById_server_MCQ(id_mcq);

        String questionsJson = listQuestionsToJSON(questions);

        answers = new ArrayList<>();
        answerSQLiteAdapter.open();
        for(Question question : questions)
        {
            ArrayList<Answer> tempList = answerSQLiteAdapter.getAllAnswerById_server_question(question.getId_server());
            if( tempList.size() != 0) {
                answers.addAll(tempList);
            }
        }
        String answersJson = listAnswersToJSON(answers);

        int questionsPositionList = 0;

        Bundle bundleContent = new Bundle();
        bundleContent.putString("list_question" , questionsJson);
        bundleContent.putString("list_answer", answersJson);
        bundleContent.putInt("questions_position",questionsPositionList);

        Bundle bundleSubHeader = new Bundle();
        bundleSubHeader.putInt("id_mcq", id_mcq);
        bundleSubHeader.putString("name_question", "BoB");

        // set the fragment initially for the sub Header
        SubHeaderQuestionnaireFragment fragmentHeader = new SubHeaderQuestionnaireFragment();
        FragmentTransaction fragmentTransactionHeader =
                getSupportFragmentManager().beginTransaction();
        fragmentHeader.setArguments(bundleSubHeader);
        fragmentTransactionHeader.replace(R.id.header_questionnaire, fragmentHeader);
        fragmentTransactionHeader.commit();

        // set the fragment initially for the content of my questionnaire
        ContentQuestionnaireFragment fragmentContent = new ContentQuestionnaireFragment();
        FragmentTransaction fragmentTransactionContent =
                getSupportFragmentManager().beginTransaction();
        fragmentContent.setArguments(bundleContent);
        fragmentTransactionContent.replace(R.id.content_questionnaire, fragmentContent);
        fragmentTransactionContent.commit();
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
}
