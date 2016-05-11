package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tactfactory.my_qcm.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 * Helping Class For User
 */
public class UserSQLiteAdapter {

    /**
     *  Class context
     */
    private Context context;

    /**
     * Name of the Table inside Mobile DB
     */
    protected static final String TABLE_USER = "user";

    /**
     *   @see  UserSQLiteAdapter#getSchema()
     *   Name of the col id inside Mobile Database
     *   id = identifier on the mobile DB
     */
    protected static final String COL_ID = "id";

    /**
     *   @see  UserSQLiteAdapter#getSchema()
     *   Name of the col id_server inside Mobile Database
     *   id_server = identifier on the WebService DB
     */
    protected static final String COL_ID_SERVER = "id_server";

    /**
     *   @see  UserSQLiteAdapter#getSchema()
     *   Name of the col username inside Mobile Database
     *   username of the user
     */
    protected static final String COL_USERNAME = "username";

    /**
     *   @see  UserSQLiteAdapter#getSchema()
     *   Name of the col pwd inside Mobile Database
     *   pwd of the user
     */
    protected static final String COL_PWD = "pwd";

    /**
     *   @see  UserSQLiteAdapter#getSchema()
     *   Name of the col email inside Mobile Database
     *   email = email of the user
     */
    protected static final String COL_EMAIL = "email";

    /**
     *   @see  UserSQLiteAdapter#getSchema()
     *   Name of the col last_login inside Mobile Database
     *   last_login = Date of the last_login on the APP
     */
    protected static final String COL_LAST_LOGIN = "last_login";

    /**
     *   @see  UserSQLiteAdapter#getSchema()
     *   Name of the col updated_at inside Mobile Database
     *   updated_at = Date of the last Update of this User
     */
    protected static final String COL_UPDATED_AT = "updated_at";

    /**
     * DataBase of the Application
     */
    private SQLiteDatabase db;
    /**
     * SQLiteOpenHelper help to manage the Database
     */
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public UserSQLiteAdapter(Context context){
        this.helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table User for the database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_USER + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_USERNAME + " TEXT NOT NULL, "
                + COL_PWD + " TEXT NOT NULL,"
                + COL_EMAIL+ " TEXT NOT NULL, "
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
    public User getUser(int id){

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
        values.put(COL_PWD, user.getPwd());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_LAST_LOGIN, user.getLast_login().toString());
        values.put(COL_UPDATED_AT, user.getUpdated_at().toString());
        return values;
    }

    /**
     * Cursor convert to User
     * get all element in temp items and add on constructor before return
     * Creation of SimpleDateFormat to parse string to date
     * @param cursor
     * @return User
     */
    public User cursorToItem(Cursor cursor){

        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String username = cursor.getString(cursor.getColumnIndex(COL_USERNAME));
        String email= cursor.getString(cursor.getColumnIndex(COL_EMAIL));
        String pwd= cursor.getString(cursor.getColumnIndex(COL_PWD));
        String last_login = cursor.getString(cursor.getColumnIndex(COL_LAST_LOGIN));
        String updated_at = cursor.getString(cursor.getColumnIndex(COL_UPDATED_AT));

        Date date_last_login = new Date();
        Date date_updated_at = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try
        {
            date_last_login = simpleDateFormat.parse(last_login);
            date_updated_at = simpleDateFormat.parse(updated_at);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }


        User result = new User(id,id_server,username,email,pwd,date_last_login,date_updated_at);


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
