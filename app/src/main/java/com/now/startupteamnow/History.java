package com.now.startupteamnow;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static maes.tech.intentanim.CustomIntent.customType;

public class History extends AppCompatActivity {

    private ArrayList<AnHistoryItem> exampleList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);



        mRecyclerView = findViewById(R.id.HistoryRecycler);
        mRecyclerView.setHasFixedSize(true);

        GetHistory();

    }




    private void GetHistory(){
        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //String token = sp.getString("token", null);
        int userid = sp.getInt("id", 0);


        String authhead = "Basic Tm93dGVhbTo1NTkxOTgwTm93";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);



        final Call<ArrayList<UserHistoryJson>> user = jsonApi.getHistory(authhead,userid);

        user.enqueue(new Callback<ArrayList<UserHistoryJson>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserHistoryJson>> call, @NonNull Response<ArrayList<UserHistoryJson>> response) {
                if(!response.isSuccessful()) {exampleList.add(new AnHistoryItem("Yenidən Yoxlayın", "",""));}

                if(response.body() != null){
                    ArrayList<UserHistoryJson> historyJsons = response.body();

                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    for (UserHistoryJson item: historyJsons) {

                        exampleList.add(new AnHistoryItem(item.getCompanyName(), dateFormat.format(item.getCapturedAt()), "+"+ item.getBonus()));


                    }


                }

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.Adapter mAdapter = new HistoryListAdapter(exampleList);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<UserHistoryJson>> call, @NonNull Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(History.this, HomePage.class);
        startActivity(intent);
        customType(History.this,"left-to-right");

        super.onBackPressed();
    }

    public void Back(View view){
        Intent intent = new Intent(History.this, HomePage.class);
        startActivity(intent);
        customType(History.this,"left-to-right");
    }
}
