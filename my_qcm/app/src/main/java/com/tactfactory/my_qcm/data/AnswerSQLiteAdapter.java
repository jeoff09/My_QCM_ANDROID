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
 * Helping Class For Answer
 */
public class AnswerSQLiteAdapter {

    /**
     *  Class context
     */
    private Context context;

    /**
     *   @see  AnswerSQLiteAdapter#getSchema()
     *   Define the name of the table inside the DB
     */
    protected static final String TABLE_ANSWER = "answer";

    /**
     *   @see  AnswerSQLiteAdapter#getSchema()
     *   Name of the col id inside Mobile Database
     *   id = identifier on the mobile DB
     */
    protected static final String COL_ID = "id";

    /**
     *   @see  AnswerSQLiteAdapter#getSchema()
     *   Name of the col id_server inside Mobile Database
     *   id_server = Id inside the webservice DB
     */
    protected static final String COL_ID_SERVER = "id_server";

    /**
     *   @see  AnswerSQLiteAdapter#getSchema()
     *   Name of the col ans inside Mobile Database
     *   ans = response value
     */
    protected static final String COL_ANS = "ans";

    /**
     *   @see  AnswerSQLiteAdapter#getSchema()
     *   Name of the col Is_true inside Mobile Database
     *   istrue = If this answer is right or not
     */
    protected static final String COL_IS_TRUE = "istrue";

    /**
     *   @see  AnswerSQLiteAdapter#getSchema()
     *   Name of the col question inside Mobile Database
     *   question = Question linked with this answer
     */
    protected static final String COL_QUESTION = "question";

    /**
     *   @see  AnswerSQLiteAdapter#getSchema()
     *   Name of the col updated_at inside Mobile Database
     *   updated_at = Last updated date of the Answer
     */
    protected static final String COL_UPDATED_AT = "updated_at";

    /**
     * Application Database
     **/
    private SQLiteDatabase db;
    /**
     * SQL open Helper for the DB
     */
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * Attribute context
     * @param context
     */
    public AnswerSQLiteAdapter(Context context){
        this.helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table Answer for Database
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
        String whereClausesUpdate = COL_ID_SERVER + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(answer.getId_server())};

        return db.update(TABLE_ANSWER, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Answer with his id.
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
     * Select a Answer with his id_server.
     * @param id_server
     * @return Answer
     */
    public Answer getAnswerById_server(int id_server){

        String[] cols = {COL_ID, COL_ID_SERVER, COL_ANS, COL_IS_TRUE, COL_QUESTION, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID_SERVER + "= ?";
        String[] whereArgsSelect = {String.valueOf(id_server)};

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
     * Get all Answer linked to question id_server
     * @return ArrayList<>
     */
    public ArrayList<Answer> getAllAnswerById_server_question(int id_server_question){
        ArrayList<Answer> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Answer>();
            // add typ into list
            do {
                Answer answer = this.cursorToItem(cursor);
                if( answer.getQuestion().getId_server() == id_server_question) {
                    result.add(this.cursorToItem(cursor));
                }
                else {
                    System.out.println("Not link to the question");
                }
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
        values.put(COL_QUESTION, answer.getQuestion().getId_server());
        values.put(COL_UPDATED_AT, answer.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Answer
     * get all element in temp items and add on constructor before return
     * Create a DateFormat for transform the StringValue to a valid Date
     * Call the adapter too get the question of this Answer
     * @param cursor
     * @return Answer
     */
    public Answer cursorToItem(Cursor cursor){
        QuestionSQLiteAdapter questionAdapter = new QuestionSQLiteAdapter(context);
        questionAdapter.open();
        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String ans = cursor.getString(cursor.getColumnIndex(COL_ANS));
        Boolean is_true = getBoolean(cursor.getInt(cursor.getColumnIndex(COL_IS_TRUE)));
        int question = cursor.getInt(cursor.getColumnIndex(COL_QUESTION));
        String s = cursor.getString(cursor.getColumnIndex(COL_UPDATED_AT));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

        try
        {
            date = simpleDateFormat.parse(s);
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }


        Answer result = new Answer(id,id_server,ans,is_true,date);
        result.setQuestion(questionAdapter.getQuestionById_server(question));

        questionAdapter.close();
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
}
