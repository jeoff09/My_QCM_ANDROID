package com.tactfactory.my_qcm.view.result;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.data.QuestionSQLiteAdapter;
import com.tactfactory.my_qcm.data.checkboxListManage.CompleteMCQFunctionAdapter;
import com.tactfactory.my_qcm.data.webservice.ResultWSAdapter;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.entity.Question;
import com.tactfactory.my_qcm.entity.Result;
import com.tactfactory.my_qcm.view.home.HomeActivity;

import java.util.ArrayList;

/**
 * Created by ProtoConcept GJ on 03/05/2016.
 */
public class ResultActivity extends AppCompatActivity {

    ResultWSAdapter resultWSAdapter;
    CompleteMCQFunctionAdapter completeMCQFunctionAdapter;
    ArrayList<Answer> answers;
    ArrayList<Question> questions;
    Question ToGetMCQ;
    QuestionSQLiteAdapter questionSQLiteAdapter;
    Result result;
    ProgressDialog dialog;
    ArrayList<Integer> id_answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        dialog=new ProgressDialog(ResultActivity.this);
        dialog.setMessage("Envoi des résultats ...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        Intent intent = getIntent();
        // get list of answers
        String answersJson = intent.getStringExtra("answers");
        String questionsJson = intent.getStringExtra("questions");
        id_answers = new ArrayList<>();

        resultWSAdapter = new ResultWSAdapter(this);
        completeMCQFunctionAdapter= new CompleteMCQFunctionAdapter();
        answers = completeMCQFunctionAdapter.responseToListAnswer(answersJson);
        questions = completeMCQFunctionAdapter.responseToListQuestion(questionsJson);
        ToGetMCQ = questions.get(0);
        System.out.println("question = " + ToGetMCQ.getQues());
        System.out.println(answersJson);
        for(Answer answer : answers)
        {
            if (answer.isSelected() == true)
            {
                id_answers.add(answer.getId_server());
                System.out.println(" id answer_server = " + answer.getId_server());
            }
        }
        questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        Question tempquestion = questionSQLiteAdapter.getQuestionById_server(ToGetMCQ.getId_server());
        questionSQLiteAdapter.close();
        result = new Result();
        result.setId_server_user(1);
        result.setId_server_mcq(tempquestion.getMcq().getId_server());
        result.setList_id_server_answer(id_answers);

        String resultJson = resultWSAdapter.resultToJSON(result);

        resultWSAdapter.sendResultRequest(resultJson, MyQCMConstants.CONST_URL_SEND_RESULT, new ResultWSAdapter.CallBack() {

            @Override
            public void methods(String response) {

                dialog.hide();
                System.out.println(" response = " + response);
                AlertDialog.Builder endQuestionnaire = new AlertDialog.Builder(ResultActivity.this);
                endQuestionnaire.setTitle("Synchronisation du résultat");
                if(response.equals("true")){
                    endQuestionnaire.setMessage("vos réponses on été prises en compte. " +
                            "Merci de contacter votre administrateur pour connaitre votre score.");
                }
                else {
                    endQuestionnaire.setMessage("vos réponses n'ont pas été prises en compte. " +
                            "Elles seront envoyées à une date ultérieur.");
                }
                endQuestionnaire.setIcon(R.drawable.ic_menu_help);
                endQuestionnaire.setPositiveButton("Accueil",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
        });



        /**
         * Todo : Call when the Questonnaire is over and Send the result on the WebService
         * else put in Database and asyntask try to send on the WebService
         */
    }
    // overrideBack button to prevent the user from leaving the questionnaire
    @Override
    public void onBackPressed() {
    }
}
