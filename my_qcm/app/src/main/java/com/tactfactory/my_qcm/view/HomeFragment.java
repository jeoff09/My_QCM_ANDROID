package com.tactfactory.my_qcm.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tactfactory.my_qcm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listCateg;

    public HomeFragment() {

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        listCateg = (ListView) view.findViewById(R.id.listViewCategMCQ);
        List<String> list = new ArrayList<String>();
        list.add("add");
        list.add("zz");
        list.add("ee");
        list.add("dd");
        list.add("fff");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this.getContext(),
                android.R.layout.simple_expandable_list_item_1,
                list);
        listCateg.setAdapter(arrayAdapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accueil, container, false);



    }


}
