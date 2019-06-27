package com.now.startupteamnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ExampleViewHolder> {
    private ArrayList<AnItem> mExampleList;

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;

        ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.itemimage);
            mTextView1 = itemView.findViewById(R.id.itemtext);
            mTextView2 = itemView.findViewById(R.id.itemtext2);
        }
    }

    ListAdapter(ArrayList<AnItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    @NonNull
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist , parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        AnItem currentItem = mExampleList.get(position);

        Glide.with(holder.mImageView.getContext())
                .load(BuildConfig.BASE_URL + "images/companies/"+ currentItem.getImageResource())
                .into(holder.mImageView);
        
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}