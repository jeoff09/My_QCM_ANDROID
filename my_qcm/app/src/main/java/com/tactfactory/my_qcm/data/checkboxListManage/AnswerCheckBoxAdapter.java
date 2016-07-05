package com.tactfactory.my_qcm.data.checkboxListManage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.entity.Answer;

import java.util.ArrayList;

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

         TextView textview_value_answer;
         CheckBox checkbox_item_answer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AnswerHolder answerHolder =  null;
        Log.v("convertView",String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row_fragment_list_answer, null);

            answerHolder = new AnswerHolder();
            answerHolder.textview_value_answer = (TextView) convertView.findViewById(R.id.textview_value_answer);
            answerHolder.checkbox_item_answer = (CheckBox) convertView.findViewById(R.id.checkbox_item_answer);
            convertView.setTag(answerHolder);


            answerHolder.checkbox_item_answer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    TextView textView = (TextView) v;
                    Answer answer = (Answer) cb.getTag();
                    answer.setSelected(cb.isChecked());
                }
            });
        }
        else {
            answerHolder = (AnswerHolder) convertView.getTag();
        }

        Answer answer = answers.get(position);
        answerHolder.textview_value_answer.setText(answer.getAns());
        answerHolder.checkbox_item_answer.setChecked(answer.isSelected());
        answerHolder.checkbox_item_answer.setTag(answer);

        return convertView;
    }
}



