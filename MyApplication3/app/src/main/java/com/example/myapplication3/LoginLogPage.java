package com.example.myapplication3;

import static com.example.myapplication3.MainActivity.myURL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import io.swagger.client.api.LoginLogControllerApi;
import io.swagger.client.model.LoginLogQueryRequest;

public class LoginLogPage extends AppCompatActivity {

    String sessionId;
    private RecyclerView recyclerView;
    private LoginLogAdapter loginLogAdapter;
    private List<MyLoginLog> myLoginLogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginlogpage);
        sessionId = getIntent().getStringExtra("SESSION_ID");
        System.out.println("sessionId: " + sessionId);
        Button btn_check = findViewById(R.id.btn_check_loginlog);
        Button btn_back = findViewById(R.id.btn_back_loginlog);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            apiClient.addDefaultHeader("Cookie", "JSESSIONID=" + sessionId);
                            System.out.println("sessionId: " + sessionId);
                            LoginLogQueryRequest loginLogQueryRequest = new LoginLogQueryRequest();
                            LoginLogControllerApi loginLogControllerApi = new LoginLogControllerApi(apiClient);
                            try {
                                // 获取响应对象
                                ApiResponse response = loginLogControllerApi.listMyLoginLogVOByPageUsingPOSTWithHttpInfo(loginLogQueryRequest);
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

                                    // 创建MyLoginLog对象列表
                                    myLoginLogList = new ArrayList<>();

                                    for (JsonElement recordElement : records) {
                                        JsonObject record = recordElement.getAsJsonObject();

                                        String id = record.has("id") && !record.get("id").isJsonNull() ? record.get("id").getAsString() : "";
                                        String userId = record.has("userId") && !record.get("userId").isJsonNull() ? record.get("userId").getAsString() : "";

                                        // 解析时间字段
                                        String loginTime = parseDateTime(record.getAsJsonObject("loginTime"));
                                        String createTime = parseDateTime(record.getAsJsonObject("createTime"));
                                        String updateTime = parseDateTime(record.getAsJsonObject("updateTime"));

                                        String deviceType = record.has("deviceType") && !record.get("deviceType").isJsonNull() ? record.get("deviceType").getAsString() : "";
                                        String ipAddress = record.has("ipAddress") && !record.get("ipAddress").isJsonNull() ? record.get("ipAddress").getAsString() : "";
                                        String location = record.has("location") && !record.get("location").isJsonNull() ? record.get("location").getAsString() : "";
                                        String browserInfo = record.has("browserInfo") && !record.get("browserInfo").isJsonNull() ? record.get("browserInfo").getAsString() : "";
                                        String operatingSystem = record.has("operatingSystem") && !record.get("operatingSystem").isJsonNull() ? record.get("operatingSystem").getAsString() : "";

                                        myLoginLogList.add(new MyLoginLog(id, userId, loginTime, deviceType, ipAddress, location, browserInfo, operatingSystem, createTime, updateTime));
                                    }

                                    // 在UI线程中更新RecyclerView
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recyclerView = findViewById(R.id.recyclerView_loginlog);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(LoginLogPage.this));
                                            loginLogAdapter = new LoginLogAdapter(myLoginLogList);
                                            recyclerView.setAdapter(loginLogAdapter);
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
                                    Toast.makeText(LoginLogPage.this, "发生未知错误!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }


    private String parseDateTime(JsonObject dateTimeObject) {
        if (dateTimeObject == null) return "";

        JsonObject dateObject = dateTimeObject.getAsJsonObject("dateTime").getAsJsonObject("date");
        JsonObject timeObject = dateTimeObject.getAsJsonObject("dateTime").getAsJsonObject("time");

        int year = dateObject.get("year").getAsInt();
        int month = dateObject.get("month").getAsInt();
        int day = dateObject.get("day").getAsInt();
        int hour = timeObject.get("hour").getAsInt();
        int minute = timeObject.get("minute").getAsInt();
        int second = timeObject.get("second").getAsInt();

        // 格式化日期时间字符串
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }
}


