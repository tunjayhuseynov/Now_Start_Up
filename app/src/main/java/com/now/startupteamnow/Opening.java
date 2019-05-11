package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import static maes.tech.intentanim.CustomIntent.customType;

public class Opening extends AppCompatActivity {

    private String token;
    private int Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);


        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        if(sp.getString("token",null) == null && sp.getInt("id",0)== 0){

            //Go Login
            Intent GoRegister = new Intent(Opening.this, LogIn.class);
            startActivity(GoRegister);

        }else{
            //Go Home with Auto-Log
            token = sp.getString("token",null);
            Userid = sp.getInt("id",0);
            Intent GoHome = new Intent(Opening.this, HomePage.class);
            GoHome.putExtra("token", token);
            GoHome.putExtra("id", Userid);
            startActivity(GoHome);
            customType(this,"right-to-left");
        }
    }
}
