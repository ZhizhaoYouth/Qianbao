package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.BudgetControllerApi;
import io.swagger.client.model.BudgetQueryRequest;

public class CheckBudget extends AppCompatActivity {

    String sessionId;
    private RecyclerView recyclerView;
    private BudgetAdapter budgetAdapter;
    private List<MyBudget> myBudgetList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbudget);
        sessionId = getIntent().getStringExtra("SESSION_ID");
        System.out.println("sessionId: " + sessionId);
        mContext = CheckBudget.this;
        //Button btn_check1 = findViewById(R.id.btn_check_checkaccount);
        //Button btn_back2 = findViewById(R.id.btn_back_checkaccount);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        fetchData();
    }

    private void fetchData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiClient apiClient = new ApiClient();
                    apiClient.setBasePath(myURL);
                    apiClient.addDefaultHeader("Cookie", "JSESSIONID=" + sessionId);
                    System.out.println("sessionId: " + sessionId);
                    BudgetQueryRequest budgetQueryRequest = new BudgetQueryRequest();
                    BudgetControllerApi budgetControllerApi = new BudgetControllerApi(apiClient);
                    try {
                        // 获取响应对象
                        ApiResponse response = budgetControllerApi.listMyBudgetVOByPageUsingPOSTWithHttpInfo(budgetQueryRequest);
                        String jsonResponse = new Gson().toJson(response.getData());

                        System.out.println("JSON Response: " + jsonResponse);

                        // 使用Gson库解析JSON数据
                        Gson gson = new Gson();
                        JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);

                        if (responseObject == null) {
                            throw new RuntimeException("Failed to parse JSON response.");
                        }

                        JsonObject dataObject = responseObject.getAsJsonObject("data");

                        if (dataObject == null || !dataObject.has("records") || dataObject.get("records").isJsonNull()) {
                            throw new RuntimeException("No records field found in the JSON response.");
                        }

                        JsonArray records = dataObject.getAsJsonArray("records");

                        if (records == null) {
                            throw new RuntimeException("Records field is null in the JSON response.");
                        }

                        // 创建MyBudget对象列表
                        myBudgetList = new ArrayList<>();

                        for (JsonElement recordElement : records) {
                            JsonObject record = recordElement.getAsJsonObject();

                            String budget_id = record.has("id") && !record.get("id").isJsonNull() ? record.get("id").getAsString() : "";
                            String budgetdesc = record.has("budgetDesc") && !record.get("budgetDesc").isJsonNull() ? record.get("budgetDesc").getAsString() : "";
                            String account_id = record.has("accountId") && !record.get("accountId").isJsonNull() ? record.get("accountId").getAsString() : "";
                            String budget = record.has("amount") && !record.get("amount").isJsonNull() ? record.get("amount").getAsString() : "";
                            String bgt_category = record.has("category") && !record.get("category").isJsonNull() ? record.get("category").getAsString() : "";

                            String startDate = "";
                            if (record.has("startDate") && !record.get("startDate").isJsonNull()) {
                                JsonObject startDateObj = record.getAsJsonObject("startDate").getAsJsonObject("dateTime").getAsJsonObject("date");
                                startDate = startDateObj.get("year").getAsString() + "-" +
                                        startDateObj.get("month").getAsString() + "-" +
                                        startDateObj.get("day").getAsString();
                            }

                            String endDate = "";
                            if (record.has("endDate") && !record.get("endDate").isJsonNull()) {
                                JsonObject endDateObj = record.getAsJsonObject("endDate").getAsJsonObject("dateTime").getAsJsonObject("date");
                                endDate = endDateObj.get("year").getAsString() + "-" +
                                        endDateObj.get("month").getAsString() + "-" +
                                        endDateObj.get("day").getAsString();
                            }

                            // 检查每个字段的实际类型
                            System.out.println("Budget ID: " + (record.has("id") ? record.get("id").toString() : "null"));
                            System.out.println("Budget Description: " + (record.has("budgetDesc") ? record.get("budgetDesc").toString() : "null"));
                            System.out.println("Account ID: " + (record.has("accountId") ? record.get("accountId").toString() : "null"));
                            System.out.println("Budget: " + (record.has("amount") ? record.get("amount").toString() : "null"));
                            System.out.println("Budget Category: " + (record.has("category") ? record.get("category").toString() : "null"));
                            System.out.println("Start Date: " + startDate);
                            System.out.println("End Date: " + endDate);

                            myBudgetList.add(new MyBudget(budget_id, budgetdesc, account_id, budget, bgt_category, startDate, endDate));
                        }

                        // 在UI线程中更新RecyclerView
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView = findViewById(R.id.recyclerView_budget);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CheckBudget.this));
                                budgetAdapter = new BudgetAdapter(myBudgetList);
                                recyclerView.setAdapter(budgetAdapter);

                                budgetAdapter.setOnItemClickListener(new BudgetAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        MyBudget clickedBudget = myBudgetList.get(position);
                                        // 事件处理逻辑
                                        initPopWindowBudget(recyclerView,clickedBudget);

                                        Toast.makeText(CheckBudget.this, "Clicked: " + clickedBudget.getBudget_Id(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        throw new RuntimeException("JSON syntax error: " + e.getMessage());
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Runtime error: " + e.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CheckBudget.this, "发生未知错误!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void initPopWindowBudget(View v, MyBudget budget) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_popbudget, null, false);
        Button btn_del_budget = view.findViewById(R.id.btn_del_budget);
        Button btn_edit_budget = view.findViewById(R.id.btn_edit_budget);

        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        popWindow.showAsDropDown(v, 60, -1300);


        btn_del_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteBudget.class);
                intent.putExtra("SESSION_ID", sessionId);
                intent.putExtra("BUDGET_ID", budget.getBudget_Id());
                startActivity(intent);
                Toast.makeText(CheckBudget.this, "删除预算: " + budget.getBudget_Id(), Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });

        btn_edit_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditBudget.class);
                intent.putExtra("SESSION_ID", sessionId);
                intent.putExtra("BUDGET_ID", budget.getBudget_Id());
                intent.putExtra("ACCOUNT_ID", budget.getAccount_Id());
                startActivity(intent);
                Toast.makeText(CheckBudget.this, "修改预算: " + budget.getBudget_Id(), Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });
    }

}
