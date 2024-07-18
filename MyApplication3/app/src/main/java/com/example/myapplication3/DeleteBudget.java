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
import io.swagger.client.api.BudgetControllerApi;
import io.swagger.client.model.DeleteRequest;

public class DeleteBudget extends AppCompatActivity {
    private String sessionId;
    private String budgetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletebudget);

        sessionId = getIntent().getStringExtra("SESSION_ID");
        budgetId = getIntent().getStringExtra("BUDGET_ID");

        Button btn_delbudget = findViewById(R.id.btn_delbudget);
        //Button btn_back_delbudget = findViewById(R.id.btn_back_delbudget);

        EditText et_delbudget_id = findViewById(R.id.et_delbudget_id);

        DeleteRequest deleteRequest = new DeleteRequest();

//        btn_back_delbudget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                finish();
//            }
//        });

        btn_delbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID="+sessionId);
                            if(budgetId!=null){
                                et_delbudget_id.setText(budgetId);
                                deleteRequest.setId(Long.valueOf(budgetId));
                            }else{
                                deleteRequest.setId(Long.valueOf(et_delbudget_id.getText().toString()));
                            }

                            BudgetControllerApi budgetControllerApi = new BudgetControllerApi(apiClient);

                            try {
                                String deletebudget_cb = budgetControllerApi.deleteBudgetUsingPOSTWithHttpInfo(deleteRequest).getData().getMessage();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DeleteBudget.this,deletebudget_cb+"!",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(DeleteBudget.this,"发生未知错误!",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

    }
}
