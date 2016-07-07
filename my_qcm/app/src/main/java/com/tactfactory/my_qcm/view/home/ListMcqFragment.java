package com.tactfactory.my_qcm.view.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.configuration.Pwd;
import com.tactfactory.my_qcm.configuration.Utility;
import com.tactfactory.my_qcm.data.McqSQLiteAdapter;
import com.tactfactory.my_qcm.data.QuestionSQLiteAdapter;
import com.tactfactory.my_qcm.data.webservice.McqWSAdapter;
import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Mcq;
import com.tactfactory.my_qcm.entity.Question;
import com.tactfactory.my_qcm.view.questionnaire.QuestionnaireActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ProtoConcept GJ on 27/04/2016.
 */
/**
 * Manage list of MCQ
 * A simple {@link Fragment} subclass.
 */
public class ListMcqFragment extends ListFragment {

    McqWSAdapter mcqWSAdapter;
    McqSQLiteAdapter mcqSQLiteAdapter;
    ArrayList<Mcq> mcqs;
    boolean isConnected;
    int id_user;
    int categ_id;
    public ListMcqFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_list_mcq,container,false);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.VISIBLE);

         mcqWSAdapter = new McqWSAdapter(getActivity().getBaseContext());
         mcqSQLiteAdapter = new McqSQLiteAdapter(getActivity().getBaseContext());

        //get element on args
         categ_id = getArguments().getInt("id_categ");
         id_user = getArguments().getInt("id_user");
        System.out.println("ID_server de la catégorie est " + categ_id);
        // open DB to get the list mcq available on DB
        mcqSQLiteAdapter.open();
        mcqs = mcqSQLiteAdapter.getAllMcqAvailable(categ_id);
        mcqSQLiteAdapter.close();

        // If list is not null set on adapter
        if(mcqs != null) {
            ArrayAdapter<Mcq> arrayAdapter = new ArrayAdapter<Mcq>(
                    getActivity(),
                    R.layout.row_fragment_list_mcq,
                    R.id.item_list_mcq,
                    mcqs);
            setListAdapter(arrayAdapter);
        }
        //Check internet connection
        isConnected = Utility.CheckInternetConnection(getActivity().getBaseContext());
        // If connected send async request to get list of mcq with this categ and user
        if(isConnected == true) {
            mcqWSAdapter.getMcqRequest(id_user, categ_id, MyQCMConstants.CONST_URL_GET_MCQs);
        }
        else {
            System.out.println("No connexion for get Mcq");
        }
        setRetainInstance(true);

        return rootView;
    }

    /**
     * On click in item list MCQ , check if questions in this MCQ
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        ViewGroup viewGroup = (ViewGroup)v;
        int id_mcq = mcqs.get(position).getId_server();
        QuestionSQLiteAdapter questionSQLiteAdapter = new  QuestionSQLiteAdapter(getActivity());
        questionSQLiteAdapter.open();
        ArrayList<Question> questions = new ArrayList<>();
        questions = questionSQLiteAdapter.getAllQuestionById_server_MCQ(id_mcq);

        if (questions != null)
        {
            Intent intent = new Intent(getActivity().getBaseContext(), QuestionnaireActivity.class);
            intent.putExtra("id_mcq", id_mcq);
            intent.putExtra("id_user",id_user);
            System.out.println("Id envoyer au questionnaire =" + id_mcq);
            getActivity().startActivity(intent);
            startActivity(intent);
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Choix du questionnaire");
            alertDialog.setMessage("Il n'y a pas de question liées à ce questionnaire." +
                    "Veuillez contacter votre administrateur.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }
}
