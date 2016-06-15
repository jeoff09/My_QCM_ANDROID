package com.tactfactory.my_qcm.view.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tactfactory.my_qcm.R;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 */
public class QuestionnaireActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        Intent intent = getIntent();
        int id_mcq = intent.getIntExtra("id_mcq", 0);
        Bundle bundle = new Bundle();
        bundle.putInt("id_mcq", id_mcq);
        bundle.putString("name_question","BoB");
        // set the fragment initially for the sub Header
        SubHeaderQuestionnaireFragment fragmentHeader = new SubHeaderQuestionnaireFragment();
        FragmentTransaction fragmentTransactionHeader =
                getSupportFragmentManager().beginTransaction();
        fragmentHeader.setArguments(bundle);
        fragmentTransactionHeader.replace(R.id.header_questionnaire, fragmentHeader);
        fragmentTransactionHeader.commit();

        // set the fragment initially for the content of my questionnaire
        ContentQuestionnaireFragment fragmentContent = new ContentQuestionnaireFragment();
        FragmentTransaction fragmentTransactionContent =
                getSupportFragmentManager().beginTransaction();
        fragmentContent.setArguments(bundle);
        fragmentTransactionContent.replace(R.id.content_questionnaire, fragmentContent);
        fragmentTransactionContent.commit();
    }
}
