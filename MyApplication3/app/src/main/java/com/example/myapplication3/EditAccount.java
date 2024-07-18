package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.AccountControllerApi;
import io.swagger.client.model.AccountEditRequest;

public class EditAccount extends AppCompatActivity {
    private String sessionId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);

        sessionId = getIntent().getStringExtra("SESSION_ID");

        Button btn_edit_editaccount = findViewById(R.id.btn_edit_editaccount);
        //Button btn_back_editaccount = findViewById(R.id.btn_back_editaccount);

        EditText et_editaccount_id = findViewById(R.id.et_editaccount_id);
        EditText et_editaccount_name = findViewById(R.id.et_editaccount_name);
        EditText et_editaccount_number = findViewById(R.id.et_editaccount_number);

        AccountEditRequest accountEditRequest = new AccountEditRequest();

//        btn_back_editaccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                finish();
//            }
//        });
        btn_edit_editaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID="+sessionId);

                            accountEditRequest.setId(Long.valueOf(et_editaccount_id.getText().toString()));
                            accountEditRequest.setAccountName(et_editaccount_name.getText().toString());
                            accountEditRequest.setAccountNumber(et_editaccount_number.getText().toString());

                            AccountControllerApi accountControllerApi = new AccountControllerApi(apiClient);

                            try {
                                String editaccount_cb = accountControllerApi.editAccountUsingPOSTWithHttpInfo(accountEditRequest).getData().getMessage();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(EditAccount.this,editaccount_cb+"!",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(EditAccount.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });


    }
}
