package com.tactfactory.my_qcm.view.questionnaire;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.data.checkboxListManage.AnswerCheckBoxAdapter;
import com.tactfactory.my_qcm.entity.Answer;

import java.util.ArrayList;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 * Fragment Content list of Answer
 */
public class ContentQuestionnaireFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    ListView lv;
    ArrayList<Answer> answers;
    AnswerCheckBoxAdapter answerCheckBoxAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_content_questionnaire, container, false);

        /*
        bundleContent.putString("list_question" , questionsJson);
        bundleContent.putString("list_answer", answersJson);
        bundleContent.putString("result",resultJson);
        bundleContent.putInt("questions_position", questionsPositionList);
        bundleContent.putInt("navigation_value",naviguationValue);
        */

        lv = (ListView)rootView.findViewById(R.id.list_answer);
        displayAnswerList(answers,rootView);
        return rootView;
    }

    private void displayAnswerList(ArrayList<Answer> anwsers, View view)
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

            Toast.makeText(getActivity().getApplication(),"Checked o nanswer = " + answer.getAns(), Toast.LENGTH_LONG).show();
        }
    }
}
