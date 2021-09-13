package com.example.volleytest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volleytest.R;
import com.example.volleytest.data.UserClient;
import com.example.volleytest.pojo.Data;
import com.example.volleytest.pojo.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText name, email, phone, password;
    private Button register;
    private TextView cancel;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // inflate for all variable
        InflateForElements();
        clickButtonRegister();
        // click button cancel
        clickButtonCancel();

    }

    private void clickButtonCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void clickButtonRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerWithApi();
            }
        });
    }

    private void registerWithApi() {
        // get All data from user
        progressBar.setVisibility(View.VISIBLE);
        String mName = name.getText().toString();
        String mEmail = email.getText().toString();
        String mPhone = phone.getText().toString();
        String mPassword = password.getText().toString();

        Data user = new Data(mName, mEmail, mPhone, mPassword);

        UserClient.getInstance().postRegister(user).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (response.body().getStatus()){
                    Data data = response.body().getData();
                    Constants.setToken(data.getToken());
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(Register.this,Profile.class));
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(Register.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InflateForElements() {
        name = findViewById(R.id.register_name);
        email = findViewById(R.id.register_email);
        phone = findViewById(R.id.register_phone);
        password = findViewById(R.id.register_password);
        register =findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.resgister_progressBar);
        cancel= findViewById(R.id.register_cancel);
    }


}