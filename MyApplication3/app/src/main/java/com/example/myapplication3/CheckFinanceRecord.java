package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.FinancialRecordControllerApi;
import io.swagger.client.model.FinancialRecordQueryRequest;

public class CheckFinanceRecord extends AppCompatActivity {

    private String sessionId;
    private RecyclerView recyclerView;
    private FinanceRecordAdapter financeRecordAdapter;
    private List<MyFinanceRecord> myFinanceRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkfinancerecord);
        sessionId = getIntent().getStringExtra("SESSION_ID");

        //Button btn_check = findViewById(R.id.btn_check_checkfinancerecord);
        //Button btn_back = findViewById(R.id.btn_back_checkfinancerecord);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID=" + sessionId);

                            FinancialRecordQueryRequest financialRecordQueryRequest = new FinancialRecordQueryRequest();
                            FinancialRecordControllerApi financialRecordControllerApi = new FinancialRecordControllerApi(apiClient);

                            ApiResponse response = financialRecordControllerApi.listMyFinancialRecordVOByPageUsingPOSTWithHttpInfo(financialRecordQueryRequest);
                            String jsonResponse = new Gson().toJson(response.getData());

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

                            myFinanceRecordList = new ArrayList<>();

                            for (JsonElement recordElement : records) {
                                JsonObject record = recordElement.getAsJsonObject();

                                String financeRecordId = record.get("id").getAsString();
                                String transactionDesc = record.get("transactionDesc").getAsString();
                                String accountId = record.get("accountId").getAsString();
                                String amount = record.get("amount").getAsBigDecimal().toString();
                                //String transactionType = record.get("transactionType").getAsInt() == 1 ? "支出" : "收入";
                                String transactionType = record.get("transactionType").getAsString();
                                String category = record.get("category").getAsString();
                                String transactionTime = record.get("transactionTime").getAsJsonObject().get("dateTime").getAsJsonObject().get("date").getAsJsonObject().get("year").getAsInt() + "-"
                                        + record.get("transactionTime").getAsJsonObject().get("dateTime").getAsJsonObject().get("date").getAsJsonObject().get("month").getAsInt()+ "-"
                                        + record.get("transactionTime").getAsJsonObject().get("dateTime").getAsJsonObject().get("date").getAsJsonObject().get("day").getAsInt();


                                myFinanceRecordList.add(new MyFinanceRecord(financeRecordId, transactionDesc, accountId, amount, transactionType, category, transactionTime));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView = findViewById(R.id.recyclerView_financerecord);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(CheckFinanceRecord.this));
                                    financeRecordAdapter = new FinanceRecordAdapter(myFinanceRecordList);
                                    recyclerView.setAdapter(financeRecordAdapter);
                                }
                            });

                        } catch (ApiException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CheckFinanceRecord.this, "接口错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CheckFinanceRecord.this, "运行时错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CheckFinanceRecord.this, "未知错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

//        btn_check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            ApiClient apiClient = new ApiClient();
//                            apiClient.setBasePath(myURL);
//                            apiClient.addDefaultHeader("Cookie", "JSESSIONID=" + sessionId);
//
//                            FinancialRecordQueryRequest financialRecordQueryRequest = new FinancialRecordQueryRequest();
//                            FinancialRecordControllerApi financialRecordControllerApi = new FinancialRecordControllerApi(apiClient);
//
//                            ApiResponse response = financialRecordControllerApi.listMyFinancialRecordVOByPageUsingPOSTWithHttpInfo(financialRecordQueryRequest);
//                            String jsonResponse = new Gson().toJson(response.getData());
//
//                            Gson gson = new Gson();
//                            JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);
//
//                            if (responseObject == null) {
//                                throw new RuntimeException("Failed to parse JSON response.");
//                            }
//
//                            JsonObject dataObject = responseObject.getAsJsonObject("data");
//
//                            if (dataObject == null || !dataObject.has("records") || dataObject.get("records").isJsonNull()) {
//                                throw new RuntimeException("No records field found in the JSON response.");
//                            }
//
//                            JsonArray records = dataObject.getAsJsonArray("records");
//
//                            myFinanceRecordList = new ArrayList<>();
//
//                            for (JsonElement recordElement : records) {
//                                JsonObject record = recordElement.getAsJsonObject();
//
//                                String financeRecordId = record.get("id").getAsString();
//                                String transactionDesc = record.get("transactionDesc").getAsString();
//                                String accountId = record.get("accountId").getAsString();
//                                String amount = record.get("amount").getAsBigDecimal().toString();
//                                String transactionType = record.get("transactionType").getAsInt() == 0 ? "支出" : "收入";
//                                String category = record.get("category").getAsString();
//                                String transactionTime = record.get("transactionTime").getAsJsonObject().get("dateTime").getAsJsonObject().get("date").getAsJsonObject().get("year").getAsInt() + "-"
//                                        + record.get("transactionTime").getAsJsonObject().get("dateTime").getAsJsonObject().get("date").getAsJsonObject().get("month").getAsInt()+ "-"
//                                        + record.get("transactionTime").getAsJsonObject().get("dateTime").getAsJsonObject().get("date").getAsJsonObject().get("day").getAsInt();
//
//
//                                myFinanceRecordList.add(new MyFinanceRecord(financeRecordId, transactionDesc, accountId, amount, transactionType, category, transactionTime));
//                            }
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    recyclerView = findViewById(R.id.recyclerView_financerecord);
//                                    recyclerView.setLayoutManager(new LinearLayoutManager(CheckFinanceRecord.this));
//                                    financeRecordAdapter = new FinanceRecordAdapter(myFinanceRecordList);
//                                    recyclerView.setAdapter(financeRecordAdapter);
//                                }
//                            });
//
//                        } catch (ApiException e) {
//                            e.printStackTrace();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(CheckFinanceRecord.this, "接口错误", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } catch (RuntimeException e) {
//                            e.printStackTrace();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(CheckFinanceRecord.this, "运行时错误", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(CheckFinanceRecord.this, "未知错误", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    }
//                }).start();
//            }
//        });

//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}
