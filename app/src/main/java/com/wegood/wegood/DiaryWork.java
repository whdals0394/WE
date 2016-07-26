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
        Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        String str_pass = it.getStringExtra("it_pass");
        EditText et_title = (EditText)findViewById(R.id.title);
        String str_title = et_title.getText().toString();

        EditText et_letter = (EditText)findViewById(R.id.letter);
        String str_letter = et_letter.getText().toString();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        String str_day = simple.format(date);
        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getWritableDatabase();
            String sql = "insert into Diary values(null,'"
                    + str_id + "','" + str_pass + "','" + /*str_picture* + "','" + */str_day + "','" + str_title + "','" + str_letter + "')";
            sqlitedb.execSQL(sql);
            sqlitedb.close();
            dbmanager.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent it2 = new Intent(this, Main.class);
        startActivity(it2);
        finish();
    }
}
