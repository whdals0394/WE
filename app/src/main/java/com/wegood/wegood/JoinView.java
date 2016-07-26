package com.wegood.wegood;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class JoinView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_view);
    }
    public void go(View v){
        Intent it = getIntent();
        String str_id= it.getStringExtra("it_id");
        String str_pass= it.getStringExtra("it_pass");

        Intent it_main = new Intent(this, Main.class);
        it_main.putExtra("it_id",str_id);
        it_main.putExtra("it_pass",str_pass);
        startActivity(it_main);
        finish();
    }
}