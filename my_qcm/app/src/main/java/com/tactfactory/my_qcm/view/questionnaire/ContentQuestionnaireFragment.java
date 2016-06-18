package com.tactfactory.my_qcm.view.questionnaire;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.data.checkboxListManage.AnswerCheckBoxAdapter;
import com.tactfactory.my_qcm.data.checkboxListManage.Answering;
import com.tactfactory.my_qcm.data.checkboxListManage.CompleteMCQFunctionAdapter;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.entity.Question;
import com.tactfactory.my_qcm.entity.Result;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 * Fragment Content list of Answer
 */
public class ContentQuestionnaireFragment extends Fragment {

    String questionsJson;
    String answersJson;
    String resultJson;
    String answeringJson;
    AnswerCheckBoxAdapter answerCheckBoxAdapter;
    int questionsPositionList;
    int naviguationValue;
    ListView list_answer;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    Result result;
    Question questionShow;
    ArrayList<Answer> answersShow;
    ArrayList<Answering> answerings;
    ArrayList<Answer> answersChecked;
    CompleteMCQFunctionAdapter completeMCQFunctionAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_content_questionnaire, container, false);
        RelativeLayout relative_layout_content = (RelativeLayout) rootView.findViewById(R.id.relative_layout_content);
        //get the class to deserialize element
        completeMCQFunctionAdapter = new CompleteMCQFunctionAdapter();
        // get All Element link with the MCQ
        questionsJson = getArguments().getString("list_question");
        questions = completeMCQFunctionAdapter.responseToListQuestion(questionsJson);
        answersJson = getArguments().getString("list_answer");
        answers = completeMCQFunctionAdapter.responseToListAnswer(answersJson);

        resultJson = getArguments().getString("result");
        result = completeMCQFunctionAdapter.responseToResult(resultJson);

        answeringJson = getArguments().getString("list_answering");
        answerings = completeMCQFunctionAdapter.responseToListAnswering(answeringJson);

        questionsPositionList = getArguments().getInt("questions_position");
        naviguationValue = getArguments().getInt("navigation_value");

        questionShow = completeMCQFunctionAdapter.questionShow(questions, questionsPositionList);
        answersShow = completeMCQFunctionAdapter.answersShow(answers, questionShow);

        list_answer = (ListView) rootView.findViewById(R.id.list_answer);


        if (questionsPositionList == 1) {
            System.out.println("Is the first Question");
            Button previous_question = (Button) relative_layout_content.findViewById(R.id.previous_question);
            previous_question.setEnabled(false);

        } else if (questionsPositionList == questions.size()) {
            System.out.println("Is the last Question");
            Button next_question = (Button) relative_layout_content.findViewById(R.id.next_question);
            next_question.setText("Terminer");
        } else {
            System.out.println("This is not the last or the first question");
        }

        //create an ArrayAdaptar from the String Array
        answerCheckBoxAdapter = new AnswerCheckBoxAdapter(answers, getContext().getApplicationContext(),
                R.layout.fragment_content_questionnaire);
        ListView listView = (ListView) rootView.findViewById(R.id.list_answer);
        // Assign adapter to ListView
        listView.setAdapter(answerCheckBoxAdapter);

            checkButtonClick(rootView);

        return rootView;
    }

    private void checkButtonClick(View rootView) {

        RelativeLayout relative_layout_content = (RelativeLayout) rootView.findViewById(R.id.relative_layout_content);
        Button myButton = (Button) relative_layout_content.findViewById(R.id.next_question);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");
                ArrayList<Answer> countryList = answerCheckBoxAdapter.answers;
                for (int i = 0; i < countryList.size(); i++) {
                    Answer country = countryList.get(i);
                    if (country.isSelected()) {
                        responseText.append("\n" + country.getAns());
                    }
                }

                Toast.makeText(getContext().getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });
    }
}


