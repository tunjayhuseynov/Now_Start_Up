package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Check extends AppCompatActivity {

    private String username = "Nowteam";
    private String password = "5591980Now";

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


        UserInput userInput = new UserInput(number, pass);

        String base = username + ":" + password;
        String authhead = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        final Call<CheckResponse> call = jsonApi.postCheckUser(authhead,userInput);

        call.enqueue(new Callback<CheckResponse>() {
            @Override
            public void onResponse(Call<CheckResponse> call, Response<CheckResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Check.this, "Serverdə Xəta Var", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(Check.this, LogIn.class);
                    startActivity(intent1);
                }

                CheckResponse checkResponse = response.body();

                assert checkResponse != null;
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
                    Intent goToHome = new Intent(Check.this, HomePage.class);
                    goToHome.putExtra("token", checkResponse.getToken());
                    goToHome.putExtra("id", checkResponse.getId());
                    startActivity(goToHome);
                }

            }

            @Override
            public void onFailure(Call<CheckResponse> call, Throwable t) {

            }
        });
    }
}
