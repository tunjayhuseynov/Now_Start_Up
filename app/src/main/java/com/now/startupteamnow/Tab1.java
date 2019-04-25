package com.now.startupteamnow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Tab1 extends Fragment {

    private ImageView profilePhoto;
    private TextView name;


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

            String url = BuildConfig.BASE_URL + "users/1";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonApi jsonApi = retrofit.create(JsonApi.class);
            Map<String, String> parameters = new HashMap<>();


            final Call<User> user = jsonApi.getUser(1);

            user.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                   if(!response.isSuccessful()){
                       name.setText("Error");
                       return;
                   }
                   User user1 = response.body();
                   name.setText(user1.getName() + " " + user1.getSurname());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    name.setText(t.getMessage());
                }
            });


            profilePhoto= (ImageView) getView().findViewById(R.id.profile_image);
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(profilePhoto);



        }catch (Exception e){
            Log.d("Exceptionvar", e.toString());
        }
    }

}