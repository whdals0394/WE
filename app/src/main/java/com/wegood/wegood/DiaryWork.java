package com.wegood.wegood;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiaryWork extends AppCompatActivity {
    SQLiteDatabase sqlitedb;
    DBmanager dbmanager;
    Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Date date = new Date();
        date.setDate(date.getDate()+1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_day = (TextView) findViewById(R.id.day);
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        String strCurYear = CurYearFormat.format(date);
        String strCurMonth = CurMonthFormat.format(date);
        String strCurDay = CurDayFormat.format(date);
        String str_day = strCurYear + "년 " + strCurMonth + "월 " + strCurDay + "일 ";
        tv_day.append(str_day);
        final int stryear = Integer.parseInt(strCurYear);
        final int strmonth = Integer.parseInt(strCurMonth);
        final int strday = Integer.parseInt(strCurDay);
        findViewById(R.id.day).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                new DatePickerDialog(DiaryWork.this, dateSetListener, stryear, strmonth, strday).show();
            }
        });


    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView tv_day = (TextView) findViewById(R.id.day);
            String str_day = year + "년 " + monthOfYear + "월 " + dayOfMonth + "일 ";
            tv_day.setText(null);
            tv_day.append(str_day);
        }

    };

    public void ok(View v) {
        Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        String str_pass = it.getStringExtra("it_pass");
        EditText et_title = (EditText) findViewById(R.id.title);
        String str_title = et_title.getText().toString();
        TextView tv_day = (TextView) findViewById(R.id.day);
        EditText et_letter = (EditText) findViewById(R.id.letter);
        String str_letter = et_letter.getText().toString();
        String str_day = tv_day.getText().toString();
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
