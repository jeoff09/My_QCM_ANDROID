package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Media;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 */
public class MediaSQLiteAdapter {

    private Context context;

    protected static final String TABLE_MEDIA = "media";
    protected static final String COL_ID = "id";
    protected static final String COL_ID_SERVER = "id_server";
    protected static final String COL_NAME = "name";
    protected static final String COL_URL = "url";
    protected static final String COL_TYP = "typ";
    protected static final String COL_UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public MediaSQLiteAdapter(Context context){
        helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_MEDIA + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " TEXT NOT NULL, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_URL + " TEXT NOT NULL, "
                + COL_TYP + " INTEGER NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL);";
    }

    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Media into DB
     * @param media
     * @return line result
     */
    public long insert(Media media){
        return db.insert(TABLE_MEDIA, null, this.mediaToContentValues(media));
    }

    /**
     * Delete Media with media object
     * @param media
     * @return line result
     */
    public long delete(Media media){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(media.getId())};

        return this.db.delete(TABLE_MEDIA, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update Media in DB
     * @param media
     * @return line result
     */
    public long update(Media media){
        ContentValues valuesUpdate = this.mediaToContentValues(media);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(media.getId())};

        return db.update(TABLE_MEDIA, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Media with his Id.
     * @param id
     * @return Media
     */
    public Media getMedia(int id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME,COL_URL,COL_TYP, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_MEDIA, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Media result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Media
     * @return ArrayList<>
     */
    public ArrayList<Media> getAllMedia(){
        ArrayList<Media> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Media>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Media to ContentValues
     * @param media
     * @return ContentValue
     */
    private ContentValues mediaToContentValues(Media media){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, media.getId_server());
        values.put(COL_NAME, media.getName());
        values.put(COL_URL,media.getUrl());
        values.put(COL_TYP, media.getTyp().getId());
        values.put(COL_UPDATED_AT, media.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Media
     * get all element in temp items and add on constructor before return
     * @param cursor
     * @return Media
     */
    public Media cursorToItem(Cursor cursor){
        TypSQLiteAdapter typAdapter = new TypSQLiteAdapter(context);
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
        String url = cursor.getString(cursor.getColumnIndex(COL_URL));
        int typ = cursor.getInt(cursor.getColumnIndex(COL_TYP));
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


        Media result = new Media(id,id_server,name,url,typAdapter.getTyp(typ),date);

        return result;
    }

    /**
     * Get all Cursor in MEDIA Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME,COL_URL,COL_TYP, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_MEDIA, cols, null, null, null, null, null);
        return cursor;
    }
}
