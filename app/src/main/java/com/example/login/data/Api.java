package com.example.login.data;

import com.example.login.pojo.Data;
import com.example.login.pojo.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {
    @GET("profile")
    Call<UserModel> getProfile(@Header("Authorization") String authorization);

    @POST("register")
    Call<UserModel> postRegister(@Body Data data);

    @POST("login")
    Call<UserModel> postLogin(@Body Data data);

    @POST("logout")
    Call<UserModel> postLogout(@Header("Authorization") String authorization);
}
