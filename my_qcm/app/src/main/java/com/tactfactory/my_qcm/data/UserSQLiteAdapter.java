package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tactfactory.my_qcm.entity.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 */
public class UserSQLiteAdapter {

    protected static final String TABLE_USER = "user";
    protected static final String COL_ID = "id";
    protected static final String COL_ID_SERVER = "id_server";
    protected static final String COL_USERNAME = "username";
    protected static final String COL_PWD = "pwd";
    protected static final String COL_EMAIL = "email";
    protected static final String COL_LAST_LOGIN = "last_login";
    protected static final String COL_UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public UserSQLiteAdapter(Context context){
        helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
    }

    /**
     * Create database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_USER + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_USERNAME + " TEXT NOT NULL, "
                + COL_PWD + "TEXT NOT NULL,"
                + COL_EMAIL+ " INTEGER NOT NULL, "
                + COL_LAST_LOGIN+ " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL);";
    }

    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert user into DB
     * @param user
     * @return line result
     */
    public long insert(User user){
        return db.insert(TABLE_USER, null, this.userToContentValues(user));
    }

    /**
     * Delete User with user object
     * @param user
     * @return line result
     */
    public long delete(User user){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(user.getId())};

        return this.db.delete(TABLE_USER, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update User in DB
     * @param user
     * @return line result
     */
    public long update(User user){
        ContentValues valuesUpdate = this.userToContentValues(user);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(user.getId())};

        return db.update(TABLE_USER, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a User with his Id.
     * @param id
     * @return User
     */
    public User getUser(long id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_USERNAME,COL_PWD, COL_EMAIL, COL_LAST_LOGIN, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_USER, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        User result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    public User getUserWithLoginPassword(String login, String password){
        String[] cols = {COL_ID, COL_ID_SERVER, COL_USERNAME,COL_PWD, COL_EMAIL, COL_LAST_LOGIN, COL_UPDATED_AT};
        String whereClausesSelect = COL_USERNAME + "=? AND " + COL_PWD + " =?";
        String[] whereArgsSelect = {String.valueOf(login), String.valueOf(password)};

        Cursor cursor = db.query(TABLE_USER, cols, whereClausesSelect, whereArgsSelect, null, null, null);
        User result = null;

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all User
     * @return ArrayList<>
     */
    public ArrayList<User> getAllUser(){
        ArrayList<User> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<User>();
            // add user into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert user to ContentValues
     * @param user
     * @return ContentValue
     */
    private ContentValues userToContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, user.getId_server());
        values.put(COL_USERNAME, user.getUsername());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_LAST_LOGIN, user.getLast_login().toString());
        values.put(COL_UPDATED_AT, user.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to User
     * @param cursor
     * @return User
     */
    public User cursorToItem(Cursor cursor){
        User result = new User();
        result.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
        result.setId_server(cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER)));
        result.setUsername(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
        result.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
        //result.setLast_login((Date)cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
        //result.setUpdated_at();

        return result;
    }

    /**
     * Get all Cursor in User Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = { COL_ID, COL_ID_SERVER, COL_USERNAME,COL_PWD ,COL_EMAIL, COL_LAST_LOGIN, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_USER, cols, null, null, null, null, null);
        return cursor;
    }
}
