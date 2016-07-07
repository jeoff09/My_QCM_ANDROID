package com.tactfactory.my_qcm.data.asynctaskManageUserDB;

import android.content.Context;
import android.os.AsyncTask;

import com.tactfactory.my_qcm.configuration.MyQCMConstants;
import com.tactfactory.my_qcm.data.UserSQLiteAdapter;
import com.tactfactory.my_qcm.entity.User;

/**
 * Asyntask to manage User in connection
 * Created by jeoffrey on 05/07/2016.
 */
public class ManageUserDBAsyncTask  extends AsyncTask<User, Void, String> {

    private OnCompleted onCompleted;
    private User user;

    public ManageUserDBAsyncTask(OnCompleted context, User user){
        this.onCompleted = context;
        this.user = user;
    }

    /**
     * Update User Async Task
     * @param params
     * @return String message
     */
    @Override
    protected String doInBackground(User... params) {
        long result;
        String resultMessage;
        UserSQLiteAdapter userSQLiteAdapter = new UserSQLiteAdapter((Context)onCompleted);
        userSQLiteAdapter.open();

        //Update user in database
        result = userSQLiteAdapter.update(user);
        System.out.println("update user OK");
        userSQLiteAdapter.close();
        if(result > 0){
            resultMessage = MyQCMConstants.CONST_MESS_UPDATEDB;
            return resultMessage;
        }else{
            resultMessage = MyQCMConstants.CONST_MESS_UPDATEDBERROR;
            return resultMessage;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        onCompleted.onCompleted(s);
    }
}

