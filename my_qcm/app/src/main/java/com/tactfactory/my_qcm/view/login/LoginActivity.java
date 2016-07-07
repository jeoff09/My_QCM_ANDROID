package com.tactfactory.my_qcm.view.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tactfactory.my_qcm.R;
import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.configuration.Pwd;
import com.tactfactory.my_qcm.configuration.Utility;
import com.tactfactory.my_qcm.data.UserSQLiteAdapter;
import com.tactfactory.my_qcm.data.asynctaskManageUserDB.ManageUserDBAsyncTask;
import com.tactfactory.my_qcm.data.asynctaskManageUserDB.OnCompleted;
import com.tactfactory.my_qcm.data.webservice.ConnectionWSAdapter;
import com.tactfactory.my_qcm.data.webservice.UserWSAdapter;
import com.tactfactory.my_qcm.entity.User;
import com.tactfactory.my_qcm.view.home.HomeActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements OnCompleted{

    long result;
    ConnectionWSAdapter connectionWSAdapter;
    UserSQLiteAdapter userSQLiteAdapter;
    MyQCMConstants myQCMConstants;
    ProgressDialog dialog;
    ManageUserDBAsyncTask manageUserDBAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Define the layout associate with the Activity
        setContentView(R.layout.activity_login);
        final EditText etLogin = (EditText)this.findViewById(R.id.textEditLogin);
        final EditText etPassword = (EditText)this.findViewById(R.id.textEditPwd);
        final Button buttonConnexion = (Button)this.findViewById(R.id.buttonConnection);
        //Call to create The Database
        final UserSQLiteAdapter user = new UserSQLiteAdapter(this);

        if (buttonConnexion != null) {
            buttonConnexion.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final String login = etLogin.getText().toString();
                    final String password = etPassword.getText().toString();
                    boolean isConnected = Utility.CheckInternetConnection(LoginActivity.this);
                    System.out.println("is connected = "+ isConnected);
                    if(isConnected != false) {
                        dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setMessage("Tentative de connexion ...");
                        dialog.setCancelable(false);
                        dialog.setInverseBackgroundForced(false);
                        dialog.show();

                        connectionWSAdapter = new ConnectionWSAdapter();
                        System.out.println("url" + myQCMConstants.CONST_URL_LOGIN);
                        connectionWSAdapter.ConnectionRequest(myQCMConstants.CONST_URL_LOGIN, login, password, new ConnectionWSAdapter.CallBack() {
                            @Override
                            public void methods(String response) {
                                System.out.println("Response login Activity = " + response);
                                if (response.equals("false") == true) {
                                    dialog.hide();
                                    connectionWSAdapter.connectionErrorMessage(LoginActivity.this);
                                } else {
                                    User userConnected = UserWSAdapter.JsonToItem(response);
                                    String pwd = Pwd.toHexString(Pwd.sha512(password));
                                    userConnected.setPwd(pwd);
                                    System.out.println("JsonToItem OK");
                                    System.out.println("User created by json : "
                                            + userConnected.getUsername() + " idServer = "
                                            + userConnected.getId_server() + " password = " + userConnected.getPwd());
                                    if (userConnected instanceof User) {

                                        userSQLiteAdapter = new UserSQLiteAdapter(LoginActivity.this);
                                        userSQLiteAdapter.open();
                                        System.out.println("userSQLiteAdapter.open() OK");
                                        //Get all users existing in local database
                                        ArrayList<User> users = userSQLiteAdapter.getAllUser();
                                        //Get user authenticated in local database
                                        User userDB = userSQLiteAdapter.getUserByIdServer(userConnected.getId_server());

                                        if (users != null && userDB == null) {
                                            //Manage only one account authorized
                                            UserMessage(LoginActivity.this, userConnected);
                                        } else if (userDB != null) {
                                            //If user exist and have to update
                                            //----------------------------------
                                            if (userDB.getUpdated_at() != null) {
                                                if (userConnected.getUpdated_at().compareTo(userDB.getUpdated_at()) > 0) {
                                                    //Try update User
                                                    try {
                                                        manageUserDBAsyncTask = new ManageUserDBAsyncTask(LoginActivity.this, userConnected);
                                                        manageUserDBAsyncTask.execute(userConnected).get();
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    } catch (ExecutionException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            //user exist and is up to date Launch Main Activity
                                            //---------------------------------------------------
                                            userSQLiteAdapter.close();
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.putExtra("FirstConnection", false);
                                            intent.putExtra("UserIdServer", userConnected.getId_server());
                                            dialog.hide();
                                            startActivity(intent);
                                        } else {
                                            result = userSQLiteAdapter.insert(userConnected);
                                            userSQLiteAdapter.close();
                                            if (result > 0) {
                                                dialog.hide();
                                                Toast.makeText(LoginActivity.this, MyQCMConstants.CONST_MESS_CREATEDB + userConnected.getUsername(), Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                intent.putExtra("FirstConnection",true);
                                                intent.putExtra("UserIdServer", userConnected.getId_server());
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(LoginActivity.this, MyQCMConstants.CONST_MESS_CREATEDBERROR, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }else {
                        userSQLiteAdapter = new UserSQLiteAdapter(LoginActivity.this);
                        userSQLiteAdapter.open();
                        //Get all users existing in local database
                        ArrayList<User> users = userSQLiteAdapter.getAllUser();
                        //Get user in database for authentification
                        String pwd = Pwd.toHexString(Pwd.sha512(password));
                        User userDB = userSQLiteAdapter.getUserWithLoginPassword(login, pwd);
                        userSQLiteAdapter.close();
                        //if user exist in database
                        if (userDB != null) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("FirstConnection",false);
                            intent.putExtra("UserIdServer", userDB.getId_server());
                            startActivity(intent);

                            //else if user does not exist but another one storaged in database
                        } else if (users != null && userDB == null) {
                            //Show error connection message
                            ConnectionMessage(LoginActivity.this,2);
                        } else {
                            //Show error connection message
                            ConnectionMessage(LoginActivity.this,1);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onCompleted(String response) {
        Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG).show();
    }

    // overrideBack button to prevent the user from leaving the questionnaire
    @Override
    public void onBackPressed() {
    }

    private void UserMessage(final Context context, final User user) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        System.out.println("OK ManageUserMessage");
        System.out.println("OK set alert dialog");
        alertDialogBuilder.setTitle("Attention compte utilisateur déjà existant !");
        alertDialogBuilder.setMessage("Si vous continuez, l'application effaçera toutes les données relatives à cet utilisateur. " +
                "Êtes-vous sûr de vouloir continuer ?");
        alertDialogBuilder.setPositiveButton("OUI",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("OK click on yes");

                        //Delete database
                        Boolean isDeleted = context.deleteDatabase(MyQCMConstants.APP_DB_NAME + MyQCMConstants.APP_DB_EXTENSION);
                        if (isDeleted == true) {
                            System.out.println("OK database deleted");
                            //New User have to be created
                            UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter(context);
                            userSQLiteAdapter.open();
                            result = userSQLiteAdapter.insert(user);
                            userSQLiteAdapter.close();
                            if (result > 0) {
                                Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDB + user.getUsername(), Toast.LENGTH_SHORT).show();
                                //Launch main activity
                                Intent intent = new Intent(context, HomeActivity.class);
                                intent.putExtra("FirstConnection", true);
                                intent.putExtra("UserIdServer", user.getId_server());
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDBERROR, Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        } else {
                            Toast.makeText(context, MyQCMConstants.CONST_MESS_CREATEDBERROR, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        alertDialogBuilder.setNegativeButton("NON",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //After logout redirect user to Login Activity
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        //Closing all the activities
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //Add new Flag to start new activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void ConnectionMessage(final Context context, int idError){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Erreur de connection");
        if (idError == 1) {
            alertDialogBuilder.setMessage("Pas de connexion Internet. Verifier votre connexion." +
                    " Si le problème persiste, veuillez contacter votre administrateur.");
        }else if (idError == 2) {
            alertDialogBuilder.setMessage("Pas de connexion Internet." +
                    " Un compte existant a été détecté sur ce terminal mobile. Si c'est votre première connexion," +
                    " un accès Internet est nécessaire. Sinon, veuillez essayer à nouveau." +
                    " Si le problème persiste, veuillez contacter votre administrateur.");
        }

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //After logout redirect user to Login Activity
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                //Closing all the activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Add new Flag to start new activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }



}
