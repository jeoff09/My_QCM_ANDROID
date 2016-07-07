package com.tactfactory.my_qcm.view.menu;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.data.UserSQLiteAdapter;
import com.tactfactory.my_qcm.entity.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *  * Simple Fragment to Show a list of element about user
 */
public class ProfileFragment extends Fragment {
    UserSQLiteAdapter userSQLiteAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.INVISIBLE);

        userSQLiteAdapter = new UserSQLiteAdapter(getActivity().getBaseContext());
        userSQLiteAdapter.open();
        ArrayList<User> users = new ArrayList<>();
        users = userSQLiteAdapter.getAllUser();
        if (users != null) {
            User userDB = users.get(0);
            System.out.println("Userid = " + userDB.getUsername());
            String username = userDB.getUsername();
            RelativeLayout relativeLayoutProfilHeader = (RelativeLayout) rootView.findViewById(R.id.profile_layout_header);

            //Header
            RelativeLayout relativeLayoutProfil = (RelativeLayout) relativeLayoutProfilHeader.findViewById(R.id.profile_layout);
            TextView textViewProfilName = (TextView) relativeLayoutProfil.findViewById(R.id.user_profile_name);
            textViewProfilName.setText(username);

            //Content profil
            LinearLayout linearLayoutContent = (LinearLayout) relativeLayoutProfilHeader.findViewById(R.id.profile_layout_content);
            TextView textViewProfilUsername = (TextView) linearLayoutContent.findViewById(R.id.user_profil_identifiant);
            textViewProfilUsername.setText(MyQCMConstants.CONST_PROFIL_USERNAME + " " + username);

            TextView textViewProfilEmail = (TextView) linearLayoutContent.findViewById(R.id.user_profil_email);
            textViewProfilEmail.setText(MyQCMConstants.CONST_PROFIL_EMAIL + " " + userDB.getEmail());

        }



        // Inflate the layout for this fragment
        return rootView;
    }

}
