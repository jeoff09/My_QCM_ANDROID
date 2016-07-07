package com.tactfactory.my_qcm.view.menu;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tactfactory.my_qcm.R;

/**
 * A simple {@link Fragment} subclass.
 *  Simple Fragment to Show the Legal Notices
 */
public class LegalNoticesFragment extends Fragment {


    public LegalNoticesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_legal_notices, container, false);

        //Disable Float Button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.INVISIBLE);


        return rootView;
    }

}
