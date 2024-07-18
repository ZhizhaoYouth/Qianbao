package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.math.BigDecimal;


import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.LoginLogControllerApi;
import io.swagger.client.api.UserControllerApi;
import io.swagger.client.model.UserLoginRequest;
import io.swagger.client.model.UserRegisterRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText edt_rid = findViewById(R.id.edt_rid);
        EditText edt_rpwd = findViewById(R.id.edt_rpwd);
        EditText edt_apwd = findViewById(R.id.edt_apwd);

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();

        Button btn_registeruser = findViewById(R.id.btn_registeruser);
        Button btn_registreturn = findViewById(R.id.btn_registreturn);

        btn_registreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            userRegisterRequest.setUserAccount(edt_rid.getText().toString());
                            userRegisterRequest.setUserPassword(edt_rpwd.getText().toString());
                            userRegisterRequest.setCheckPassword(edt_apwd.getText().toString());

                            UserControllerApi userControllerApi = new UserControllerApi(apiClient);
                            try {
                                String register_cb = userControllerApi.userRegisterUsingPOSTWithHttpInfo(userRegisterRequest).getData().getMessage();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this,register_cb+"!",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } catch (ApiException e) {
                                throw new RuntimeException(e);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }

        });

    }

}
