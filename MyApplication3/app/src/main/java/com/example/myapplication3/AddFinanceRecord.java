package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.BudgetControllerApi;
import io.swagger.client.api.FinancialRecordControllerApi;
import io.swagger.client.model.FinancialRecordAddRequest;

public class AddFinanceRecord extends AppCompatActivity {
    private String sessionId;
    private String accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfinancerecord);

        sessionId = getIntent().getStringExtra("SESSION_ID");
        accountId = getIntent().getStringExtra("ACCOUNT_ID");
        System.out.println("sessionId: " + sessionId);

        Spinner spinnerFrdCategory = findViewById(R.id.spinner_frd_category);
// 创建一个包含预算类别的数组
        String[] frdCategories = new String[]{"餐饮", "娱乐", "购物", "交通","学习"};
// 创建一个 ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, frdCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// 将 ArrayAdapter 设置到 Spinner
        spinnerFrdCategory.setAdapter(adapter);
        spinnerFrdCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 获取用户选择的内容
                String selectedCategory = parent.getItemAtPosition(position).toString();
                System.out.println("Selected Category: " + selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 如果未选择任何内容，可以在此处理
            }
        });

        Button btn_add_addfrd = findViewById(R.id.btn_add_addfrd);
        //Button btn_back_addfrd = findViewById(R.id.btn_back_addfrd);

        FinancialRecordAddRequest financialRecordAddRequest = new FinancialRecordAddRequest();

        EditText et_account_IDfrd = findViewById(R.id.et_account_IDfrd);
        EditText et_account_frd = findViewById(R.id.et_account_frd);
        EditText et_account_frdtype = findViewById(R.id.et_account_frdtype);
        EditText et_account_frd_date = findViewById(R.id.et_account_frd_date);
        EditText et_account_frddes = findViewById(R.id.et_account_frddes);

        btn_add_addfrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID="+sessionId);
                            System.out.println("sessionId: " + sessionId);

                            if(accountId!=null){
                                financialRecordAddRequest.setAccountId(Long.valueOf(accountId));
                                et_account_IDfrd.setText(accountId);
                            }else{
                                financialRecordAddRequest.setAccountId(Long.valueOf(et_account_IDfrd.getText().toString()));
                            }
                            financialRecordAddRequest.setTransactionDesc(et_account_frddes.getText().toString());
                            financialRecordAddRequest.setCategory(spinnerFrdCategory.getSelectedItem().toString());
                            financialRecordAddRequest.setTransactionTime(et_account_frd_date.getText().toString());

                            if(et_account_frdtype.getText().toString().equals("支出")){
                                financialRecordAddRequest.setTransactionType(1);
                            }else{
                                financialRecordAddRequest.setTransactionType(0);
                            }


                            //System.out.println(spinnerFrdCategory.getSelectedItem().toString());

                            String budgetText = et_account_frd.getText().toString();// 检查输入是否为空或无效
                            if (budgetText.isEmpty()) {
                                // 处理空输入的情况，例如显示错误消息
                                et_account_frd.setError("请输入账户余额");
                            } else {
                                try {
                                    // 将字符串转换为BigDecimal
                                    BigDecimal budget = new BigDecimal(budgetText);
                                    // 设置账户余额
                                    financialRecordAddRequest.setAmount(budget);
                                } catch (NumberFormatException e) {
                                    // 处理无效数字格式的情况，例如显示错误消息
                                    et_account_frd.setError("请输入有效的数字");
                                }
                            }
                            FinancialRecordControllerApi financialRecordControllerApi = new FinancialRecordControllerApi(apiClient);

                            try {
                                String addfrd_cb = financialRecordControllerApi.addFinancialRecordUsingPOSTWithHttpInfo(financialRecordAddRequest).getData().getMessage();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddFinanceRecord.this,addfrd_cb+"!",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(AddFinanceRecord.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

//        btn_back_addfrd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();// 在此处添加返回的逻辑
//            }
//        });

    }
}
