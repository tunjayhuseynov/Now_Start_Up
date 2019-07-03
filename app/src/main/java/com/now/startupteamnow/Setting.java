package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static maes.tech.intentanim.CustomIntent.customType;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sp = getSharedPreferences("Login", MODE_PRIVATE).edit();
                sp.putInt("id", 0);
                sp.putString("token", null);
                sp.apply();

                startActivity(new Intent(Setting.this, LogIn.class));
                customType(Setting.this, "left-to-right");
            }
        });
    }
}
