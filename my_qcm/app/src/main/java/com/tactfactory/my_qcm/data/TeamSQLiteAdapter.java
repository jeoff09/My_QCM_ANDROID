package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.my_qcm.entity.Team;
import com.tactfactory.my_qcm.entity.Typ;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 * Helping Class For Team
 */
public class TeamSQLiteAdapter {

    /**
     *  Class context
     */
    private Context context;

    /**
     * Name of the Table inside DB
     */
    protected static final String TABLE_TEAM = "team";

    /**
     *   @see  TeamSQLiteAdapter#getSchema()
     *   Name of the col id inside Mobile Database
     *   id = identifier on the mobile DB
     */
    protected static final String COL_ID = "id";

    /**
     *   @see  TeamSQLiteAdapter#getSchema()
     *   Name of the col id_server inside Mobile Database
     *   id_server = identifier on the WebService DB
     */
    protected static final String COL_ID_SERVER = "id_server";

    /**
     *   @see  TeamSQLiteAdapter#getSchema()
     *   Name of the col name inside Mobile Database
     *   name = Name of this team
     */
    protected static final String COL_NAME = "name";

    /**
     *   @see  TeamSQLiteAdapter#getSchema()
     *   Name of the col updated_at inside Mobile Database
     *   updated_at = Date of the last updated of this team
     */
    protected static final String COL_UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public TeamSQLiteAdapter(Context context){
        this.helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table Team
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_TEAM + " ("
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
     * Insert Team into DB
     * @param team
     * @return line result
     */
    public long insert(Team team){
        return db.insert(TABLE_TEAM, null, this.teamToContentValues(team));
    }

    /**
     * Delete Team with team object
     * @param team
     * @return line result
     */
    public long delete(Team team){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(team.getId())};

        return this.db.delete(TABLE_TEAM, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update team in DB
     * @param team
     * @return line result
     */
    public long update(Team team){
        ContentValues valuesUpdate = this.teamToContentValues(team);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(team.getId())};

        return db.update(TABLE_TEAM, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Team with his Id.
     * @param id
     * @return Team
     */
    public Team getTeam(int id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_TEAM, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Team result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Team
     * @return ArrayList<>
     */
    public ArrayList<Team> getAllTeam(){
        ArrayList<Team> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Team>();
            // add team into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Team to ContentValues
     * @param team
     * @return ContentValue
     */
    private ContentValues teamToContentValues(Team team){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, team.getId_server());
        values.put(COL_NAME, team.getName());
        values.put(COL_UPDATED_AT, team.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Team
     * get all element in temp items and add on constructor before return
     * Creation of DateFormat to Transform String to Date
     * @param cursor
     * @return Team
     */
    public Team cursorToItem(Cursor cursor){

        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
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


        Team result = new Team(id,id_server,name,date);

        return result;
    }

    /**
     * Get all Cursor in Team Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER, COL_NAME, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_TEAM, cols, null, null, null, null, null);
        return cursor;
    }
}
