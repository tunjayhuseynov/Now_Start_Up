package com.now.startupteamnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.security.Permission;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class Opening extends AppCompatActivity {

    private String[] Permissions = {Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        for (String permission:Permissions) {
            if (ContextCompat.checkSelfPermission(Opening.this, permission)!= PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Opening.this, Permissions,0);

                }
        }

        CheckUser();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == 0) {
                if(grantResults.length > 0){
                    for (int result:grantResults) {
                        if(result == PackageManager.PERMISSION_DENIED){
                            buildAlertMessageNoGps();
                        }
                    }
                }
        }
    }


    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bütün İcazələr Təsdiq Edilmədən Tətbiqat İşləmir.\nZəhmət Olmasa, İcazələri Təsdiq Edəsiniz")
                .setCancelable(false)
                .setNegativeButton("Çıxış", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        finish();
    }


    private void CheckUser(){

        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        if(sp.getString("token",null) == null && sp.getInt("id",0)== 0){

            //Go Login
            Intent GoRegister = new Intent(Opening.this, LogIn.class);
            startActivity(GoRegister);

        }else{
            //Go Home with Auto-Log
            String token = sp.getString("token", null);
            int userid = sp.getInt("id", 0);
            Intent GoHome = new Intent(Opening.this, HomePage.class);
            GoHome.putExtra("token", token);
            GoHome.putExtra("id", userid);
            startActivity(GoHome);
            customType(this,"right-to-left");
        }
    }
}
