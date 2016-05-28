package com.tactfactory.my_qcm.view.home;

import android.os.AsyncTask;
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
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
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

        // get the list of Categ in the Web server
        final CategoryWSAdapter categoryWSAdapter = new CategoryWSAdapter();
        final CategSQLiteAdapter categSQLiteAdapter = new CategSQLiteAdapter(getActivity().getApplicationContext());

        // open DB to get the list Categ on DB
        categSQLiteAdapter.open();
        ArrayList<Categ> categories = categSQLiteAdapter.getAllCateg();
        categSQLiteAdapter.close();

        // Create Array Adapter to set the CategoriesDB on the fragment list and in TextView
        ArrayAdapter<Categ> arrayAdapter = new ArrayAdapter<Categ>(
                getActivity(),
                R.layout.row_fragment_home,
                R.id.item_list_home,
                categories);
        setListAdapter(arrayAdapter);

        categoryWSAdapter.getCategoryRequest(1, MyQCMConstants.CONST_URL_GET_CATEGORIES, new CategoryWSAdapter.CallBack() {
            @Override
            public void methods(ArrayList<Categ> response) {

                if (response.isEmpty() == false) {
                    // get the list of categ in Flux and add on listView
                    ArrayList<Categ> list = response;
                    ArrayList<String>listResult = null;
                    // Create Array Adapter to set the Categories Flux on the fragment list and in TextView
                    ArrayAdapter<Categ> arrayAdapter = new ArrayAdapter<Categ>(
                            getActivity(),
                            R.layout.row_fragment_home,
                            R.id.item_list_home,
                            list);
                    setListAdapter(arrayAdapter);

                  // Call the AsyncTask to add Categ on the DB and returns the list of result
                    try {
                        listResult = new ManageCategDBTask().execute(list).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    System.out.println(listResult);

                }
            }
        });


        Date currentDate = new Date();


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

    /**
     * Asyntask To Manage Categories get on the Flow in the DB
     */
    public class ManageCategDBTask extends AsyncTask <ArrayList<Categ>,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(ArrayList<Categ>... params) {
            ArrayList<Categ> categories = new ArrayList<Categ>();
            ArrayList<String> results = new ArrayList<String>();
            categories = params[0];
            CategSQLiteAdapter categSQLiteAdapter = new CategSQLiteAdapter(getActivity().getApplicationContext());
            categSQLiteAdapter.open();

            for(Categ categ : categories)
            {
                Categ tempCateg ;
                //Try to find a Categ with this id_server
                tempCateg = categSQLiteAdapter.getCategById_server(categ.getId_server());

                //If Categ not exist on Mobile DB
                if(tempCateg == null)
                {
                    //Add categ on the DB
                    long result = categSQLiteAdapter.insert(categ);
                    System.out.print("Add a new Categ sucess = " + result);
                    results.add(String.valueOf(result));
                }
                else
                {
                    long result = categSQLiteAdapter.update(categ);
                    System.out.print("Update a  Categ Number of row changed = " + result);
                    results.add(String.valueOf(result));
                }
            }
         
            
            
            categSQLiteAdapter.close();


            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
        }
    }
}
