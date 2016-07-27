package com.wegood.wegood;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends AppCompatActivity {
    SQLiteDatabase sqlitedb;
    DBmanager dbmanager;
    final int REQ_CODE_SELECT_IMAGE = 100;
    String str_id;
    String d = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.view);
        Intent it = getIntent();
        str_id = it.getStringExtra("it_id");

        Toast.makeText(this, str_id, Toast.LENGTH_LONG).show();
        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            String sql = "select * from Diary where id ='" + str_id + "'Order By day DESC";
            Cursor cursor = sqlitedb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                final String day = cursor.getString(cursor.getColumnIndex("day"));
                final String title = cursor.getString(cursor.getColumnIndex("title"));
                final String id = cursor.getString(cursor.getColumnIndex("id"));
                final String picture = cursor.getString(cursor.getColumnIndex("picture"));
                final String pass = cursor.getString(cursor.getColumnIndex("pass"));
                final String letter = cursor.getString(cursor.getColumnIndex("letter"));
                final String num = cursor.getString(cursor.getColumnIndex("num"));
                // Toast.makeText(this,day,Toast.LENGTH_LONG).show();
                LinearLayout layout_list = new LinearLayout(this);
                layout_list.setOrientation(LinearLayout.VERTICAL);
                if (!d.equals(day)) {
                    TextView tv_list = new TextView(this);
                    tv_list.setText(day);
                    tv_list.setTextSize(20);
                    layout_list.addView(tv_list);
                    d = tv_list.getText().toString();
                }
                if (title != null) {
                    TextView tv_list2 = new TextView(this);
                    tv_list2.setText(title + " - " + id);
                    tv_list2.setTextSize(15);
                    tv_list2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(Main.this, DiaryView.class);
                            it.putExtra("it_picture", picture);
                            it.putExtra("it_title", title);
                            it.putExtra("it_id", id);
                            it.putExtra("it_pass", pass);
                            it.putExtra("it_letter", letter);
                            it.putExtra("it_day", day);
                            it.putExtra("it_num", num);
                            startActivity(it);
                            finish();
                        }
                    });
                    layout_list.addView(tv_list2);
                    layout.addView(layout_list);

                }
            }
            cursor.close();
            sqlitedb.close();
            dbmanager.close();

        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void plus(View v) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    String url = data.getData().toString();
                    Intent it = new Intent(this, DiaryWork.class);
                    it.putExtra("it_uri", url);
                    it.putExtra("it_id", str_id);
                    startActivity(it);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
