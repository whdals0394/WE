package com.wegood.wegood;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryWork extends AppCompatActivity {
    SQLiteDatabase sqlitedb;
    DBmanager dbmanager;
    Date date = new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_work);
    }
    public void ok(View v) {

        EditText et_title = (EditText)findViewById(R.id.title);
        String str_title = et_title.getText().toString();

        EditText et_letter = (EditText)findViewById(R.id.letter);
        String str_letter = et_letter.getText().toString();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        String str_day = simple.format(date);
        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", str_title);
            values.put("letter", str_letter);
            values.put("day", str_day);
            long newRowId = sqlitedb.insert("Diary", null, values);
            sqlitedb.close();
            dbmanager.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent it = new Intent(this, Main.class);
        startActivity(it);
        finish();
    }
}
