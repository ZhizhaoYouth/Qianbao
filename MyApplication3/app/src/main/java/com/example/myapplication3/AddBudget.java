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

import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.AccountControllerApi;
import io.swagger.client.api.BudgetControllerApi;
import io.swagger.client.model.BudgetAddRequest;

public class AddBudget extends AppCompatActivity {
    private String sessionId;
    private String accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbudget);
        sessionId = getIntent().getStringExtra("SESSION_ID");
        accountId = getIntent().getStringExtra("ACCOUNT_ID");
        System.out.println("sessionId: " + sessionId);

        Spinner spinnerBudgetCategory = findViewById(R.id.spinner_budget_category);
// 创建一个包含预算类别的数组
        String[] budgetCategories = new String[]{"餐饮", "娱乐", "购物", "交通","学习"};
// 创建一个 ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, budgetCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// 将 ArrayAdapter 设置到 Spinner
        spinnerBudgetCategory.setAdapter(adapter);
        spinnerBudgetCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        Button btn_add2 = findViewById(R.id.btn_add2);
        //Button btn_back3 = findViewById(R.id.btn_back3);

        EditText et_account_IDbgt = findViewById(R.id.et_account_IDbgt);
        EditText et_account_budget = findViewById(R.id.et_account_budget);
        EditText et_account_bgt_startdate = findViewById(R.id.et_account_bgt_startdate);
        EditText et_account_bgt_enddate = findViewById(R.id.et_account_bgt_enddate);
        EditText et_account_bgtdes = findViewById(R.id.et_account_bgtdes);

        BudgetAddRequest budgetAddRequest = new BudgetAddRequest();


        btn_add2.setOnClickListener(new View.OnClickListener() {
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

                            if(accountId != null){
                                budgetAddRequest.setAccountId(Long.valueOf(accountId));
                                et_account_IDbgt.setText(accountId);
                            }else{
                                budgetAddRequest.setAccountId(Long.valueOf(et_account_IDbgt.getText().toString()));
                            }

                            budgetAddRequest.setBudgetDesc(et_account_bgtdes.getText().toString());
                            budgetAddRequest.setStartDate(et_account_bgt_startdate.getText().toString());
                            budgetAddRequest.setEndDate(et_account_bgt_enddate.getText().toString());
                            budgetAddRequest.setCategory(spinnerBudgetCategory.getSelectedItem().toString());
                            System.out.println(spinnerBudgetCategory.getSelectedItem().toString());
                            String budgetText = et_account_budget.getText().toString();// 检查输入是否为空或无效
                            if (budgetText.isEmpty()) {
                                // 处理空输入的情况，例如显示错误消息
                                et_account_budget.setError("请输入账户余额");
                            } else {
                                try {
                                    // 将字符串转换为BigDecimal
                                    BigDecimal budget = new BigDecimal(budgetText);
                                    // 设置账户余额
                                    budgetAddRequest.setAmount(budget);
                                } catch (NumberFormatException e) {
                                    // 处理无效数字格式的情况，例如显示错误消息
                                    et_account_budget.setError("请输入有效的数字");
                                }
                            }
                            BudgetControllerApi budgetControllerApi = new BudgetControllerApi(apiClient);

                            try {
                                String addbudget_cb = budgetControllerApi.addBudgetUsingPOSTWithHttpInfo(budgetAddRequest).getData().getMessage();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddBudget.this,addbudget_cb+"!",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(AddBudget.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

//        btn_back3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

    }
}
