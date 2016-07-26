package com.wegood.wegood;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jun on 2016-07-25.
 */
public class DBmanager extends SQLiteOpenHelper {
    public DBmanager(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("creeate table Diary (id text primary key, pass text, picture text, day text, title text, letter text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


