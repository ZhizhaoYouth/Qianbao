package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.AccountControllerApi;
import io.swagger.client.model.DeleteRequest;

public class DeleteAccount extends AppCompatActivity {
    private String sessionId;
    private String accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteaccount);

        sessionId = getIntent().getStringExtra("SESSION_ID");
        accountId = getIntent().getStringExtra("ACCOUNT_ID");

        Button btn_delaccount = findViewById(R.id.btn_delaccount);
        //Button btn_back_delaccount = findViewById(R.id.btn_back_delaccount);

        EditText et_delaccount_id = findViewById(R.id.et_delaccount_id);

        DeleteRequest deleteRequest = new DeleteRequest();

//        btn_back_delaccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                finish();
//            }
//        });

        btn_delaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID="+sessionId);
                            if(accountId != null){
                                deleteRequest.setId(Long.valueOf(accountId));
                                et_delaccount_id.setText(accountId);
                            }else{
                                deleteRequest.setId(Long.valueOf(et_delaccount_id.getText().toString()));
                            }
                            AccountControllerApi accountControllerApi = new AccountControllerApi(apiClient);

                            try {
                                String deleteaccount_cb = accountControllerApi.deleteAccountUsingPOSTWithHttpInfo(deleteRequest).getData().getMessage();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DeleteAccount.this,deleteaccount_cb+"!",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(DeleteAccount.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

    }
}
