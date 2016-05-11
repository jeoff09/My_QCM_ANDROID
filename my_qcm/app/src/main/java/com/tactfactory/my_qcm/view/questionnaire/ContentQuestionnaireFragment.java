package com.tactfactory.my_qcm.view.questionnaire;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tactfactory.my_qcm.R;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 * Fragment Content list of Answer
 */
public class ContentQuestionnaireFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content_questionnaire, container, false);

        /**
         * Todo : Show the list of Answer in the actual Question , 2 buttons ( next , preview)
         * Replacement the fragment with another
         */
    }
}
