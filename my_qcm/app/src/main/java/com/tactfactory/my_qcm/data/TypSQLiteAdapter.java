package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;

import com.tactfactory.my_qcm.entity.Typ;
import com.tactfactory.my_qcm.entity.User;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by jeoffrey on 06/04/2016.
 * Helping Class For Typ
 */
public class TypSQLiteAdapter {

    /**
     *  Class context
     */
    private Context context;

    /**
     * Name of the Table Inside Mobile Database
     */
    protected static final String TABLE_TYP = "typ";

    /**
     *   @see  TypSQLiteAdapter#getSchema()
     *   Name of the col id inside Mobile Database
     *   id = identifier on the mobile DB
     */
    protected static final String COL_ID = "id";

    /**
     *   @see  TypSQLiteAdapter#getSchema()
     *   Name of the col id_server inside Mobile Database
     *   id_server = identifier on the WebService DB
     */
    protected static final String COL_ID_SERVER = "id_server";

    /**
     *   @see  TypSQLiteAdapter#getSchema()
     *   Name of the col name inside Mobile Database
     *   name = Value of Typ
     */
    protected static final String COL_NAME = "name";

    /**
     *   @see  TypSQLiteAdapter#getSchema()
     *   Name of the col updated_at inside Mobile Database
     *   updated_at = Date of the last update
     */
    protected static final String COL_UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public TypSQLiteAdapter(Context context){
        this.helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table Typ for database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_TYP + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL);";
    }


    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Typ into DB
     * @param typ
     * @return line result
     */
    public long insert(Typ typ){
        return db.insert(TABLE_TYP, null, this.typToContentValues(typ));
    }

    /**
     * Delete Typ with typ object
     * @param typ
     * @return line result
     */
    public long delete(Typ typ){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(typ.getId())};

        return this.db.delete(TABLE_TYP, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update typ in DB
     * @param typ
     * @return line result
     */
    public long update(Typ typ){
        ContentValues valuesUpdate = this.typToContentValues(typ);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(typ.getId())};

        return db.update(TABLE_TYP, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Typ with his Id.
     * @param id
     * @return Typ
     */
    public Typ getTyp(int id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_TYP, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Typ result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Typ
     * @return ArrayList<>
     */
    public ArrayList<Typ> getAllTyp(){
        ArrayList<Typ> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Typ>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Typ to ContentValues
     * @param typ
     * @return ContentValue
     */
    private ContentValues typToContentValues(Typ typ){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, typ.getId_server());
        values.put(COL_NAME, typ.getName());
        values.put(COL_UPDATED_AT, typ.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Typ
     * get all element in temp items and add on constructor before return
     * Creation of DateFormat to parse String to Date
     * @param cursor
     * @return Typ
     */
    public Typ cursorToItem(Cursor cursor){

        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        String s = cursor.getString(cursor.getColumnIndex(COL_UPDATED_AT));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try
        {
             date = simpleDateFormat.parse(s);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }


        Typ result = new Typ(id,id_server,name,date);

        return result;
    }

    /**
     * Get all Cursor in Typ Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_TYP, cols, null, null, null, null, null);
        return cursor;
    }
}
