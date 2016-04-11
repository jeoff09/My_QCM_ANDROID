package com.tactfactory.my_qcm.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeoffrey on 06/04/2016.
 */
public class My_QCMSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "myqcm.sqlite";

    public My_QCMSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public My_QCMSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*db.execSQL(AnswerSQLiteAdapter.getSchema());
        db.execSQL(CategSQLiteAdapter.getSchema());
        db.execSQL(McqSQLiteAdapter.getSchema());
        db.execSQL(MediaSQLiteAdapter.getSchema());
        db.execSQL(QuestionSQLiteAdapter.getSchema());
        db.execSQL(ResultSQLiteAdapter.getSchema());
        db.execSQL(TeamSQLiteAdapter.getSchema());
        db.execSQL(TypSQLiteAdapter.getSchema());*/
        db.execSQL(UserSQLiteAdapter.getSchema());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
