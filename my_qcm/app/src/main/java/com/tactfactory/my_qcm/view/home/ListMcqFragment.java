package com.tactfactory.my_qcm.view.home;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.Fragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.entity.Categ;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ProtoConcept GJ on 27/04/2016.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class ListMcqFragment extends ListFragment {

    public ListMcqFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the fragment
       ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_list_mcq,container,false);

        //Get the Value pass on the Bundle
        String message = getArguments().getString("name");

        List<String> list = new ArrayList<String>();

        /**
         * Todo : GetCategByName(message) in CategAdapter-> getMcqByIdCateg(cat.id) in McqAdapter
         * and add the List of Mcq in the Array Adapter of Mcq
         */
        list.add(message);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.row_fragment_list_mcq,
                R.id.item_list_mcq,
                list);
        setListAdapter(arrayAdapter);

        setRetainInstance(true);

        return rootView;
    }

    @Override
    /**
     * Todo : Get the value of Selected Item and send him by the Bundle and call Questionnaire
     * When The User select e item on list Call and pass to param the name of the Mcq
     */
    public void onListItemClick(ListView l, View v, int position, long id) {
        ViewGroup viewGroup = (ViewGroup)v;
        TextView txt = (TextView)viewGroup.findViewById(R.id.item_list_mcq);
        Toast.makeText(getActivity(), txt.getText(), Toast.LENGTH_LONG).show();

    }
}
