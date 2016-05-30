package com.tactfactory.my_qcm.data.webservice;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.data.AnswerSQLiteAdapter;
import com.tactfactory.my_qcm.data.McqSQLiteAdapter;
import com.tactfactory.my_qcm.data.QuestionSQLiteAdapter;
import com.tactfactory.my_qcm.entity.Answer;
import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Mcq;
import com.tactfactory.my_qcm.entity.Question;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ProtoConcept GJ on 26/05/2016.
 */
public class McqWSAdapter {

    String response;
    MyQCMConstants myQCMConstants;
    Context context;
    McqSQLiteAdapter mcqSQLiteAdapter ;
    QuestionSQLiteAdapter questionSQLiteAdapter;
    AnswerSQLiteAdapter answerSQLiteAdapter;

    public McqWSAdapter(Context context) {
        this.context = context;
    }

    public void getMcqRequest (Integer user_id_server,Integer categ_id_server ,String url)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(60000);
        asyncHttpClient.setTimeout(600000);
        RequestParams params = new RequestParams();
        params.put(myQCMConstants.CONST_VALUE_ID_USER, user_id_server);
        params.put(myQCMConstants.CONST_VALUE_ID_CATEG,categ_id_server);

        asyncHttpClient.post(url + ".json", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                response = responseString;
                System.out.println("On failure");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                response = responseString;
                System.out.println("responseString = " + responseString);
                ArrayList<Mcq> mcqs = responseToList(response);
                for(Mcq mcq:mcqs) {
                    System.out.println("On success = " + mcq.getName());
                }

                ManageMcqDB(mcqs);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                String str = null;
                try {
                    str = new String(responseBytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("On failure = " + str);
                response = "false";

            }
        });
    }

    public ArrayList<Mcq> responseToList(String response)
    {
        //Format of the recup Date
        String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        Gson gson =  gsonBuilder.create();
        Type collectionType = new TypeToken<List<Mcq>>(){}.getType();

        ArrayList<Mcq> mcqs = new ArrayList<Mcq>();
        mcqs = (ArrayList<Mcq>) gson.fromJson(response, collectionType);

        return mcqs;
    }

    public void ManageMcqDB (ArrayList<Mcq> response)
    {
        if (response.isEmpty() == false) {
            // get the list of categ in Flux and add on listView
            ArrayList<Mcq> list = response;
            ArrayList<String> listResult = null;

            // Call the AsyncTask to add Categ on the DB and returns the list of result
            try {
                listResult = new ManageMCQDBTask().execute(list).get();
                System.out.println(" list result "+ listResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    public class ManageMCQDBTask extends AsyncTask<ArrayList<Mcq>,Void,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(ArrayList<Mcq>... params) {
            ArrayList<Mcq> mcqs = params[0];
            ArrayList<String> results = new ArrayList<>();
            ArrayList<String> resultsQuestion ;

            mcqSQLiteAdapter = new McqSQLiteAdapter(context);
            mcqSQLiteAdapter.open();
            ArrayList<Mcq> mcqsDB = mcqSQLiteAdapter.getAllMcq();

            for(Mcq mcq : mcqs)
            {
                Mcq tempMcq ;
                //Try to find a Mcq with this id_server
                tempMcq = mcqSQLiteAdapter.getMcqById_server(mcq.getId_server());

                //If Categ not exist on Mobile DB
                if(tempMcq == null)
                {
                    //Add categ on the DB
                    long result = mcqSQLiteAdapter.insert(mcq);
                    results.add(String.valueOf(result));
                }
                else
                {
                    long result = mcqSQLiteAdapter.update(mcq);
                    results.add(String.valueOf(result));
                }

                resultsQuestion = ManaqeQuestionsMcq(mcq);
                System.out.println("result question = " + resultsQuestion);
            }
            //delete check is existe on the DB but not
            if(mcqsDB != null) {
                for (Mcq mcqDB : mcqsDB) {
                    Boolean isExist = false;
                    for (Mcq mcq : mcqs) {
                        if (mcq.getId_server() == mcqDB.getId_server()) {
                            isExist = true;
                        }
                    }

                    if (isExist == false) {
                        long result = mcqSQLiteAdapter.delete(mcqDB);
                    }
                }
            }

            mcqSQLiteAdapter.close();
            return null;
        }
        protected ArrayList<String> ManaqeQuestionsMcq(Mcq mcq)
        {
            ArrayList<Question> questions ;
            questionSQLiteAdapter = new QuestionSQLiteAdapter(context);
            ArrayList<Question> questionsDB = questionSQLiteAdapter.getAllQuestion();
            ArrayList<String> results = null ;
            ArrayList<String> resultsAnswers = null ;

            questions = mcq.getQuestions();

            for(Question question : questions)
            {
                question.setMcq(mcq);
                Question tempQuestion;

                tempQuestion = questionSQLiteAdapter.getQuestionById_server(question.getId_server());

                if(tempQuestion == null)
                {
                    //Add categ on the DB
                    long result = questionSQLiteAdapter.insert(question);
                    results.add(String.valueOf(result));
                }
                else
                {
                    long result = questionSQLiteAdapter.update(question);
                    results.add(String.valueOf(result));
                }

                resultsAnswers = ManaqeAnswersQuestion(question);
                System.out.println("result answer = " + resultsAnswers);
            }
            //delete check is existe on the DB but not
            if(questionsDB != null) {
                for (Question questionDB : questionsDB) {
                    Boolean isExist = false;
                    for (Question question : questions) {
                        if (question.getId_server() == questionDB.getId_server()) {
                            isExist = true;
                        }
                    }

                    if (isExist == false) {
                        long result = questionSQLiteAdapter.delete(questionDB);
                    }
                }
            }

            return results;

        }

        protected ArrayList<String> ManaqeAnswersQuestion(Question question)
        {
            answerSQLiteAdapter = new AnswerSQLiteAdapter(context);
            ArrayList<Answer> answersDB = answerSQLiteAdapter.getAllAnswer();
            ArrayList<Answer> answers ;
            ArrayList<String> results = null ;

            answers = question.getAnswers();

            for(Answer answer : answers)
            {
                answer.setQuestion(question);
                Answer tempAnswer;

                tempAnswer = answerSQLiteAdapter.getAnswerById_server(answer.getId_server());

                if(tempAnswer == null)
                {
                    //Add categ on the DB
                    long result = answerSQLiteAdapter.insert(answer);
                    results.add(String.valueOf(result));
                }
                else
                {
                    long result = answerSQLiteAdapter.update(answer);
                    results.add(String.valueOf(result));
                }

            }
            //delete check is existe on the DB but not
            if(answersDB != null) {
                for (Answer answerDB : answersDB) {
                    Boolean isExist = false;
                    for (Answer answer : answers) {
                        if (answer.getId_server() == answerDB.getId_server()) {
                            isExist = true;
                        }
                    }

                    if (isExist == false) {
                        long result = answerSQLiteAdapter.delete(answerDB);
                    }
                }
            }
            return results;

        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
        }
    }

}

