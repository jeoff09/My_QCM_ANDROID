package com.tactfactory.my_qcm.view.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.configuration.Utility;
import com.tactfactory.my_qcm.data.CategSQLiteAdapter;
import com.tactfactory.my_qcm.data.webservice.CategoryWSAdapter;
import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends ListFragment {

    ArrayList<Categ> categories;
    private boolean isServerReachable;
    CategoryWSAdapter categoryWSAdapter;
    CategSQLiteAdapter categSQLiteAdapter;
    ProgressDialog dialog;
    int userIdServer = 0;
    public HomeFragment() {

    }
    
    @Override
    /**
     * When the home fragment is create
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home,container,false);


        //Set the visibilty of Floating Button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(fab.VISIBLE);

        Bundle args = getArguments();
        if(args != null) {
            // check is first connection
            if (args.containsKey("FirstConnection")) {
                boolean isFirstConnection = getArguments().getBoolean("FirstConnection");

                    // check UserId and send request to get catg of this User
                if (args.containsKey("UserIdServer")) {
                    userIdServer = getArguments().getInt("UserIdServer", 0);
                    categoryWSAdapter = new CategoryWSAdapter(getActivity().getBaseContext());
                    isServerReachable = Utility.CheckInternetConnection(getActivity().getBaseContext());

                    //If is the first Connection on the User and if the connection is available
                    if (userIdServer != 0 && isFirstConnection == true && isServerReachable == true) {

                        // New Load Dialog during the request
                        dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Récupération des informations pour votre première connexion...");
                        dialog.setCancelable(false);
                        dialog.setInverseBackgroundForced(false);
                        dialog.show();

                        // get categ
                        categoryWSAdapter.getCategoryRequest(
                                userIdServer,
                                MyQCMConstants.CONST_URL_GET_CATEGORIES,
                                new CategoryWSAdapter.CallBack() {
                                    @Override
                                    public void methods(String response) {
                                        System.out.println("Response Main FragmentList FirstConnection : " + response);
                                            // On error Hide dialog and show error message 1
                                        if (response.equals("OnFailure") == true) {
                                            dialog.hide();
                                            System.out.println(response);
                                            System.out.println("context = " + getActivity().getBaseContext());
                                            categoryWSAdapter.CategoryErrorMessage(getActivity().getBaseContext(), 1);

                                        } else {

                                            //If success insert in DB if categ in categorie
                                            categories = CategoryWSAdapter.ResponseToList(response);
                                            if (categories.isEmpty() != false) {
                                                dialog.hide();
                                                System.out.println(response);
                                                System.out.println("context = " + getActivity().getBaseContext());
                                                categoryWSAdapter.CategoryErrorMessage(getActivity(), 2);
                                            } else {
                                                int error = 0;
                                                System.out.println(response);
                                                categSQLiteAdapter = new CategSQLiteAdapter(getActivity().getBaseContext());
                                                categSQLiteAdapter.open();
                                                for (Categ category : categories) {
                                                    long result = categSQLiteAdapter.insert(category);
                                                    if (result < 0) {
                                                        error = 1;
                                                    }
                                                }
                                                categSQLiteAdapter.close();

                                                if (error == 1) {
                                                    dialog.hide();
                                                    categoryWSAdapter.CategoryErrorMessage(getActivity().getBaseContext(), 3);
                                                }

                                                // Create the  Array Adapter to show categories
                                                ArrayAdapter<Categ> arrayAdapter = new ArrayAdapter<Categ>(
                                                        getActivity(),
                                                        R.layout.row_fragment_home,
                                                        R.id.item_list_home,
                                                        categories);
                                                setListAdapter(arrayAdapter);
                                                dialog.hide();
                                            }
                                        }
                                    }
                                });
                        setRetainInstance(true);
                    }
                    //If is not the first connection
                    else {
                        if (userIdServer != 0 && isServerReachable == true) {
                            categoryWSAdapter.getCategoryRequest(userIdServer,
                                    MyQCMConstants.CONST_URL_GET_CATEGORIES);
                            setRetainInstance(true);
                        }

                        //Get Categ in database
                        categSQLiteAdapter = new CategSQLiteAdapter(getActivity().getBaseContext());
                        categSQLiteAdapter.open();
                        categories = categSQLiteAdapter.getAllCateg();
                        categSQLiteAdapter.close();

                        if (categories != null) {
                            // set the value of categories in list
                            ArrayAdapter<Categ> arrayAdapter = new ArrayAdapter<Categ>(
                                    getActivity(),
                                    R.layout.row_fragment_home,
                                    R.id.item_list_home,
                                    categories);
                            setListAdapter(arrayAdapter);
                        }
                        else {
                            System.out.println("No categories");
                        }
                    }
                }
            }
            // if not args in param of the fragment show error message
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getBaseContext()).create();
            alertDialog.setTitle("Message d'erreur");
            alertDialog.setMessage("Un problème est survenu lors de votre connexion. Veuillez-vous reconnecter svp." +
                    "Si le problème persiste, contactez votre administrateur.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        return rootView;
    }

    /**
     * On click item in list start the MCQ fragment
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //Get the value of categ
        int id_categ = categories.get(position).getId_server();

        // set the fragment initially
        ListMcqFragment fragment = new ListMcqFragment();

        // create a Bundle To store Value
        Bundle categBundle = new Bundle();

        // Put Messag inside The Bundle
        categBundle.putInt("id_categ", id_categ);
        categBundle.putInt("id_user", userIdServer);

        //Set the Bundle on the Fragment
        fragment.setArguments(categBundle);

        //Fragment transaction
        FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }
}
