package com.tactfactory.my_qcm.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.data.UserSQLiteAdapter;
import com.tactfactory.my_qcm.data.webservice.ConnectionWSAdapter;
import com.tactfactory.my_qcm.view.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

        ConnectionWSAdapter connectionWSAdapter;
        MyQCMConstants myQCMConstants;
        ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Todo: Get button and Call onClick Event :
         * On click Get the value on my 2 Edit Text and call the WebService(LoginChiff,pwdChiff)
         * to try connection ( Fragment Chargement ?)
         * if webService response == false not connected and show error pop_up
         * else get the jsonFlux and add on the Database and go to the Home Activity
         */
        //Define the layout associate with the Activity
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Call to create The Database
        UserSQLiteAdapter user = new UserSQLiteAdapter(this);
        user.open();

        // Close after Open
        user.close();
        final EditText etLogin = (EditText)this.findViewById(R.id.textEditLogin);
        final EditText etPassword = (EditText)this.findViewById(R.id.textEditPwd);
        final Button buttonConnexion = (Button)this.findViewById(R.id.buttonConnection);

        if (buttonConnexion != null) {
            buttonConnexion.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog=new ProgressDialog(LoginActivity.this);
                    dialog.setMessage("Tentative de connexion ...");
                    dialog.setCancelable(false);
                    dialog.setInverseBackgroundForced(false);
                    dialog.show();
                    final String login = etLogin.getText().toString();
                    final String password = etPassword.getText().toString();
                    System.out.println("login = " + login + " password = " + password);
                    connectionWSAdapter = new ConnectionWSAdapter();
                    connectionWSAdapter.ConnectionRequest(myQCMConstants.CONST_URL_LOGIN, login, password, new ConnectionWSAdapter.CallBack() {
                        @Override
                        public void methods(String reponse) {
                            System.out.println("Response login Activity = " + reponse);
                            if (reponse.equals("true") == true) {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                dialog.hide();
                                startActivity(intent);
                            }
                            else {
                                    dialog.hide();
                                    connectionWSAdapter.connectionErrorMessage(LoginActivity.this);
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
