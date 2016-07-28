package com.wegood.wegood;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
        String str_day = it.getStringExtra("it_day");


        TextView tv_title = (TextView) findViewById(R.id.title);
        TextView tv_letter = (TextView) findViewById(R.id.letter);
        TextView tv_day = (TextView) findViewById(R.id.day);
        ImageView lv_picture = (ImageView)findViewById(R.id.image);
        Uri photoURI = Uri.parse(str_picture);
        tv_title.append(str_title);
        tv_letter.append(str_letter);
        tv_day.append(str_day);
        try {
            Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
            lv_picture.setImageBitmap(image_bitmap);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    public void revise(View v) {
        Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        String str_pass = it.getStringExtra("it_pass");
        String str_num = it.getStringExtra("it_num");
        String str_picture = it.getStringExtra("it_picture");
        String str_day = it.getStringExtra("it_day");
        String str_title = it.getStringExtra("it_title");
        String str_letter = it.getStringExtra("it_letter");
        Intent it2 = new Intent(this, DiaryUpdate.class);
        it2.putExtra("it_picture", str_picture);
        it2.putExtra("it_title", str_title);
        it2.putExtra("it_id", str_id);
        it2.putExtra("it_pass", str_pass);
        it2.putExtra("it_letter", str_letter);
        it2.putExtra("it_day", str_day);
        it2.putExtra("it_num", str_num);
        startActivity(it2);
        finish();


    }
    public void delete(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("알림창");
        alert.setMessage("삭제하시겠습니까?");
        alert.setNegativeButton("취소", null);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbmanager = new DBmanager(DiaryView.this);
                sqlitedb = dbmanager.getReadableDatabase();
                Intent it = getIntent();
                String str_num = it.getStringExtra("it_num");
                String str_id = it.getStringExtra("it_id");
                String query = "delete from Diary where num="+str_num;
                sqlitedb.execSQL(query);
                sqlitedb.close();
                Intent it2 = new Intent(DiaryView.this, Main.class);
                dbmanager.close();
                it2.putExtra("it_id", str_id);
                startActivity(it2);
                finish();
            }
        });
        alert.show();

    }
    @Override
    public void onBackPressed() {
        Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        Intent it2 = new Intent(this, Main.class);
        it2.putExtra("it_id", str_id);
        startActivity(it2);
        finish();
    }
}
