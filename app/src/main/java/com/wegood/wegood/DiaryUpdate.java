package com.wegood.wegood;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class DiaryUpdate extends AppCompatActivity {
    SQLiteDatabase sqlitedb;
    DBmanager dbmanager;
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_update);
        Intent it = getIntent();
        String str_picture = it.getStringExtra("it_picture");
        String str_title = it.getStringExtra("it_title");
        String str_letter = it.getStringExtra("it_letter");
        String str_day = it.getStringExtra("it_day");

        EditText et_title = (EditText)findViewById(R.id.title);
        EditText et_letter = (EditText)findViewById(R.id.letter);
        TextView tv_day = (TextView)findViewById(R.id.day);

        et_title.append(str_title);
        et_letter.append(str_letter);
        tv_day.append(str_day);
        String strCurYear = str_day.substring(0,4);
        String strCurMonth = str_day.substring(6,8);
        String strCurDay = str_day.substring(10,12);
        final int stryear = Integer.parseInt(strCurYear);
        final int strmonth = Integer.parseInt(strCurMonth);
        final int strday = Integer.parseInt(strCurDay);
        findViewById(R.id.day).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                new DatePickerDialog(DiaryUpdate.this, dateSetListener, stryear, strmonth-1, strday).show();
            }
        });

    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String st_year = Integer.toString(year);
            String st_month = String.format("%02d", (monthOfYear+1));
            String st_day = Integer.toString(dayOfMonth);


            TextView tv_day = (TextView) findViewById(R.id.day);
            String str_day = st_year + "년 " + st_month + "월 " + st_day + "일 ";
            tv_day.setText(null);
            tv_day.append(str_day);
        }

    };
    public void revise(View v) {
        Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        EditText et_title = (EditText) findViewById(R.id.title);
        String str_title = et_title.getText().toString();
        TextView tv_day = (TextView) findViewById(R.id.day);
        EditText et_letter = (EditText) findViewById(R.id.letter);
        String str_letter = et_letter.getText().toString();
        String str_day = tv_day.getText().toString();
        String str_num = it.getStringExtra("it_num");
        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getWritableDatabase();
            String sql = "update Diary set title = '" + str_title + "', letter = '" + str_letter + "', day = '" + str_day+ "' where num ='" + str_num + "'";
            sqlitedb.execSQL(sql);
            sqlitedb.close();
            dbmanager.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent it2 = new Intent(this, Main.class);
        it2.putExtra("it_id", str_id);
        startActivity(it2);
        finish();
    }
}
