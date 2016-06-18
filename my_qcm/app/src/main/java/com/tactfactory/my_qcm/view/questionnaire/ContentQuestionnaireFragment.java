package com.tactfactory.my_qcm.view.questionnaire;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.data.checkboxListManage.AnswerCheckBoxAdapter;
import com.tactfactory.my_qcm.data.checkboxListManage.Answering;
import com.tactfactory.my_qcm.data.checkboxListManage.CompleteMCQFunctionAdapter;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.entity.Question;
import com.tactfactory.my_qcm.entity.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 * Fragment Content list of Answer
 */
public class ContentQuestionnaireFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    String questionsJson;
    String answersJson;
    String resultJson;
    String answeringJson;
    int questionsPositionList;
    int naviguationValue;
    ListView lv;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    Result result;
    Question questionShow;
    ArrayList<Answer> answersShow;
    ArrayList<Answering> answerings;
    ArrayList<Answer> answersChecked;
    AnswerCheckBoxAdapter answerCheckBoxAdapter;
    CompleteMCQFunctionAdapter completeMCQFunctionAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_content_questionnaire, container, false);
        RelativeLayout relative_layout_content = (RelativeLayout)rootView.findViewById(R.id.relative_layout_content);
        //get the class to deserialize element
        completeMCQFunctionAdapter = new CompleteMCQFunctionAdapter();
        // get All Element link with the MCQ
        questionsJson =  getArguments().getString("list_question");
        questions = completeMCQFunctionAdapter.responseToListQuestion(questionsJson);

        answersJson =  getArguments().getString("list_answer");
        answers = completeMCQFunctionAdapter.responseToListAnswer(answersJson);

        resultJson =  getArguments().getString("result");
        result = completeMCQFunctionAdapter.responseToResult(resultJson);

        answeringJson = getArguments().getString("list_answering");
        answerings = completeMCQFunctionAdapter.responseToListAnswering(answeringJson);

        questionsPositionList = getArguments().getInt("questions_position");
        naviguationValue = getArguments().getInt("navigation_value");

        questionShow = completeMCQFunctionAdapter.questionShow(questions, questionsPositionList);
        answersShow = completeMCQFunctionAdapter.answersQuestion(answers,questionShow);

        lv = (ListView)rootView.findViewById(R.id.list_answer);
        displayAnswerList(answersShow);

        if(questionsPositionList == 0)
        {
            System.out.println("Is the first Question");
            Button previous_question = (Button)relative_layout_content.findViewById(R.id.previous_question);
            previous_question.setEnabled(false);

        }else if (questionsPositionList == questions.size())
        {
            System.out.println("Is the last Question");
            Button next_question = (Button)relative_layout_content.findViewById(R.id.next_question);
            next_question.setText("Terminer");
        }
        else {
            System.out.println("This is not the last or the first question");
        }

        return rootView;
    }

    private void displayAnswerList(ArrayList<Answer> anwsers)
    {
        answerCheckBoxAdapter = new  AnswerCheckBoxAdapter(anwsers,getActivity().getApplication());
        lv.setAdapter(answerCheckBoxAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int pos = lv.getPositionForView(buttonView);
        if(pos != ListView.INVALID_POSITION){
            Answer answer = answers.get(pos);
            answer.setSelected(isChecked);

            boolean isExist = false;
            //check if the list is null if not null enter
            if(answersChecked != null)
            {
                for (Answer answerCheck : answersChecked)
                {
                    if(answerCheck.getId_server() == answer.getId_server())
                    {
                        System.out.println("Answers already exist");
                        isExist = true;
                        answerCheck.setSelected(answer.isSelected());
                    }
                }
                //if not exist in the list Add
                if(isExist ==false)
                {
                   answersChecked.add(answer);
                }
            }
            // is null add answer
            else {

                answersChecked.add(answer);
            }

            Toast.makeText(getActivity().getApplication(),"Checked o nanswer = " + answer.getAns(), Toast.LENGTH_LONG).show();
        }
    }
}
