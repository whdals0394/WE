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
            String sql = "select * from Diary where id ='" + str_id + "'";
            Cursor cursor = sqlitedb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String day = cursor.getString(cursor.getColumnIndex("day"));
                // Toast.makeText(this,day,Toast.LENGTH_LONG).show();
                LinearLayout layout_list = new LinearLayout(this);
                layout_list.setOrientation(LinearLayout.VERTICAL);

                TextView tv_list = new TextView(this);
                tv_list.setText(day);
                tv_list.setTextSize(20);
                layout_list.addView(tv_list);
                layout.addView(layout_list);
                String sql2 = "select *from Diary where day ='" + day + "'";
                Cursor cursor2 = sqlitedb.rawQuery(sql2, null);
                while (cursor2.moveToNext()) {
                    final String title = cursor2.getString(cursor2.getColumnIndex("title"));
                    final String id = cursor2.getString(cursor2.getColumnIndex("id"));
                    final String picture = cursor2.getString(cursor2.getColumnIndex("picture"));
                    LinearLayout layout_list2 = new LinearLayout(this);
                    layout_list2.setOrientation(LinearLayout.HORIZONTAL);

                    TextView tv_list2 = new TextView(this);
                    tv_list2.setText(title);
                    tv_list2.setTextSize(15);
                    tv_list2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Intent it = new Intent(this, DiaryView.class);
                            // it.putExtra("it_picture", picture);
                            //it.putExtra("it_title", title);
                            //it.putExtra("it_id", id);
                            //startActivity(it);
                            //finish();
                        }
                    });
                    TextView tv_list3 = new TextView(this);
                    tv_list3.setGravity(Gravity.RIGHT);
                    layout_list2.addView(tv_list2);
                    layout_list2.addView(tv_list3);

                    layout.addView(layout_list2);
                }
                cursor2.close();

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
