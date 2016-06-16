package com.tactfactory.my_qcm.data.checkboxListManage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.view.questionnaire.ContentQuestionnaireFragment;
import com.tactfactory.my_qcm.view.questionnaire.QuestionnaireActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ProtoConcept GJ on 16/06/2016.
 */
public class AnswerCheckBoxAdapter extends ArrayAdapter<Answer> {

    private ArrayList<Answer> answers;
    private Context context;

    public AnswerCheckBoxAdapter(ArrayList<Answer> answers,Context context) {
        super(context, R.layout.row_fragment_list_answer,answers);
        this.answers = answers;
        this.context = context;
    }

    private static class AnswerHolder{

        public TextView textview_value_answer;
        public CheckBox checkbox_item_answer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        AnswerHolder answerHolder = new AnswerHolder();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_fragment_list_answer,null);

            answerHolder.textview_value_answer = (TextView) v.findViewById(R.id.textview_value_answer);

            answerHolder.checkbox_item_answer = (CheckBox) v.findViewById(R.id.checkbox_item_answer);

            answerHolder.checkbox_item_answer.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) context);


        }else {
            answerHolder = (AnswerHolder) v.getTag();
        }

        Answer answer = answers.get(position);
        answerHolder.textview_value_answer.setText(answer.getAns());
        answerHolder.checkbox_item_answer.setChecked(answer.isSelected());
        answerHolder.checkbox_item_answer.setTag(answer);


         return super.getView(position,convertView,parent);
    }
}

