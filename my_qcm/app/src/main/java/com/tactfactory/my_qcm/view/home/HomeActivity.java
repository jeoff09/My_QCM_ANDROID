package com.tactfactory.my_qcm.view.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import  android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.data.UserSQLiteAdapter;
import com.tactfactory.my_qcm.data.webservice.CategoryWSAdapter;
import com.tactfactory.my_qcm.data.webservice.McqWSAdapter;
import com.tactfactory.my_qcm.entity.Mcq;
import com.tactfactory.my_qcm.entity.User;
import com.tactfactory.my_qcm.view.menu.LegalNoticesFragment;
import com.tactfactory.my_qcm.view.login.LoginActivity;
import com.tactfactory.my_qcm.view.menu.ProfileFragment;
import com.tactfactory.my_qcm.view.menu.RulesFragment;
import com.tactfactory.my_qcm.view.menu.HelpFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // set the fragment initially
        HomeFragment fragment = new HomeFragment();

        //frameLayout in app_bar_home.xml
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        // if parama send on create activity
        if (extras != null) {
            Bundle bundle = new Bundle();
            // test if First connection
            if (extras.containsKey("FirstConnection")) {
                boolean isFirstConnection = intent.getBooleanExtra("FirstConnection", false);
                if (isFirstConnection == true) {
                    bundle.putBoolean("FirstConnection", true);
                } else {
                    bundle.putBoolean("FirstConnection", false);
                }
            }
            // Get the User ID
            if (extras.containsKey("UserIdServer")) {
                int userIdServer = intent.getIntExtra("UserIdServer", 0);
                bundle.putInt("UserIdServer",userIdServer);
            }
            //Set fragment's arguments
            fragment.setArguments(bundle);
        }
        //Start  fragement
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set element off Floating Button if is invisible set visible
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(fab.VISIBLE);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("Aide");
                alertDialog.setIcon(R.drawable.ic_menu_help);
                alertDialog.setMessage("Pour accéder aux questionnaires,"
                        + " Sélectionnez un élément dans la liste");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        //Set menu layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    /**
     * ON item Select in the NaviguationDrower
     *  Create Fragment call on the selected menu and make a transaction to replace the actual
     */
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profil_menu) {

            ProfileFragment fragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.home_menu) {
            // Set element in Fragment transaction for the Home fragment
            HomeFragment fragment = new HomeFragment();
            //Set argument to MainFragmentList
            Bundle categoryBundle = new Bundle();
            categoryBundle.putBoolean("FirstConnection",false);

            //Get user's IdServer
            UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter(HomeActivity.this);
            userSQLiteAdapter.open();
            ArrayList<User> users = userSQLiteAdapter.getAllUser();
            userSQLiteAdapter.close();
            if (users.size() == 1) {
                for (User user : users) {
                    categoryBundle.putInt("UserIdServer",user.getId_server());
                }
            }else{
                categoryBundle.putInt("UserIdServer",0);
            }

            fragment.setArguments(categoryBundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.rules_menu) {
            RulesFragment fragment = new RulesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.legal_mentions_menu) {
            LegalNoticesFragment fragment = new LegalNoticesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.help_menu) {
            HelpFragment fragment = new HelpFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
        else if(id == R.id.logout_menu)
        {
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            //Closing all the activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //Add new Flag to start new activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
