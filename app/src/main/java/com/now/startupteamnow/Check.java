package com.now.startupteamnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        final Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        String pass = intent.getStringExtra("pass");


        JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);

        final Call<CheckResponse> call = jsonApi.CheckUser(Request_And_API_Key.Api_Key,number, pass);

        call.enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(@NonNull Call<CheckResponse> call, @NonNull Response<CheckResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Check.this, "Xəta", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(Check.this, LogIn.class);
                    startActivity(intent1);
                }

                if(response.body() != null){
                    CheckResponse checkResponse = response.body();

                    if(!checkResponse.isFound()){
                        Toast.makeText(Check.this, "Daxil Etdiyiniz Mılumatlar Databazamızda Mövcud Deyil", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(Check.this, LogIn.class);
                        startActivity(intent1);
                    }
                    if(!checkResponse.isPassCorrect()){
                        Toast.makeText(Check.this, "Şifrə Yanlışdır", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(Check.this, LogIn.class);
                        startActivity(intent1);
                    }

                    if(checkResponse.getId() != 0 && checkResponse.getToken() != null && checkResponse.isFound() && checkResponse.isPassCorrect()){
                        SharedPreferences.Editor sp = getSharedPreferences("Login", MODE_PRIVATE).edit();
                        sp.putInt("id", checkResponse.getId());
                        sp.putString("token", checkResponse.getToken());
                        sp.apply();

                        Intent goToHome = new Intent(Check.this, HomePage.class);
                        goToHome.putExtra("token", checkResponse.getToken());
                        goToHome.putExtra("id", checkResponse.getId());
                        startActivity(goToHome);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<CheckResponse> call, @NonNull Throwable t) {
                Toast.makeText(Check.this, "Xəta", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Check.this, LogIn.class);
                startActivity(intent1);
            }
        });
    }
}
