package com.tactfactory.my_qcm.view.result;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tactfactory.my_qcm.R;

/**
 * Created by ProtoConcept GJ on 03/05/2016.
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        /**
         * Todo : Call when the Questonnaire is over and Send the result on the WebService
         * else put in Database and asyntask try to send on the WebService
         */
    }
}
