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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 * Helping Class For Mcq
 */
public class McqSQLiteAdapter {

    /**
     * Value of the Class Context
     */
    private Context context;

    /**
     * name of the Table inside Mobile Database
     */
    protected static final String TABLE_MCQ = "mcq";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Name of the col id inside Mobile Database
     *   id = identifier on the mobile DB
     */
    protected static final String COL_ID = "id";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Name of the col id_server inside Mobile Database
     *   id_server = identifier on the webService DB
     */
    protected static final String COL_ID_SERVER = "id_server";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Name of the col name inside Mobile Database
     *   name = value of the MCQ name
     */
    protected static final String COL_NAME = "name";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   dateEnd of the col datEnd inside Mobile Database
     *   DateEnd = when the MCQ become unavailable
     */
    protected static final String COL_DATE_END = "dateEnd";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Name of the col dateStart inside Mobile Database
     *   dateStart = When the MCQ become available
     */
    protected static final String COL_DATE_START = "dateStart";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Name of the col duration inside Mobile Database
     *   duration = How many time the user have to complete the MCQ
     */
    protected static final String COL_DURATION = "duration";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Value of the col Categ inside Mobile Database
     *   id_categ = Value of the Categ associate of this MCQ
     */
    protected static final String COL_CATEG = "id_categ";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Name of the col updated_at inside Mobile Database
     *   updated_at = Date of the last update of this MCQ
     */
    protected static final String COL_UPDATED_AT = "updated_at";

    /**
     *   @see  McqSQLiteAdapter#getSchema()
     *   Name of the col is_actif inside Mobile Database
     *   is_actif = if the qcm is available
     */
    protected static final String COL_IS_ACTIF = "isActif";
    /**
     * Database of the Application
     */
    private SQLiteDatabase db;
    /**
     * SQL Open helper
     */
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public McqSQLiteAdapter(Context context){
        this.helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table MCQ for the database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_MCQ + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_NAME + " TEXT NOT NULL,"
                + COL_DATE_END  + " TEXT ,"
                + COL_DATE_START + " TEXT NOT NULL,"
                + COL_CATEG + " INTEGER NOT NULL,"
                + COL_IS_ACTIF + " INTEGER NOT NULL,"
                + COL_DURATION + " INTEGER NOT NULL, "
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
        System.out.println("MCQ INSERT  dae de fin : " + mcq.getDateEnd());
        return this.db.delete(TABLE_MCQ, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update Mcq in DB
     * @param mcq
     * @return line result
     */
    public long update(Mcq mcq){
        System.out.println("mcq . is actif " + mcq.getIsActif());
        ContentValues valuesUpdate = this.mcqToContentValues(mcq);
        String whereClausesUpdate = COL_ID_SERVER + "= ?";
        System.out.println("MCQ update  dae de fin : " + mcq.getDateEnd());
        String[] whereArgsUpdate =  {String.valueOf(mcq.getId_server())};

        return db.update(TABLE_MCQ, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Mcq with his Id.
     * @param id
     * @return Mcq
     */
    public Mcq getMcq(int id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME,COL_DATE_END,COL_DATE_START,COL_DURATION, COL_CATEG,COL_IS_ACTIF,COL_UPDATED_AT};
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
     * Select a Mcq with his Id_server.
     * @param id_server
     * @return Mcq
     */
    public Mcq getMcqById_server(int id_server){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME,COL_DATE_END,COL_DATE_START,COL_DURATION,COL_CATEG,COL_IS_ACTIF,COL_UPDATED_AT};
        String whereClausesSelect = COL_ID_SERVER + "= ?";
        String[] whereArgsSelect = {String.valueOf(id_server)};

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

    public ArrayList<Mcq> getAllMcqAvailable(int id_categ){
        ArrayList<Mcq> result = null;
        Cursor cursor = getAllCursor();
        Date date = Calendar.getInstance().getTime();
        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Mcq>();
            // add typ into list
            do {
                Mcq tempMcq = this.cursorToItem(cursor);
                System.out.println(
                        "date de fin get All = " + tempMcq.getDateEnd() +
                        "");
                if(tempMcq.getCategory().getId_server() == id_categ) {
                    if (tempMcq.getIsActif() == true) {
                        if (tempMcq.getDateStart().compareTo(date) < 0) {
                            if (tempMcq.getDateEnd() != null) {
                                if (tempMcq.getDateEnd().compareTo(date) > 0) {
                                    result.add(tempMcq);
                                } else {
                                    System.out.println("This MCQ is not more available");
                                }
                            } else {
                                result.add(tempMcq);
                            }
                        } else {
                            System.out.println("Is to early to complete this QCM");
                        }
                    } else {
                        System.out.println("The MCQ is not available");
                    }
                }else{
                    System.out.println("The MCQ is not with this categ");
                }

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
        if(mcq.getDateEnd() != null) {
            values.put(COL_DATE_END, mcq.getDateEnd().toString());
        }
        values.put(COL_DATE_START, mcq.getDateStart().toString());
        values.put(COL_CATEG,mcq.getCategory().getId_server());
        values.put(COL_IS_ACTIF, mcq.getIsActif());
        values.put(COL_DURATION, mcq.getDuration());
        values.put(COL_UPDATED_AT, mcq.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Mcq
     * get all element in temp items and add on constructor before return
     * Call adapter for get the Categ of this Mcq
     * Create date Format for parse Strings Date to Dates
     * @param cursor
     * @return Mcq
     */
    public Mcq cursorToItem(Cursor cursor){
        CategSQLiteAdapter cateSQLite = new CategSQLiteAdapter(context);
        cateSQLite.open();
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        String end = cursor.getString(cursor.getColumnIndex(COL_DATE_END));
        String start = cursor.getString(cursor.getColumnIndex(COL_DATE_START));
        int duration = cursor.getInt(cursor.getColumnIndex(COL_DURATION));
        int categ = cursor.getInt(cursor.getColumnIndex(COL_CATEG));
        Boolean is_actif = getBoolean(cursor.getInt(cursor.getColumnIndex(COL_IS_ACTIF)));
        String update = cursor.getString(cursor.getColumnIndex(COL_UPDATED_AT));
        Date date_end = null;
        Date date_start = new Date();
        Date date_updated = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

        try
        {
            date_updated = simpleDateFormat.parse(update);
            if(end != null && !end.isEmpty()  ) {
                System.out.println("Date de fin du cursor  is not null");
                date_end = simpleDateFormat.parse(end);
            }
            date_start = simpleDateFormat.parse(start);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }


        Mcq result = new Mcq(id,id_server,name,duration,cateSQLite.getCategById_server(categ),date_updated);

        if (date_end != null)
        {
            result.setDateEnd(date_end);
        }
        if (date_start != null)
        {
            result.setDateStart(date_start);
        }
        result.setIsActif(is_actif);
        cateSQLite.close();
        return result;
    }

    /**
     * Function to transform int to boolean
     * @param columnIndex
     * @return boolean
     */
    public boolean getBoolean(int columnIndex) {
        if (  columnIndex == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get all Cursor in Mcq Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME,COL_DATE_END,COL_DATE_START,COL_DURATION,COL_CATEG,COL_IS_ACTIF, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_MCQ, cols, null, null, null, null, null);
        return cursor;
    }
}
