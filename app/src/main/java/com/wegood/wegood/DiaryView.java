package com.wegood.wegood;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DiaryView extends AppCompatActivity {
    SQLiteDatabase sqlitedb;
    DBmanager dbmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);
        Intent it = getIntent();
        String str_picture = it.getStringExtra("it_picture");
        String str_title = it.getStringExtra("it_title");
        String str_letter = it.getStringExtra("it_letter");

        TextView tv_title = (TextView) findViewById(R.id.title);
        TextView tv_letter = (TextView) findViewById(R.id.letter);
        ImageView lv_picture = (ImageView)findViewById(R.id.image);

        tv_title.append(": " + str_title);
        tv_letter.append(": " + str_letter);
    }
    public void home(View v) {
        Intent it = new Intent(this, Main.class);
        startActivity(it);
        finish();
    }
    public void revise(View v) {

    }
    public void delete(View v) {
        dbmanager = new DBmanager(this);
        sqlitedb = dbmanager.getReadableDatabase();
        Intent it = getIntent();
        String str_num = it.getStringExtra("it_num");
        String query = "delete from customers where num="+str_num;
        sqlitedb.execSQL(query);
        sqlitedb.close();
        dbmanager.close();
        Intent it2 = new Intent(this, Main.class);
        startActivity(it2);
        finish();
    }
}
