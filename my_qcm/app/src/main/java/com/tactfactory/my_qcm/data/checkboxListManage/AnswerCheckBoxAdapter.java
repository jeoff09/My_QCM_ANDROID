package com.tactfactory.my_qcm.data.checkboxListManage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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

    public ArrayList<Answer> answers;
    public Context context;

    public AnswerCheckBoxAdapter(ArrayList<Answer> answers, Context context,int ViewResourceId) {
        super(context,ViewResourceId, answers);
        this.answers = answers;
        this.context = context;
    }

    private class AnswerHolder {

        public TextView textview_value_answer;
        public CheckBox checkbox_item_answer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        AnswerHolder answerHolder = new AnswerHolder();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_fragment_list_answer, null);

            answerHolder.textview_value_answer = (TextView) v.findViewById(R.id.textview_value_answer);

            answerHolder.checkbox_item_answer = (CheckBox) v.findViewById(R.id.checkbox_item_answer);

            v.setTag(answerHolder);


            answerHolder.checkbox_item_answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Answer answer = (Answer) cb.getTag();
                    Toast.makeText(getContext().getApplicationContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    answer.setSelected(cb.isChecked());
                }
            });
        }
        else {
            answerHolder = (AnswerHolder) v.getTag();
        }

        Answer answer = answers.get(position);
        answerHolder.textview_value_answer.setText(answer.getAns());
        answerHolder.checkbox_item_answer.setChecked(answer.isSelected());


        return v;
    }
}



