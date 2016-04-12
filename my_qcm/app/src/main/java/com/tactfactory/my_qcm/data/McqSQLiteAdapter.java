package com.tactfactory.my_qcm.data;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Mcq;
import android.content.Context;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 */
public class McqSQLiteAdapter {

    private Context context;


    protected static final String TABLE_MCQ = "mcq";
    protected static final String COL_ID = "id";
    protected static final String COL_ID_SERVER = "id_server";
    protected static final String COL_NAME = "name";
    protected static final String COL_DATE_END = "dateEnd";
    protected static final String COL_DATE_START = "dateStart";
    protected static final String COL_DURATION = "duration";
    protected static final String COL_CATEG = "id_categ";
    protected static final String COL_UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public McqSQLiteAdapter(Context context){
        helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_MCQ + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_NAME + "TEXT NOT NULL,"
                + COL_DATE_END  + "TEXT ,"
                + COL_DATE_START + "TEXT NOT NULL,"
                + COL_CATEG + "INTEGER NOT NULL"
                + COL_DURATION + "INTEGER NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL);";
    }


    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Mcq into DB
     * @param mcq
     * @return line result
     */
    public long insert(Mcq mcq){
        return db.insert(TABLE_MCQ, null, this.mcqToContentValues(mcq));
    }

    /**
     * Delete Mcq with mcq object
     * @param mcq
     * @return line result
     */
    public long delete(Mcq mcq){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(mcq.getId())};

        return this.db.delete(TABLE_MCQ, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update Mcq in DB
     * @param mcq
     * @return line result
     */
    public long update(Mcq mcq){
        ContentValues valuesUpdate = this.mcqToContentValues(mcq);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(mcq.getId())};

        return db.update(TABLE_MCQ, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Mcq with his Id.
     * @param id
     * @return Mcq
     */
    public Mcq getMcq(int id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME,COL_DATE_END,COL_DATE_START,COL_DURATION, COL_CATEG,COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_MCQ, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Mcq result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Mcq
     * @return ArrayList<>
     */
    public ArrayList<Mcq> getAllMcq(){
        ArrayList<Mcq> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Mcq>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Mcq to ContentValues
     * @param mcq
     * @return ContentValue
     */
    private ContentValues mcqToContentValues(Mcq mcq){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, mcq.getId_server());
        values.put(COL_NAME, mcq.getName());
        values.put(COL_DATE_END, mcq.getDateEnd().toString());
        values.put(COL_DATE_START, mcq.getDateStart().toString());
        values.put(COL_CATEG,mcq.getCategory().getId());
        values.put(COL_DURATION, mcq.getDuration());
        values.put(COL_UPDATED_AT, mcq.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Categ
     * get all element in temp items and add on constructor before return
     * @param cursor
     * @return Typ
     */
    public Mcq cursorToItem(Cursor cursor){
        CategSQLiteAdapter cateSQLite = new CategSQLiteAdapter(context);
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        String end = cursor.getString(cursor.getColumnIndex(COL_DATE_END));
        String start = cursor.getString(cursor.getColumnIndex(COL_DATE_START));
        int duration = cursor.getInt(cursor.getColumnIndex(COL_DURATION));
        int categ = cursor.getInt(cursor.getColumnIndex(COL_CATEG));
        String update = cursor.getString(cursor.getColumnIndex(COL_UPDATED_AT));

        Date date_end = new Date();
        Date date_start = new Date();
        Date date_updated = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try
        {
            date_updated = simpleDateFormat.parse(update);
            date_end = simpleDateFormat.parse(end);
            date_start = simpleDateFormat.parse(start);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }


        Mcq result = new Mcq(id,id_server,name,duration,cateSQLite.getCateg(categ),date_updated);

        if (date_end != null)
        {
            result.setDateEnd(date_end);
        }
        if (date_start != null)
        {
            result.setDateStart(date_start);
        }
        return result;
    }

    /**
     * Get all Cursor in Mcq Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME,COL_DATE_END,COL_DATE_START,COL_DURATION,COL_CATEG, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_MCQ, cols, null, null, null, null, null);
        return cursor;
    }
}
