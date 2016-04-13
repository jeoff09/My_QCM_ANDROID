package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.my_qcm.entity.Answer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 */
public class AnswerSQLiteAdapter {

    private Context context;

    protected static final String TABLE_ANSWER = "answer";
    protected static final String COL_ID = "id";
    protected static final String COL_ID_SERVER = "id_server";
    protected static final String COL_ANS = "ans";
    protected static final String COL_IS_TRUE = "istrue";
    protected static final String COL_QUESTION = "question";
    protected static final String COL_UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public AnswerSQLiteAdapter(Context context){
        helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_ANSWER + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_ANS + " TEXT NOT NULL, "
                + COL_IS_TRUE + " INTEGER NOT NULL, "
                + COL_QUESTION + " INTEGER NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL);";
    }

    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Answer into DB
     * @param answer
     * @return line result
     */
    public long insert(Answer answer){
        return db.insert(TABLE_ANSWER, null, this.answerToContentValues(answer));
    }

    /**
     * Delete Answer with Answer object
     * @param answer
     * @return line result
     */
    public long delete(Answer answer){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(answer.getId())};

        return this.db.delete(TABLE_ANSWER, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update Answer in DB
     * @param answer
     * @return line result
     */
    public long update(Answer answer){
        ContentValues valuesUpdate = this.answerToContentValues(answer);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(answer.getId())};

        return db.update(TABLE_ANSWER, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Answer with his Id.
     * @param id
     * @return Answer
     */
    public Answer getAnswer(int id){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_ANS, COL_IS_TRUE, COL_QUESTION, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_ANSWER, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Answer result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Answer
     * @return ArrayList<>
     */
    public ArrayList<Answer> getAllAnswer(){
        ArrayList<Answer> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Answer>();
            // add team into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Answer to ContentValues
     * @param answer
     * @return ContentValue
     */
    private ContentValues answerToContentValues(Answer answer){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, answer.getId_server());
        values.put(COL_ANS, answer.getAns());
        values.put(COL_IS_TRUE, answer.getIsTrue());
        values.put(COL_QUESTION, answer.getQuestion().toString());
        values.put(COL_UPDATED_AT, answer.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Answer
     * get all element in temp items and add on constructor before return
     * @param cursor
     * @return Answer
     */
    public Answer cursorToItem(Cursor cursor){
        QuestionSQLiteAdapter questionAdapter = new QuestionSQLiteAdapter(context);
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String ans = cursor.getString(cursor.getColumnIndex(COL_ANS));
        Boolean is_true = getBoolean(cursor.getInt(cursor.getColumnIndex(COL_IS_TRUE)));
        int question = cursor.getInt(cursor.getColumnIndex(COL_QUESTION));
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


        Answer result = new Answer(id,id_server,ans,is_true,questionAdapter.getQuestion(question),date);

        return result;
    }

    /**
     * Get all Cursor in Answer Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = { COL_ID, COL_ID_SERVER, COL_ANS, COL_IS_TRUE, COL_QUESTION, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_ANSWER, cols, null, null, null, null, null);
        return cursor;
    }

    public boolean getBoolean(int columnIndex) {
        if (  columnIndex == 0) {
            return false;
        } else {
            return true;
        }
    }
}
