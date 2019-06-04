package com.now.startupteamnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class List<U> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<AnItem> exampleList = new ArrayList<>();
        exampleList.add(new AnItem(R.drawable.scanicon, "Line 1", "Line 2"));
        exampleList.add(new AnItem(R.drawable.scanicon, "Line 1", "Line 2"));

        RecyclerView mRecyclerView = findViewById(R.id.CompanyRecycler);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new ListAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(List.this, HomePage.class);
        this.startActivity(intent);
        customType(List.this,"right-to-left");

        super.onBackPressed();
    }
}
