package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.my_qcm.entity.Categ;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 * Helping Class For Categ
 */
public class CategSQLiteAdapter {

    /**
     *  Class context
     */
    private Context context;
    /**
     *   Name of the Table Categ inside Mobile Database
     */
    protected static final String TABLE_CATEG = "categ";

    /**
     *   @see  CategSQLiteAdapter#getSchema()
     *   Name of the col id inside Mobile Database
     *   id = identifier on the mobile DB
     */
    protected static final String COL_ID = "id";

    /**
     *   @see  CategSQLiteAdapter#getSchema()
     *   Name of the col id_server inside Mobile Database
     *   id_server = identifier ont the WebService Database
     */
    protected static final String COL_ID_SERVER = "id_server";

    /**
     *   @see  CategSQLiteAdapter#getSchema()
     *   Name of the col name inside Mobile Database
     *   name = Categ value
     */
    protected static final String COL_NAME = "name";

    /**
     *   @see  CategSQLiteAdapter#getSchema()
     *   Name of the col update_at inside Mobile Database
     *   update_at = Date of the last update of this Categ
     */
    protected static final String COL_UPDATED_AT = "updated_at";

    /**
     * Database of the Application
     */
    private SQLiteDatabase db;

    /**
     *  SQLiteOpenHelper to help manage DB
     */
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public CategSQLiteAdapter(Context context){
        this.helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table Categ for Database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_CATEG + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL);";
    }


    /**
     * Open The Database
     */
    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Categ into DB
     * @param categ
     * @return line result
     */
    public long insert(Categ categ){
        return db.insert(TABLE_CATEG, null, this.categToContentValues(categ));
    }

    /**
     * Delete Categ with Categ object
     * @param categ
     * @return line result
     */
    public long delete(Categ categ){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(categ.getId())};

        return this.db.delete(TABLE_CATEG, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update Categ in DB
     * @param categ
     * @return line result
     */
    public long update(Categ categ){
        ContentValues valuesUpdate = this.categToContentValues(categ);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(categ.getId())};

        return db.update(TABLE_CATEG, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Categ with his id.
     * @param id
     * @return Categ
     */
    public Categ getCateg(int id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_CATEG, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Categ result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Categ
     * @return ArrayList<>
     */
    public ArrayList<Categ> getAllCateg(){
        ArrayList<Categ> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Categ>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Categ to ContentValues
     * @param categ
     * @return ContentValue
     */
    private ContentValues categToContentValues(Categ categ){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, categ.getId_server());
        values.put(COL_NAME, categ.getName());
        values.put(COL_UPDATED_AT, categ.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Categ
     * get all element in temp items and add on constructor before return
     * Create a DateFormat to parse string to DateValue
     * @param cursor
     * @return Typ
     */
    public Categ cursorToItem(Cursor cursor){

        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        // String to Date try if working
        String s = cursor.getString(cursor.getColumnIndex(COL_UPDATED_AT));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try
        {
            date = simpleDateFormat.parse(s);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }


        Categ result = new Categ(id_server,name,date);
        result.setId(id);
        return result;
    }

    /**
     * Get all Cursor in Categ Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_CATEG, cols, null, null, null, null, null);
        return cursor;
    }
}
