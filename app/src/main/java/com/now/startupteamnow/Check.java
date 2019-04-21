package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Check extends AppCompatActivity {

    private String CodeText;
    private String Lat;
    private String Lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        assert extras != null;
        CodeText = extras.getString("TextOfCode");
        Lat = extras.getString("Lat");
        Lon = extras.getString("Lon");

        Toast.makeText(this, CodeText + " " + Lat + " "+ Lon, Toast.LENGTH_LONG).show();
    }
}
