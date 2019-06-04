package com.now.startupteamnow;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonApi {
    @GET("api/users/{Id}")
    Call<User> getUserWithPost(@Header("Authorization") String auth, @Path("Id") int id, @Query("token") String token);

    @GET("api/barcodelist/CheckBarcode")
    Call<QRcode> getBarcodeList(@Header("Authorization") String auth, @Body String code);

    @GET("api/company")
    Call<ArrayList<CompanyInformation>> getCompanyList(@Header("Authorization") String auth);

    @GET("api/barcodelist/GetHistory")
    Call<ArrayList<UserHistoryJson>> getHistory(@Header("Authorization") String auth, @Query("code") int code);

    @GET("api/users/CheckUser")
    Call<CheckResponse> CheckUser(@Header("Authorization") String auth, @Query("Number") String Number, @Query("Password") String Password);

    @GET("api/update")
    Call<Boolean> CheckUpdate(@Header("Authorization") String auth);

    @Multipart
    @POST("api/users")
    Call<CheckResponse> postNewUser(@Header("Authorization") String auth, @Part MultipartBody.Part file, @QueryMap Map<String, String> value);

    @POST("api/process")
    Call<Integer> postBonus(@Header("Authorization") String auth, @Body int id, String token, int value);
}
