package com.now.startupteamnow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static maes.tech.intentanim.CustomIntent.customType;

public class List extends AppCompatActivity {

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
        JsonApi jsonApi = Request_And_API_Key.GetRetrofit().create(JsonApi.class);

        final Call<ArrayList<CompanyInformation>> user = jsonApi.getCompanyList(Request_And_API_Key.Api_Key);

        user.enqueue(new Callback<ArrayList<CompanyInformation>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CompanyInformation>> call, @NonNull Response<ArrayList<CompanyInformation>> response) {
                if(!response.isSuccessful()) {
                    exampleList.add(new AnItem("", "Yenidən Yoxlayın",""));
                }

                if(response.body() != null){
                    ArrayList<CompanyInformation> historyJsons = response.body();

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
            public void onFailure(@NonNull Call<ArrayList<CompanyInformation>> call, @NonNull Throwable t) {
                Toast.makeText(List.this, "Yenidən Yoxlayın", Toast.LENGTH_LONG).show();
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

    public void Back(View view){
        Intent intent = new Intent(List.this, HomePage.class);
        startActivity(intent);
        customType(List.this,"left-to-right");
    }
}
