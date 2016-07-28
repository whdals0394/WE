package com.wegood.wegood;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                // Toast.makeText(this,day,Toast.LENGTH_LONG).show();
                LinearLayout layout_daylist = new LinearLayout(this);
                layout_daylist.setOrientation(LinearLayout.VERTICAL);

                //Toast.makeText(this, picture , Toast.LENGTH_SHORT).show();

                if (!d.equals(day)) {
                    TextView tv_daylist = new TextView(this);
                    tv_daylist.setText(day);
                    tv_daylist.setTextSize(20);
                    layout_daylist.addView(tv_daylist);
                    d = tv_daylist.getText().toString();
                }
                if (title != null) {
                    LinearLayout layout_list = new LinearLayout(this);
                    layout_list.setOrientation(LinearLayout.HORIZONTAL);
                    layout_list.setPadding(20, 20, 20, 20);
                    LinearLayout layout_inlist = new LinearLayout(this);
                    layout_inlist.setOrientation(LinearLayout.VERTICAL);
                    layout_inlist.setPadding(20, 0, 0, 0);

                    TextView tv_title = new TextView(this);
                    TextView tv_id = new TextView(this);
                    ImageView ptimage = new ImageView(this);
                    //ptimage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Uri photoURI = Uri.parse(picture);

                    tv_title.setText("TITLE:" + title);
                    tv_title.setTextSize(15);
                    tv_id.setText("ID:" + id);
                    tv_id.setTextSize(15);

                    try {
                        Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);

                        int height = image_bitmap.getHeight();
                        int width = image_bitmap.getWidth();

                        Bitmap resized = null;

                        // while (height > 118) {
                        resized = Bitmap.createScaledBitmap(image_bitmap, 300, 300, true);
                        // height = resized.getHeight();
                        // width = resized.getWidth();
                        // }

                        ptimage.setImageBitmap(resized);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                    layout_list.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(Main.this, DiaryView.class);
                            it.putExtra("it_picture", picture);
                            it.putExtra("it_title", title);
                            it.putExtra("it_id", id);
                            it.putExtra("it_pass", pass);
                            it.putExtra("it_letter", letter);
                            it.putExtra("it_day", day);
                            startActivity(it);
                            finish();
                        }
                    });

                    Drawable drawable = getResources().getDrawable(R.drawable.border);
                    layout_list.setBackground(drawable);
                    layout_list.addView(ptimage);
                    layout_list.addView(layout_inlist);
                    layout_inlist.addView(tv_title);
                    layout_inlist.addView(tv_id);
                    layout_daylist.addView(layout_list);
                    layout.addView(layout_daylist);

                }
            }
            cursor.close();


        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqlitedb.close();
            dbmanager.close();
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

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "'뒤로'버튼을한번더누르시면종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }


}
