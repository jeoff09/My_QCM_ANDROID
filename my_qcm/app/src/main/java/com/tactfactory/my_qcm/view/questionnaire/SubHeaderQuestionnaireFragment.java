package com.tactfactory.my_qcm.view.questionnaire;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tactfactory.my_qcm.R;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 * Duration and QCM name and Number of the Question
 */
public class SubHeaderQuestionnaireFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_header_questionnaire, container, false);

        /**
         * Todo : Special Fragment always visible in the Questionaire , show duration, and numb
         * of the question
         */
    }
}
