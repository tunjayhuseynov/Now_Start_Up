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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        final Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        String pass = intent.getStringExtra("pass");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);


        //UserInput userInput = new UserInput(number, pass);

        String username = "Nowteam";
        String password = "5591980Now";
        String base = username + ":" + password;
        //String authhead = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        String authhead = "Basic Tm93dGVhbTo1NTkxOTgwTm93";
        final Call<CheckResponse> call = jsonApi.CheckUser(authhead,number, pass);

        call.enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(@NonNull Call<CheckResponse> call, @NonNull Response<CheckResponse> response) {
                if(!response.isSuccessful()){
                    Log.d("Qanli", response.raw().toString() + " " + response.headers().toString());
                    Toast.makeText(Check.this, response.raw().toString()+"" + response.code(), Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(Check.this, LogIn.class);
                    startActivity(intent1);
                }

                if(response.body() != null){
                    CheckResponse checkResponse = response.body();

                    if(!checkResponse.isFound()){
                        Toast.makeText(Check.this, "Daxil Etdiyiniz Melumatlar Databazamizda Mevcut Deyil", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<CheckResponse> call, Throwable t) {
                Log.d("Qanli Error", t.toString());
                Toast.makeText(Check.this, t.toString(), Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Check.this, LogIn.class);
                startActivity(intent1);
            }
        });
    }
}
