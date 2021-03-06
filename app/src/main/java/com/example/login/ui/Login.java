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
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TextInputEditText email,password;
    private Button btnLogin;
    private TextView signUp, cancel, google, facebook;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // todo create shared preference for save a status if was true or false

        // inflate all variable
        inflateForElements();
        // while click sign up
        clickSignUp();
        // while click button cancel
        ClickCancel();
        // while click button login
        loginWithApi();
    }

    private void clickSignUp() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }

    private void ClickCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Profile.class));
            }
        });
    }

    private void loginWithApi() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                // get text email and password from user
                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();
                Data data = new Data(mEmail,mPassword);

                UserClient.getInstance().postLogin(data).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (response.body().getStatus()){
                            Data token = response.body().getData();
                            Constants.setToken(token.getToken());
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(Login.this,Profile.class));
                        }
                    }
                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(Login.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void inflateForElements() {
        email = (TextInputEditText)findViewById(R.id.login_ed_email);
        password = (TextInputEditText)findViewById(R.id.login_ed_password);
        btnLogin = (Button)findViewById(R.id.login_btn_login);
        signUp = (TextView)findViewById(R.id.login_txt_signUp);
        google = (TextView)findViewById(R.id.loginWithGoogle);
        facebook = (TextView)findViewById(R.id.loginWithFacebook);
        cancel = findViewById(R.id.login_cancel);
        progressBar = findViewById(R.id.login_progressBar);
    }
}