package com.tactfactory.my_qcm.view.questionnaire;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.data.checkboxListManage.AnswerCheckBoxAdapter;
import com.tactfactory.my_qcm.data.checkboxListManage.CompleteMCQFunctionAdapter;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.entity.Question;
import com.tactfactory.my_qcm.entity.Result;
import com.tactfactory.my_qcm.view.result.ResultActivity;

import java.util.ArrayList;

/**
 * Created by ProtoConcept GJ on 30/04/2016.
 * Fragment Content list of Answer
 */
public class ContentQuestionnaireFragment extends Fragment {

    String questionsJson;
    String answersJson;
    String resultJson;
    String answeringJson;
    AnswerCheckBoxAdapter answerCheckBoxAdapter;
    int questionsPositionList;
    int naviguationValue;
    ListView list_answer;
    TextView textView_Question_Value;
    ArrayList<Question> questions;
    ArrayList<Answer> answers;
    Result result;
    Question questionShow;
    ArrayList<Answer> answersShow;
    String answersToJson;
    CompleteMCQFunctionAdapter completeMCQFunctionAdapter;
    boolean isLastQuestion = false;
    int id_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_content_questionnaire, container, false);
        RelativeLayout relative_layout_content = (RelativeLayout) rootView.findViewById(R.id.relative_layout_content);
        //get the class to deserialize element
        completeMCQFunctionAdapter = new CompleteMCQFunctionAdapter();
        // get All Element link with the MCQ, Answer, Question
        questionsJson = getArguments().getString("list_question");
        questions = completeMCQFunctionAdapter.responseToListQuestion(questionsJson);
        answersJson = getArguments().getString("list_answer");
        System.out.println("answerJson" + answersJson);
        answers = completeMCQFunctionAdapter.responseToListAnswer(answersJson);
        //position of the question
        questionsPositionList = getArguments().getInt("questions_position");
        // if next = 1 if back = 0
        naviguationValue = getArguments().getInt("navigation_value");
        // get the id user
        id_user = getArguments().getInt("id_user");
        questionShow = completeMCQFunctionAdapter.questionShow(questions, questionsPositionList);
        answersShow = completeMCQFunctionAdapter.answersShow(answers, questionShow);

        // get the list view to put answer
        list_answer = (ListView) rootView.findViewById(R.id.list_answer);

        // get the value of text view and set the question name
        textView_Question_Value = (TextView)rootView.findViewById(R.id.question_value);
        textView_Question_Value.setText(questionShow.getQues());

        System.out.println("Question size = " + questions.size());
        System.out.println("Question position = " + questionsPositionList);
        // Manage button if is first question or last
        if (questionsPositionList == 0) {
            System.out.println("Is the first Question");
            Button previous_question = (Button) relative_layout_content.findViewById(R.id.previous_question);
            previous_question.setVisibility(previous_question.INVISIBLE);

        } else if (questionsPositionList == questions.size() -1) {
            System.out.println("Is the last Question");
            isLastQuestion = true;
            Button next_question = (Button) relative_layout_content.findViewById(R.id.next_question);
            next_question.setText("Terminer");
        } else {
            Button previous_question = (Button) relative_layout_content.findViewById(R.id.previous_question);
            previous_question.setVisibility(previous_question.VISIBLE);
            Button next_question = (Button) relative_layout_content.findViewById(R.id.next_question);
            next_question.setText("Suivant");
            System.out.println("This is not the last or the first question");
        }

        // Set the number of question and the actual question
        TextView textViewNumberQuestion = (TextView) getActivity().findViewById(R.id.questions_numbers);
        int questionPosition  = questionsPositionList +1;
        String textToTextView = questionPosition  + "/" + questions.size();
        System.out.println("textToTextView = " + textToTextView);
        textViewNumberQuestion.setText(textToTextView);


        //create an ArrayAdaptar from the String Array
        answerCheckBoxAdapter = new AnswerCheckBoxAdapter(answersShow, getContext().getApplicationContext(),
                R.layout.fragment_content_questionnaire);

        // Assign adapter to list_answer
        list_answer.setAdapter(answerCheckBoxAdapter);


        list_answer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Answer answer = (Answer) parent.getItemAtPosition(position);
                Toast.makeText(getContext().getApplicationContext(),
                        "Clicked on Row: " + answer.getAns(),
                        Toast.LENGTH_LONG).show();
            }
        });

        nextButtonClick(rootView,isLastQuestion);
        previousButtonClick(rootView);


        return rootView;
    }

    /**
     * Methode call when next button is clicked  check is the last question
     * @param rootView
     * @param isLastQuestion
     */
    private void nextButtonClick(View rootView, final boolean isLastQuestion) {
        RelativeLayout relative_layout_content = (RelativeLayout) rootView.findViewById(R.id.relative_layout_content);
        Button myButton = (Button) relative_layout_content.findViewById(R.id.next_question);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<Answer> answerListSelected = answerCheckBoxAdapter.answers;
                //update my answer list for setting
                for (Answer answerGetInJson : answers) {
                    for (Answer answerSelected : answerListSelected) {
                        if (answerGetInJson.getId_server() == answerSelected.getId_server()) {
                            System.out.println("Change is selected");
                            answerGetInJson.setSelected(answerSelected.isSelected());
                        }
                    }
                }
                TextView textViewNext = (TextView) getActivity().findViewById(R.id.next_question);
                String valueOfButton = textViewNext.getText().toString();
                // if last question show end dialog
                if (isLastQuestion == true || valueOfButton.equals("Terminer") == true) {

                    // if is the last question show dialog message
                    AlertDialog.Builder endQuestionnaire = new AlertDialog.Builder(getContext());
                    endQuestionnaire.setTitle("Fin du questionnaire");
                    endQuestionnaire.setMessage("Voulez-vous vraiment terminer le questionnaire ?");
                    endQuestionnaire.setIcon(R.drawable.ic_menu_help);
                    endQuestionnaire.setPositiveButton("Oui",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String answersJson = completeMCQFunctionAdapter.listAnswersToJSON(answers);
                                    String questionsJson = completeMCQFunctionAdapter.listQuestionsToJSON(questions);
                                    Intent intent = new Intent(getActivity(), ResultActivity.class);
                                    intent.putExtra("answers", answersJson);
                                    intent.putExtra("questions", questionsJson);
                                    intent.putExtra("id_user", id_user);
                                    startActivity(intent);
                                }
                            });
                    endQuestionnaire.setNegativeButton("Non",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();

                }
                // If no the last question set the value of different element and call the next question
                else {
        answersToJson = completeMCQFunctionAdapter.listAnswersToJSON(answers);
        questionsPositionList = questionsPositionList + 1;

        // Add element on bundle
        Bundle bundleContent = new Bundle();
        bundleContent.putString("list_question", questionsJson);
        bundleContent.putString("list_answer", answersToJson);
        bundleContent.putInt("questions_position", questionsPositionList);
        bundleContent.putInt("id_user", id_user);
        bundleContent.putInt("navigation_value", 1);

        //start new fragment
        // set the fragment initially for the content of my questionnaire with is bundle
        ContentQuestionnaireFragment fragmentContent = new ContentQuestionnaireFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransactionContent = fragmentManager.beginTransaction();
        fragmentContent.setArguments(bundleContent);
        fragmentTransactionContent.replace(R.id.content_questionnaire, fragmentContent);
        fragmentTransactionContent.commit();
    }
}
});
        }

    /**
     * On click previous button pass element for the futur fragment
     * @param rootView
     */
    private void previousButtonClick(View rootView){
        RelativeLayout relative_layout_content = (RelativeLayout) rootView.findViewById(R.id.relative_layout_content);
        Button myButton = (Button) relative_layout_content.findViewById(R.id.previous_question);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<Answer> answerListSelected = answerCheckBoxAdapter.answers;
                //update my answer list for setting
                for( Answer answerGetInJson : answers)
                {
                    for(Answer answerSelected : answerListSelected)
                    {
                        if(answerGetInJson.getId_server() == answerSelected.getId_server())
                        {
                            System.out.println("Change is selected");
                            answerGetInJson.setSelected(answerSelected.isSelected());
                        }
                    }
                }
                answersToJson = completeMCQFunctionAdapter.listAnswersToJSON(answers);
                questionsPositionList = questionsPositionList - 1;

                // Add element on bundle
                Bundle bundleContent = new Bundle();
                bundleContent.putString("list_question" , questionsJson);
                bundleContent.putString("list_answer", answersToJson);
                bundleContent.putInt("questions_position", questionsPositionList);
                bundleContent.putInt("id_user", id_user);
                bundleContent.putInt("navigation_value",0);

                //start new fragment
                // set the fragment initially for the content of my questionnaire with is bundle
                ContentQuestionnaireFragment fragmentContent = new ContentQuestionnaireFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransactionContent = fragmentManager.beginTransaction();
                fragmentContent.setArguments(bundleContent);
                fragmentTransactionContent.replace(R.id.content_questionnaire, fragmentContent);
                fragmentTransactionContent.commit();
            }
        });
    }
}


