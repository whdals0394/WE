package com.wegood.wegood;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    SQLiteDatabase sqlitedb;
    DBmanager dbmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.view);
        Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        try {
            dbmanager = new DBmanager(this);
            sqlitedb = dbmanager.getReadableDatabase();
            String sql = "select * from Diary where id ='" + str_id + "'";
            Cursor cursor = sqlitedb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String day = cursor.getString(cursor.getColumnIndex("day"));
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
                    final String pass = cursor2.getString(cursor2.getColumnIndex("pass"));
                    final String letter = cursor2.getString(cursor2.getColumnIndex("letter"));
                    LinearLayout layout_list2 = new LinearLayout(this);
                    layout_list2.setOrientation(LinearLayout.HORIZONTAL);

                    TextView tv_list2 = new TextView(this);
                    tv_list2.setText(title);
                    tv_list2.setTextSize(15);
                    tv_list2.setTag(String.valueOf(cursor2.getInt(0)));
                    tv_list2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String num = String.valueOf(v.getTag());
                            Intent it2 = new Intent(Main.this ,DiaryView.class);
                            it2.putExtra("it_num", num);
                            it2.putExtra("it_picture", picture);
                            it2.putExtra("it_title", title);
                            it2.putExtra("it_letter", letter);
                            startActivity(it2);
                            finish();
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
        /*Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        String str_pass = it.getStringExtra("it_pass");
        Intent it2 = new Intent(this, Galeray.class);
        it2.putExtra("it_id", str_id);
        it2.putExtra("it_pass", str_pass);
        startActivity(it2);
        finish();*/
    }
}
