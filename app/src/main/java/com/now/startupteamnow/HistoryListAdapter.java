package com.now.startupteamnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ExampleViewHolder> {
    private ArrayList<AnHistoryItem> mExampleList;

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView mBonus;
        TextView mTextView1;
        TextView mTextView2;

        ExampleViewHolder(View itemView) {
            super(itemView);
            mBonus = itemView.findViewById(R.id.historyCompany);
            mTextView1 = itemView.findViewById(R.id.historyText);
            mTextView2 = itemView.findViewById(R.id.historyText2);
        }
    }

    HistoryListAdapter(ArrayList<AnHistoryItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    @NonNull
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item , parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        AnHistoryItem currentItem = mExampleList.get(position);

        holder.mBonus.setText(currentItem.getmBonus());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}