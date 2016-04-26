package com.tactfactory.my_qcm.view;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends ListFragment {


    public HomeFragment() {

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_accueil,container,false);
        List<Categ> list = new ArrayList<Categ>();
        Date currentDate = new Date();
        Categ categ  = new Categ(1,2,"chant",currentDate);
        Categ categ1 = new Categ(2,3,"danse",currentDate);
        Categ categ2 = new Categ(3,4,"music",currentDate);

        list.add(categ);
        list.add(categ1);
        list.add(categ2);

         ArrayAdapter<Categ> arrayAdapter = new ArrayAdapter<Categ>(
                 getActivity(),
                R.layout.row_fragment_home,
                 R.id.item_list_home,
                list);
        setListAdapter(arrayAdapter);

        setRetainInstance(true);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ViewGroup viewGroup = (ViewGroup)v;
        TextView txt = (TextView)viewGroup.findViewById(R.id.item_list_home);
        Toast.makeText(getActivity(),txt.getText(),Toast.LENGTH_LONG).show();

    }
}
