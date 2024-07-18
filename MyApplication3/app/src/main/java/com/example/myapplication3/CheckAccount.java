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
import io.swagger.client.api.AccountControllerApi;
import io.swagger.client.model.AccountQueryRequest;
import io.swagger.client.model.AccountVO;

public class CheckAccount extends AppCompatActivity {
    String sessionId;
    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;
    private List<MyAccount> myaccountList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkaccount);
        sessionId = getIntent().getStringExtra("SESSION_ID");
        System.out.println("sessionId: " + sessionId);
        mContext = CheckAccount.this;

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

    private void fetchData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiClient apiClient = new ApiClient();
                    apiClient.setBasePath(myURL);
                    apiClient.addDefaultHeader("Cookie", "JSESSIONID=" + sessionId);
                    AccountQueryRequest accountQueryRequest = new AccountQueryRequest();
                    AccountControllerApi accountControllerApi = new AccountControllerApi(apiClient);
                    try {
                        ApiResponse response = accountControllerApi.listMyAccountVOByPageUsingPOSTWithHttpInfo(accountQueryRequest);
                        String jsonResponse = new Gson().toJson(response.getData());

                        Gson gson = new Gson();
                        JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);
                        JsonObject dataObject = responseObject.getAsJsonObject("data");
                        JsonArray records = dataObject.getAsJsonArray("records");

                        myaccountList = new ArrayList<>();
                        for (JsonElement recordElement : records) {
                            JsonObject record = recordElement.getAsJsonObject();

                            String id = record.get("id").getAsString();
                            String accountName = record.get("accountName").getAsString();
                            String accountNumber = record.get("accountNumber").getAsString();
                            String cardType = record.getAsJsonObject("bankInfo").get("cardType").getAsString();
                            String bankLogoURL = record.getAsJsonObject("bankInfo").get("bankLogoURL").getAsString();
                            String balance = record.get("balance").getAsString();

                            myaccountList.add(new MyAccount(id, accountName, accountNumber, cardType, bankLogoURL, balance));
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView = findViewById(R.id.recyclerView_account);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CheckAccount.this));
                                accountAdapter = new AccountAdapter(myaccountList);
                                recyclerView.setAdapter(accountAdapter);

                                accountAdapter.setOnItemClickListener(new AccountAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        MyAccount clickedAccount = myaccountList.get(position);
                                        // 事件处理逻辑
                                        initPopWindowAccount(recyclerView,clickedAccount);

                                        Toast.makeText(CheckAccount.this, "Clicked: " + clickedAccount.getAccountName(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    } catch (ApiException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CheckAccount.this, "发生未知错误!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CheckAccount.this, "发生未知错误!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CheckAccount.this, "发生未知错误!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CheckAccount.this, "发生未知错误!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }


    private void initPopWindowAccount(View v, MyAccount account) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_popaccount, null, false);
        Button btn_del_account = view.findViewById(R.id.btn_del_account);
        Button btn_add_budget = view.findViewById(R.id.btn_add_budget);
        Button btn_add_financerecord = view.findViewById(R.id.btn_add_financerecord);

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
        Log.d("CheckAccount", "Popup window shown for account: " + account.getAccountName());

        btn_del_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteAccount.class);
                intent.putExtra("SESSION_ID", sessionId);
                intent.putExtra("ACCOUNT_ID", account.getAccount_Id()); // 传递当前点击的账户ID
                startActivity(intent);
                Toast.makeText(CheckAccount.this, "删除账户: " + account.getAccountName(), Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });

        btn_add_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddBudget.class);
                intent.putExtra("SESSION_ID", sessionId);
                intent.putExtra("ACCOUNT_ID", account.getAccount_Id()); // 传递当前点击的账户ID
                startActivity(intent);
                Toast.makeText(CheckAccount.this, "添加预算: " + account.getAccountName(), Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });

        btn_add_financerecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddFinanceRecord.class);
                intent.putExtra("SESSION_ID", sessionId);
                intent.putExtra("ACCOUNT_ID", account.getAccount_Id()); // 传递当前点击的账户ID
                startActivity(intent);
                Toast.makeText(CheckAccount.this, "添加交易记录: " + account.getAccountName(), Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });

    }

}
