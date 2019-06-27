package com.now.startupteamnow;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

class Request_And_API_Key {

    static String Api_Key = "Basic Tm93dGVhbTo1NTkxOTgwTm93";

    static int GetId(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getInt("id", 0);
    }

    static String GetToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        return sp.getString("token", null);
    }

    static Retrofit GetRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
