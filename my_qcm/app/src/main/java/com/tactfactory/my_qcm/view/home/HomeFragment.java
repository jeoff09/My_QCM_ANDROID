package com.tactfactory.my_qcm.view.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home,container,false);
        /**
         * Todo : Get list Of Category in Database and create an array Adapter and set inside
         */
        List<Categ> list = new ArrayList<Categ>();
        Date currentDate = new Date();
        Categ categ  = new Categ(1,2,"chant",currentDate);
        Categ categ1 = new Categ(2,3,"danse",currentDate);
        Categ categ2 = new Categ(3,4,"music",currentDate);

        list.add(categ);
        list.add(categ1);
        list.add(categ2);

        // Create Array Adapter to set the Categories on the fragment list and in TextView
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
    /**
     * Todo : Get the value of Selected Item and send him by the Bundle
     * When The User select e item on list Call and pass to param the name of the Category
     */
    public void onListItemClick(ListView l, View v, int position, long id) {

        ViewGroup viewGroup = (ViewGroup)v;
        //Get the value
        TextView txt = (TextView)viewGroup.findViewById(R.id.item_list_home);
        // set the fragment initially
        ListMcqFragment fragment = new ListMcqFragment();
        // create a Bundle To store Value
        Bundle categBundle = new Bundle();
        // Put Messag inside The Bundle
        categBundle.putString("name",txt.getText().toString());
        //Set the Bundle on the Fragment
        fragment.setArguments(categBundle);
        //Fragment transaction
        FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }
}
