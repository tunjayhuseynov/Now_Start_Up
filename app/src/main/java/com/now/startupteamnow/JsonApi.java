package com.now.startupteamnow;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JsonApi {
    @GET("api/users/{Id}")
    Call<User> getUserWithPost(@Path("Id") int id, @Body String token);

    @GET("api/barcodelist/CheckBarcode")
    Call<QRcode> getBarcodeList(@Header("Authorization") String auth, @Body String code);

    @POST("api/users/check")
    Call<CheckResponse> postCheckUser(@Header("Authorization") String auth, @Body UserInput userInput);

    @POST("api/users")
    Call<CheckResponse> postNewUser(@Part MultipartBody.Part file, @Body CreateUser createUser);

}
