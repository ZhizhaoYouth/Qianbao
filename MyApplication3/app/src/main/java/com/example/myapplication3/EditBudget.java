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
import io.swagger.client.api.AccountControllerApi;
import io.swagger.client.api.BudgetControllerApi;
import io.swagger.client.model.AccountEditRequest;
import io.swagger.client.model.BudgetEditRequest;

public class EditBudget extends AppCompatActivity {
    private String sessionId;
    private String budgetId;
    private String accountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbudget);

        sessionId = getIntent().getStringExtra("SESSION_ID");
        budgetId = getIntent().getStringExtra("BUDGET_ID");
        accountId = getIntent().getStringExtra("ACCOUNT_ID");

        Button btn_edit_editbudget = findViewById(R.id.btn_edit_editbudget);
        //Button btn_back_editbudget = findViewById(R.id.btn_back_editbudget);

        EditText et_edit_IDbgt = findViewById(R.id.et_edit_IDbgt);
        EditText et_edit_IDaccount = findViewById(R.id.et_edit_IDaccount);
        EditText et_edit_budget = findViewById(R.id.et_edit_budget);
        EditText et_edit_bgt_startdate = findViewById(R.id.et_edit_bgt_startdate);
        EditText et_edit_bgt_enddate = findViewById(R.id.et_edit_bgt_enddate);
        EditText et_edit_bgtdes = findViewById(R.id.et_edit_bgtdes);

        Spinner spinnerBudgetCategory = findViewById(R.id.spinner_editbudget_category);
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


        BudgetEditRequest budgetEditRequest = new BudgetEditRequest();

//        btn_back_editbudget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                finish();
//            }
//        });
        btn_edit_editbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID="+sessionId);


                            if(budgetId != null){
                                budgetEditRequest.setId(Long.valueOf(budgetId));
                                et_edit_IDbgt.setText(budgetId);
                            }
                            else{
                                budgetEditRequest.setId(Long.valueOf(et_edit_IDbgt.getText().toString()));
                            }
                            if(accountId != null){
                                budgetEditRequest.setAccountId(Long.valueOf(accountId));
                                et_edit_IDaccount.setText(accountId);
                            }else{
                                budgetEditRequest.setAccountId(Long.valueOf(et_edit_IDaccount.getText().toString()));
                            }
                            budgetEditRequest.setStartDate(et_edit_bgt_startdate.getText().toString());
                            budgetEditRequest.setEndDate(et_edit_bgt_enddate.getText().toString());
                            budgetEditRequest.setBudgetDesc(et_edit_bgtdes.getText().toString());
                            budgetEditRequest.setCategory(spinnerBudgetCategory.getSelectedItem().toString());


                            String budgetText = et_edit_budget.getText().toString();// 检查输入是否为空或无效
                            if (budgetText.isEmpty()) {
                                // 处理空输入的情况，例如显示错误消息
                                et_edit_budget.setError("请输入账户余额");
                            } else {
                                try {
                                    // 将字符串转换为BigDecimal
                                    BigDecimal budget = new BigDecimal(budgetText);
                                    // 设置账户余额
                                    budgetEditRequest.setAmount(budget);
                                } catch (NumberFormatException e) {
                                    // 处理无效数字格式的情况，例如显示错误消息
                                    et_edit_budget.setError("请输入有效的数字");
                                }
                            }


                            BudgetControllerApi budgetControllerApi = new BudgetControllerApi(apiClient);

                            try {
                                String editbudget_cb = budgetControllerApi.editBudgetUsingPOSTWithHttpInfo(budgetEditRequest).getData().getMessage();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(EditBudget.this,editbudget_cb+"!",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(EditBudget.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });


    }
}
