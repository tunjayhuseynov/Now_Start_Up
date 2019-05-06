package com.now.startupteamnow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tab1 extends Fragment {

    private ImageView profilePhoto;
    private TextView name;
    private TextView bonus;
    private ProgressBar bar;

    public Tab1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.tab1, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {

            name = getView().findViewById(R.id.name);
            profilePhoto= (ImageView) getView().findViewById(R.id.profile_image);
            bonus = getView().findViewById(R.id.bonusview);
            bar = getView().findViewById(R.id.bar);

            name.setText("");
            profilePhoto.setVisibility(View.INVISIBLE);
            bonus.setText("");

            Intent intent = Objects.requireNonNull(getActivity()).getIntent();
            String token = intent.getStringExtra("token");
            int id = intent.getIntExtra("id", 0);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonApi jsonApi = retrofit.create(JsonApi.class);
            Map<String, String> parameters = new HashMap<>();


            final Call<User> user = jsonApi.getUserWithPost(id,token);

            user.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                   if(!response.isSuccessful()){
                       name.setText("Error");
                       return;
                   }

                   bar.setVisibility(View.INVISIBLE);
                   profilePhoto.setVisibility(View.VISIBLE);
                   User user1 = response.body();
                    assert user1 != null;
                    String FullName = user1.getName() + " " + user1.getSurname();
                   Picasso.get().load(BuildConfig.BASE_URL + "images/"+user1.getImgPath()).into(profilePhoto);
                   String textBonus = String.format(Locale.getDefault(),"Bonusunuz: %d",user1.getBonus());
                   bonus.setText(textBonus);
                   name.setText(FullName);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    name.setText(t.getMessage());
                }
            });




        }catch (Exception e){
            Log.d("Exceptionvar", e.toString());
        }
    }

}