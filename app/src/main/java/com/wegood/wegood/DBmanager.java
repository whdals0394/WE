package com.wegood.wegood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBmanager extends SQLiteOpenHelper {
    public DBmanager(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Diary(num integer primary key autoincrement, id text, pass text, picture text, day text, title text, letter text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Diary;");
        onCreate(db);
    }
}

