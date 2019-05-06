package com.now.startupteamnow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {
    @GET("api/users/{Id}")
    Call<User> getUserWithPost(@Path("Id") int id, @Body String token);

    @GET("api/barcodelist")
    Call<List<QRcode>> getBarcodeList();

    @POST("api/users/check")
    Call<CheckResponse> postCheckUser(@Body UserInput userInput);

}
