package com.tactfactory.my_qcm.view.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.view.home.HomeActivity;
import com.tactfactory.my_qcm.view.home.HomeFragment;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 */
public class QuestionnaireActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        // set the fragment initially for the sub Header
        SubHeaderQuestionnaireFragment fragmentHeader = new SubHeaderQuestionnaireFragment();
        FragmentTransaction fragmentTransactionHeader =
                getSupportFragmentManager().beginTransaction();
        fragmentTransactionHeader.replace(R.id.sub_header_questionnaire, fragmentHeader);
        fragmentTransactionHeader.commit();

        // set the fragment initially for the content of my questionnaire
        ContentQuestionnaireFragment fragmentContent = new ContentQuestionnaireFragment();
        FragmentTransaction fragmentTransactionContent =
                getSupportFragmentManager().beginTransaction();
        fragmentTransactionContent.replace(R.id.sub_header_questionnaire, fragmentContent);
        fragmentTransactionContent.commit();
    }
}
