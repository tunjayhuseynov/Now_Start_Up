package com.now.startupteamnow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static maes.tech.intentanim.CustomIntent.customType;

public class History extends AppCompatActivity {

    private ArrayList<AnHistoryItem> exampleList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        mRecyclerView = findViewById(R.id.HistoryRecycler);
        mRecyclerView.setHasFixedSize(true);

        GetHistory();

    }

    private void GetHistory(){
        JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);

        final Call<ArrayList<UserHistoryJson>> user = jsonApi.getHistory(Request_And_API_Key.Api_Key,Request_And_API_Key.GetId(History.this));

        user.enqueue(new Callback<ArrayList<UserHistoryJson>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<UserHistoryJson>> call, @NonNull Response<ArrayList<UserHistoryJson>> response) {
                if(!response.isSuccessful()) {
                    exampleList.add(new AnHistoryItem("", "Yenidən Yoxlayın",""));
                }

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
                exampleList.add(new AnHistoryItem("", "Yenidən Yoxlayın",""));

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
