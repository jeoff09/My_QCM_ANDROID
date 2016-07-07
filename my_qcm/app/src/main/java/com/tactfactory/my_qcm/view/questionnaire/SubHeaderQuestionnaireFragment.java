package com.tactfactory.my_qcm.view.questionnaire;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.data.McqSQLiteAdapter;
import com.tactfactory.my_qcm.data.QuestionSQLiteAdapter;
import com.tactfactory.my_qcm.entity.Mcq;
import com.tactfactory.my_qcm.entity.Question;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 * Duration and QCM name and Number of the Question
 */
public class SubHeaderQuestionnaireFragment extends Fragment {

    McqSQLiteAdapter mcqSQLiteAdapter;
    QuestionSQLiteAdapter questionSQLiteAdapter;
    TextView timer_mcq;
    Mcq mcq;
    boolean isfinish = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sub_header_questionnaire, container, false);
        RelativeLayout relativ_subHeader = (RelativeLayout)rootView.findViewById(R.id.relative_layout_subHeader);

        int id_mcq =  getArguments().getInt("id_mcq");

        // Get the mcq to set the name and set the timer
        mcqSQLiteAdapter = new McqSQLiteAdapter(getActivity().getApplication());
        questionSQLiteAdapter = new QuestionSQLiteAdapter(getActivity().getApplication());
        questionSQLiteAdapter.open();
        mcqSQLiteAdapter.open();
        ArrayList<Question> questions = questionSQLiteAdapter.getAllQuestionById_server_MCQ(id_mcq);
        questionSQLiteAdapter.close();
        mcq = mcqSQLiteAdapter.getMcqById_server(id_mcq);
        mcqSQLiteAdapter.close();

        TextView question_content = (TextView)rootView.findViewById(R.id.mcq_value);
        question_content.setText(mcq.getName());

        timer_mcq = (TextView)relativ_subHeader.findViewById(R.id.timer_mcq);
        long mcq_duration_mill = TimeUnit.MINUTES.toMillis( Long.valueOf(mcq.getDuration()));
        final CounterMCQDuration timer = new CounterMCQDuration(mcq_duration_mill,1000,mcq.getDuration());
        timer.start();
        /**
         * Todo : Special Fragment always visible in the Questionaire , show duration, and numb
         * of the question
         */
        return rootView;
    }

    /**
     * Create timer and update in all second the textview
     */
    public class CounterMCQDuration extends CountDownTimer{

        int duration;

        public CounterMCQDuration(long millisInFuture, long countDownInterval, int duration) {
            super(millisInFuture, countDownInterval);
            this.duration = duration;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis )));
            timer_mcq.setText(hms);
        }

        @Override
        public void onFinish() {
                    Button previous_question = (Button) getActivity().findViewById(R.id.previous_question);
                    previous_question.setVisibility(previous_question.INVISIBLE);
                    TextView textViewNext = (TextView) getActivity().findViewById(R.id.next_question);
                    textViewNext.setText("Terminer");
                }
            }
        }
