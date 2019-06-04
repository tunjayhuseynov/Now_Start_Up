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
import android.net.Uri;
import android.os.Bundle;

import java.security.Permission;
import java.sql.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                return;

                }
        }

                CheckUpdate();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == 0) {
                if(grantResults.length > 0){
                    for (int result:grantResults) {
                        if(result == PackageManager.PERMISSION_DENIED){
                            buildAlertMessage();
                            return;
                        }
                    }
                    Intent same = new Intent(Opening.this, Opening.class);
                    startActivity(same);

                }
        }
    }


    protected void buildAlertMessage() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bütün İcazələr Təsdiq Edilməlidir.\nZəhmət Olmasa, İcazələri Təsdiq Edəsiniz")
                .setCancelable(false)
                .setNegativeButton("Yenidən", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        //finish();
                        //ActivityCompat.requestPermissions(Opening.this, Permissions,0);
                        Intent same = new Intent(Opening.this, Opening.class);
                        startActivity(same);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //finish();
    }


    private void CheckUser(){

        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        if(sp.getString("token",null) == null && sp.getInt("id",0)== 0){

            //Go Login
            Intent GoRegister = new Intent(Opening.this, LogIn.class);
            startActivity(GoRegister);
            customType(this,"right-to-left");

        }else{
            //Go Home with Auto-Log
            Intent GoHome = new Intent(Opening.this, HomePage.class);
            startActivity(GoHome);
            customType(this,"right-to-left");
        }
    }

    private void CheckUpdate(){

        String authhead = "Basic Tm93dGVhbTo1NTkxOTgwTm93";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);



        final Call<Boolean> call = jsonApi.CheckUpdate(authhead);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){}

                if(response.body() != null){
                    Boolean bool = response.body();
                    if(bool){
                        UpdateAlert();
                    }else{
                        CheckUser();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    protected void UpdateAlert() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tətbiqatı Yeni Versiyaya Yüksəldin:")
                .setCancelable(false)
                .setPositiveButton("Yüksəlt", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
