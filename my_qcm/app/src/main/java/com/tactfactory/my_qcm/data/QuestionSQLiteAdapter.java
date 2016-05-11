package com.tactfactory.my_qcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tactfactory.my_qcm.entity.Categ;
import com.tactfactory.my_qcm.entity.Mcq;
import com.tactfactory.my_qcm.entity.Question;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jeoffrey on 06/04/2016.
 * Helping Class For Question
 */
public class QuestionSQLiteAdapter {

    /**
     * Context of the class
     */
    private Context context;
    /**
     * name of the class inside the Mobile Database
     */
    protected static final String TABLE_QUESTION = "question";

    /**
     *   @see  QuestionSQLiteAdapter#getSchema()
     *   Name of the col id inside Mobile Database
     *   id = identifier on the mobile DB
     */
    protected static final String COL_ID = "id";

    /**
     *   @see  QuestionSQLiteAdapter#getSchema()
     *   Name of the col id_server inside Mobile Database
     *   id_server = identifier on the WebService DB
     */
    protected static final String COL_ID_SERVER = "id_server";

    /**
     *   @see  QuestionSQLiteAdapter#getSchema()
     *   Name of the col ques inside Mobile Database
     *   ques = entitlted of the question
     */
    protected static final String COL_QUES = "ques";

    /**
     *   @see  QuestionSQLiteAdapter#getSchema()
     *   Name of the col mcq inside Mobile Database
     *   mcq = MCQ linked to this Question
     */
    protected static final String COL_MCQ = "mcq";

    /**
     *   @see  QuestionSQLiteAdapter#getSchema()
     *   Name of the col media inside Mobile Database
     *   media = Media linked to this Question
     */
    protected static final String COL_MEDIA = "media";

    /**
     *   @see  QuestionSQLiteAdapter#getSchema()
     *   Name of the col updated_at inside Mobile Database
     *   updated_at = Date of the last update of this Question
     */
    protected static final String COL_UPDATED_AT = "updated_at";

    private SQLiteDatabase db;
    private My_QCMSQLiteOpenHelper helper;

    /**
     * Helper object to create access db
     * @param context
     */
    public QuestionSQLiteAdapter(Context context){
        helper = new My_QCMSQLiteOpenHelper(context,My_QCMSQLiteOpenHelper.DB_NAME,null,1);
        this.context = context;
    }

    /**
     * Create table Question for database
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_QUESTION + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_SERVER + " INTEGER NOT NULL, "
                + COL_QUES + " TEXT NOT NULL, "
                + COL_MCQ + " INTEGER NOT NULL,"
                + COL_MEDIA+ " INTEGER,"
                + COL_UPDATED_AT + " TEXT NOT NULL);";
    }


    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Question into DB
     * @param question
     * @return line result
     */
    public long insert(Question question){
        return db.insert(TABLE_QUESTION, null, this.questionToContentValues(question));
    }

    /**
     * Delete Question with question object
     * @param question
     * @return line result
     */
    public long delete(Question question){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(question.getId())};

        return this.db.delete(TABLE_QUESTION, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Update Question in DB
     * @param question
     * @return line result
     */
    public long update(Question question){
        ContentValues valuesUpdate = this.questionToContentValues(question);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(question.getId())};

        return db.update(TABLE_QUESTION, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Question with his Id.
     * @param id
     * @return Question
     */
    public Question getQuestion(int id){

        String[] cols = {COL_ID, COL_ID_SERVER,COL_QUES ,COL_MCQ, COL_MEDIA,COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        // create SQL request
        Cursor cursor = db.query(TABLE_QUESTION, cols, whereClausesSelect, whereArgsSelect, null, null, null);

        Question result = null;

        // if SQL request return a result
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            result = cursorToItem(cursor);
        }
        return result;
    }

    /**
     * Get all Question
     * @return ArrayList<>
     */
    public ArrayList<Question> getAllQuestion(){
        ArrayList<Question> result = null;
        Cursor cursor = getAllCursor();

        // if cursor contains result
        if (cursor.moveToFirst()){
            result = new ArrayList<Question>();
            // add typ into list
            do {
                result.add(this.cursorToItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    /**
     * Convert Question to ContentValues
     * @param question
     * @return ContentValue
     */
    private ContentValues questionToContentValues(Question question){
        ContentValues values = new ContentValues();
        values.put(COL_ID_SERVER, question.getId_server());
        values.put(COL_QUES, question.getQues());
        values.put(COL_MCQ, question.getMcq().getId());
        values.put(COL_MEDIA, question.getMedia().getId());
        values.put(COL_UPDATED_AT, question.getUpdated_at().toString());

        return values;
    }

    /**
     * Cursor convert to Question
     * get all element in temp items and add on constructor before return
     * Call adapter for get the mcq and if he exist the media of this question
     * @param cursor
     * @return Question
     */
    public Question cursorToItem(Cursor cursor){
        McqSQLiteAdapter mcqAdapter     = new McqSQLiteAdapter(context);
        MediaSQLiteAdapter mediaAdapter = new MediaSQLiteAdapter(context);

        int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        int id_server = cursor.getInt(cursor.getColumnIndex(COL_ID_SERVER));
        String ques = cursor.getString(cursor.getColumnIndex(COL_QUES));
        int mcq = cursor.getInt(cursor.getColumnIndex(COL_MCQ));
        int media = cursor.getInt(cursor.getColumnIndex(COL_MEDIA));

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


        Question result = new Question(id,id_server,ques,date,mcqAdapter.getMcq(mcq));

        if(media != 0)
        {
            result.setMedia(mediaAdapter.getMedia(media));
        }
        return result;
    }

    /**
     * Get all Cursor in Question Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_ID_SERVER,COL_QUES, COL_MCQ,COL_MEDIA, COL_UPDATED_AT};
        Cursor cursor = db.query(TABLE_QUESTION, cols, null, null, null, null, null);
        return cursor;
    }
}
