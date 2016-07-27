package com.wegood.wegood;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        /*ImageView lv_picture = (ImageView)findViewById(R.id.image);
        Uri photoURI = Uri.parse(it.getStringExtra("it_picture"));*/
        tv_title.append(str_title);
        tv_letter.append(str_letter);
        tv_day.append(str_day);
        /*try {
            Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
            lv_picture.setImageBitmap(image_bitmap);
        } catch (Exception e) {
            e.getStackTrace();
        }*/
    }

    public void home(View v) {
        Intent it = getIntent();
        String str_id = it.getStringExtra("it_id");
        Intent it2 = new Intent(this, Main.class);
        it2.putExtra("it_id", str_id);
        startActivity(it2);
        finish();
    }

    public void revise(View v) {

    }
}
