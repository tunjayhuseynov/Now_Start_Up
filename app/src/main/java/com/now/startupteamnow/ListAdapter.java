package com.now.startupteamnow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ExampleViewHolder> {
    private ArrayList<AnItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.itemimage);
            mTextView1 = itemView.findViewById(R.id.itemtext);
            mTextView2 = itemView.findViewById(R.id.itemtext2);
        }
    }

    public ListAdapter(ArrayList<AnItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist , parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        AnItem currentItem = mExampleList.get(position);

        //holder.mImageView.setImageResource(currentItem.getImageResource());

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