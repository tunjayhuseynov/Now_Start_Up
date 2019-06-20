package com.now.startupteamnow;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class Request_And_API_Key {

    public static String Api_Key = "Basic Tm93dGVhbTo1NTkxOTgwTm93";

    public static Retrofit retrofit;

    public static int GetId(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getInt("id", 0);
    }

    public static String GetToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("token", null);
    }

    public static Retrofit GetRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


}
