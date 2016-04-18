package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tactfactory.my_qcm.entity.Result;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 06/04/2016.
 * Helping Class For Result
 */
public class ResultSQLiteAdapter {

    private Context context;

    protected static final String TABLE_RESULT = "result";
    protected static final String COL_ID = "id";
    protected static final String COL_ID_SERVER_USER = "id_server_user";
    protected static final String COL_ID_SERVER_MCQ = "id_server_mcq";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public ResultSQLiteAdapter(Context context){
        helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table Result for the database ( not the same in phpMyadmin)
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_RESULT + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER_USER + " INTEGER NOT NULL, "
                + COL_ID_SERVER_MCQ + " INTEGER NOT NULL);";
    }

    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Result into DB
     * @param result
     * @return line result
     */
    public long insert(Result result){
        return db.insert(TABLE_RESULT, null, this.resultToContentValues(result));
    }

    /**
     * Delete Result with media object
     * @param result
     * @return line result
     */
    public long delete(Result result){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(result.getId())};

        return this.db.delete(TABLE_RESULT, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update Result in DB
     * @param result
     * @return line result
     */
    public long update(Result result){
        ContentValues valuesUpdate = this.resultToContentValues(result);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(result.getId())};

        return db.update(TABLE_RESULT, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Result with his Id.
     * @param id
     * @return Result
     */
    public Result getResult(int id){

        String[] cols = {COL_ID, COL_ID_SERVER_MCQ,COL_ID_SERVER_USER};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_RESULT, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Result result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Result
     * @return ArrayList<>
     */
    public ArrayList<Result> getAllResult(){
        ArrayList<Result> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Result>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Result to ContentValues
     * @param result
     * @return ContentValue
     */
    private ContentValues resultToContentValues(Result result){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER_MCQ, result.getId_server_mcq());
        values.put(COL_ID_SERVER_USER, result.getId_server_user());

        return values;
    }

    /**
     * Cursor convert to Result
     * get all element in temp items and add on constructor before return
     * @param cursor
     * @return Result
     */
    public Result cursorToItem(Cursor cursor){
        TypSQLiteAdapter typAdapter = new TypSQLiteAdapter(context);
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server_user = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER_USER));
        int id_server_mcq = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER_MCQ));


        Result result = new Result(id,id_server_user,id_server_mcq);

        return result;
    }

    /**
     * Get all Cursor in Result Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER_USER,COL_ID_SERVER_MCQ};
        Cursor cursor = db.query(TABLE_RESULT, cols, null, null, null, null, null);
        return cursor;
    }
}
