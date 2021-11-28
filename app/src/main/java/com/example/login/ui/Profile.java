package com.example.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.login.R;
import com.example.login.data.UserClient;
import com.example.login.pojo.Data;
import com.example.login.pojo.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private TextView id, name, email, phone;
    private ProgressBar progressBar;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // inflate for all variables
        inflateForElements();
        // show progressBar
        progressBar.setVisibility(View.VISIBLE);
        // get all data using api profile
        getAllDataFromApiProfile();
        // logout using api logout
        logoutUsingApi();
    }

    private void inflateForElements() {
        id = findViewById(R.id.profile_id);
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        phone = findViewById(R.id.profile_phone);
        btnLogout = findViewById(R.id.profile_logout);
        progressBar = findViewById(R.id.profile_progressBar);
    }
    private void getAllDataFromApiProfile() {
        UserClient.getInstance().getProfile(Constants.getToken()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (response.body().getStatus()){
                    Data data = response.body().getData();
                    progressBar.setVisibility(View.GONE);
                    id.setText(String.valueOf(data.getId()));
                    name.setText(data.getName());
                    email.setText(data.getEmail());
                    phone.setText(data.getPhone());
                    Constants.setLogoutToken(data.getToken());
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(Profile.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void logoutUsingApi() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserClient.getInstance().postLogout(Constants.getLogoutToken()).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if(response.body().getStatus()){
                            startActivity(new Intent(Profile.this,Login.class));
                        }
                    }
                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(Profile.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}