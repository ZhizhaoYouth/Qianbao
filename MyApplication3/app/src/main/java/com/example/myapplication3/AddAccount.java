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
import io.swagger.client.api.UserControllerApi;
import io.swagger.client.model.AccountAddRequest;

public class AddAccount extends AppCompatActivity {
        private String sessionId;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaccount);
        sessionId = getIntent().getStringExtra("SESSION_ID");

        EditText et_account_name1 = findViewById(R.id.et_account_name1);
        EditText et_account_number1 = findViewById(R.id.et_account_number1);
        EditText et_account_balance1 = findViewById(R.id.et_account_balance1);

        AccountAddRequest accountAddRequest = new AccountAddRequest();

        Button btn_add1 = findViewById(R.id.btn_add1);
        //Button btn_back1 = findViewById(R.id.btn_back1);

        btn_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID="+sessionId);
                            accountAddRequest.setAccountName(et_account_name1.getText().toString());
                            accountAddRequest.setAccountNumber(et_account_number1.getText().toString());

                            String balanceText = et_account_balance1.getText().toString();

// 检查输入是否为空或无效
                            if (balanceText.isEmpty()) {
                                // 处理空输入的情况，例如显示错误消息
                                et_account_balance1.setError("请输入账户余额");
                            } else {
                                try {
                                    // 将字符串转换为BigDecimal
                                    BigDecimal balance = new BigDecimal(balanceText);
                                    // 设置账户余额
                                    accountAddRequest.setBalance(balance);
                                } catch (NumberFormatException e) {
                                    // 处理无效数字格式的情况，例如显示错误消息
                                    et_account_balance1.setError("请输入有效的数字");
                                }
                            }

                            AccountControllerApi accountControllerApi = new AccountControllerApi(apiClient);

                            try {
                                String addaccount_cb = accountControllerApi.addAccountUsingPOSTWithHttpInfo(accountAddRequest).getData().getMessage();


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddAccount.this,addaccount_cb+"!",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(AddAccount.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
//        btn_back1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


    }
}
