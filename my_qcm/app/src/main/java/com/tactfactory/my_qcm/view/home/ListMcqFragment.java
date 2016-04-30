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

        String message = getArguments().getString("message");
        List<String> list = new ArrayList<String>();

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        ViewGroup viewGroup = (ViewGroup)v;
        TextView txt = (TextView)viewGroup.findViewById(R.id.item_list_mcq);
        Toast.makeText(getActivity(), txt.getText(), Toast.LENGTH_LONG).show();

    }
}
