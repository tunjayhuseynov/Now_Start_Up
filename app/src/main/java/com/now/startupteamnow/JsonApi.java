package com.now.startupteamnow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonApi {
    @GET("users/{Id}")
    Call<User> getUser(@Path("Id") int id);
}
