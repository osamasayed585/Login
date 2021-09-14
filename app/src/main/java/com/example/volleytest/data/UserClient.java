package com.example.volleytest.data;

import com.example.volleytest.pojo.Data;
import com.example.volleytest.pojo.UserModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {

    private static final String BASE_URL = "https://student.valuxapps.com/api/";
    private Api api;
    private static UserClient Instance;

    public UserClient(){
        // builder & converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // interFace
        api = retrofit.create(Api.class);
    }

    // singleton pattern
    public static UserClient getInstance() {
        if (Instance == null) Instance = new UserClient();
        return Instance;
    }

    public Call<UserModel> postRegister(Data data){
        return api.postRegister(data);}

    public Call<UserModel> postLogin(Data data){ return api.postLogin(data);}

    public Call<UserModel> postLogout(String token){ return api.postLogout(token);}

    public Call<UserModel> getProfile(String token){ return api.getProfile(token);}

}
