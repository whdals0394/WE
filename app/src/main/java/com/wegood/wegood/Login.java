package com.wegood.wegood;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login(View v){
        EditText et_id = (EditText)findViewById(R.id.id);
        EditText et_pass = (EditText)findViewById(R.id.pass);
        String str_id = et_id.getText().toString();
        String str_pass = et_id.getText().toString();
        if(str_id.equals("")||str_id==null){
            Toast.makeText(this,"아이디를 입력해주세요", Toast.LENGTH_LONG).show();
            return;
        }else if(str_id.equals("")||str_id==null){
            Toast.makeText(this,"비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            loginwork(str_id,str_pass);
        }catch (Exception e){
            e.getStackTrace();
        }
    }
    public void loginwork(String id,String pass){
        dbmanager = new DBManager(this);
        sqlitedb = dbmanager.getReadableDatabase();
        String sql = "select * from Diary where id"+"'"+id+"',pass='"+pass+"'";
        Cursor cursor = sqlitedb.rawQuery(sql, null);
        if(cursor.moveToNext()){
            Intent it = new Intent(this,Main.class);
            it.putExtra("it_id",id);
            startActivity(it);
            finish();
        }else
            Toast.makeText(this,"아이디,비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
    }
    public void join(View v){
        dbmanager = new DBManager(this);
        sqlitedb = dbmanager.getReadableDatabase();

        EditText et_id = (EditText)findViewById(R.id.id);
        EditText et_pass = (EditText)findViewById(R.id.pass);
        String str_id = et_id.getText().toString();
        String str_pass = et_id.getText().toString();

        String sql = "select * from Diary where id"+"'"+et_id+"'";
        Cursor cursor = sqlitedb.rawQuery(sql, null);
        if(cursor.moveToNext()){
            Toast.makeText(this,"중복된 아이디입니다.", Toast.LENGTH_LONG).show();
        }else{
            Intent it = new Intent(this,JoinView.class);
            it.putExtra("it_id",str_id);
            it.putExtra("it_pass",str_pass);
            startActivity(it);
            finish();
        }
    }
}

