package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static maes.tech.intentanim.CustomIntent.customType;

public class List<U> extends AppCompatActivity {

    ArrayList<AnItem> exampleList = new ArrayList<>();
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRecyclerView = findViewById(R.id.CompanyRecycler);
        mRecyclerView.setHasFixedSize(true);

        GetList();

    }

    private void GetList(){
        SharedPreferences sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //String token = sp.getString("token", null);
        int userid = sp.getInt("id", 0);


        String authhead = "Basic Tm93dGVhbTo1NTkxOTgwTm93";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi jsonApi = retrofit.create(JsonApi.class);



        final Call<ArrayList<CompanyInformation>> user = jsonApi.getCompanyList(authhead);

        user.enqueue(new Callback<ArrayList<CompanyInformation>>() {
            @Override
            public void onResponse(Call<ArrayList<CompanyInformation>> call, Response<ArrayList<CompanyInformation>> response) {
                if(!response.isSuccessful()) {exampleList.add(new AnItem("Yenidən Yoxlayın", "",""));}

                if(response.body() != null){
                    ArrayList<CompanyInformation> historyJsons = response.body();

                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    for (CompanyInformation item: historyJsons) {

                        exampleList.add(new AnItem(item.getImageSrc(),item.getName(), item.getDescription()));


                    }


                }

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.Adapter mAdapter = new ListAdapter(exampleList);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<CompanyInformation>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(List.this, HomePage.class);
        this.startActivity(intent);
        customType(List.this,"right-to-left");

        super.onBackPressed();
    }
}
