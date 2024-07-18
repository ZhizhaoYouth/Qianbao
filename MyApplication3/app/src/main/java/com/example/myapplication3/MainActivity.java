package com.example.myapplication3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication3.utils.IPUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.TimeZone;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.LoginLogControllerApi;
import io.swagger.client.api.UserControllerApi;
import io.swagger.client.model.LoginLogAddRequest;
import io.swagger.client.model.UserLoginRequest;

public class MainActivity extends AppCompatActivity {

    public static final String myURL = "XXXX";
    private String publicIpAddress;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private String addressString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        IPUtils.getPublicIpAddress(new IPUtils.IPCallback() {
            @Override
            public void onIPReceived(String ip) {
                if (ip != null) {
                    publicIpAddress = ip;
                    Log.d("MainActivity", "Public IP: " + publicIpAddress);
                    //Toast.makeText(MainActivity.this, "Public IP: " + publicIpAddress, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unable to fetch public IP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 获取当前时间
        Date currentTime = new Date();
        // 设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        // 设置时区为UTC（协调世界时）
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        // 格式化日期并转换为字符串
        String formattedTime = sdf.format(currentTime);
        // 打印输出
        Log.d("Formatted Time", formattedTime);

//        LoginLogAddRequest loginLogAddRequest = new LoginLogAddRequest();
//        loginLogAddRequest.setLoginTime(formattedTime);
//        loginLogAddRequest.setIpAddress(publicIpAddress);
//        loginLogAddRequest.setLocation(addressString);
//        loginLogAddRequest.setBrowserInfo("none");
//        loginLogAddRequest.setDeviceType("Phone");
//        loginLogAddRequest.setOperatingSystem("Android");


        EditText edt_uid = findViewById(R.id.edt_uid);
        EditText edt_upwd = findViewById(R.id.edt_upwd);
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        String json1 = new Gson().toJson(userLoginRequest);
        //BigDecimal Balance返回值
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_register = findViewById(R.id.btn_register);

        //点按事件
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ApiClient apiClient = new ApiClient();
                            apiClient.setBasePath(myURL);
                            userLoginRequest.setUserAccount(edt_uid.getText().toString());
                            userLoginRequest.setUserPassword(edt_upwd.getText().toString());

                            UserControllerApi userControllerApi = new UserControllerApi(apiClient);
                            try {
                                String login_cb = userControllerApi.userLoginUsingPOSTWithHttpInfo(userLoginRequest).getData().getMessage();
                                ApiResponse response = userControllerApi.userLoginUsingPOSTWithHttpInfo(userLoginRequest);
                                Log.d("API Response", new Gson().toJson(response));
                                String sessionId = null;

                                // Convert the response data to JSON string
                                String jsonResponse = new Gson().toJson(response.getData());
                                System.out.println("JSON Response: " + jsonResponse);
                                Gson gson = new Gson();
                                JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);
                                if (responseObject == null) {
                                    throw new RuntimeException("Failed to parse JSON response.");
                                }
                                JsonObject dataObject = responseObject.getAsJsonObject("data");
//                                if (dataObject == null) {
//                                    throw new RuntimeException("No data field found in the JSON response.");
//                                }
//                                String userRole = dataObject.get("userRole").getAsString();
//                                System.out.println("User Role: " + userRole);

                                String cookiesHeader = response.getHeaders().toString();
                                System.out.println(cookiesHeader);
                                if (cookiesHeader != null) {
                                    Pattern pattern = Pattern.compile("JSESSIONID=([^;]+)");
                                    Matcher matcher = pattern.matcher(cookiesHeader);

                                    if (matcher.find()) {
                                        sessionId = matcher.group(1);
                                    }
                                }
                                System.out.println(sessionId);
                                System.out.println(addressString);
                                System.out.println(publicIpAddress);
                                System.out.println(formattedTime);
                                apiClient.addDefaultHeader("Cookie", "JSESSIONID="+sessionId);
                                LoginLogAddRequest loginLogAddRequest = new LoginLogAddRequest();
                                loginLogAddRequest.setLoginTime(formattedTime);
                                loginLogAddRequest.setIpAddress(publicIpAddress);
                                loginLogAddRequest.setLocation(addressString);
                                loginLogAddRequest.setBrowserInfo("none");
                                loginLogAddRequest.setDeviceType("Phone");
                                loginLogAddRequest.setOperatingSystem("Android");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, login_cb + "!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                if (login_cb.equals("ok")) {
                                    String userRole = dataObject.get("userRole").getAsString();
                                    String userName = dataObject.get("userName").getAsString();
                                    String userAvatar = dataObject.get("userAvatar").getAsString();
                                    System.out.println("User Role: " + userRole);
                                    System.out.println("User Name: " + userName);
                                    System.out.println("User Avatar: " + userAvatar);
                                    LoginLogControllerApi loginLogControllerApi = new LoginLogControllerApi(apiClient);
                                    String loginlog_cb = loginLogControllerApi.addLoginLogUsingPOSTWithHttpInfo(loginLogAddRequest).getData().getMessage();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(MainActivity.this, loginlog_cb + "!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    intent.putExtra("SESSION_ID", sessionId);
                                    intent.putExtra("USER_ROLE", userRole);
                                    intent.putExtra("USER_NAME", userName);
                                    intent.putExtra("USER_AVATAR", userAvatar);
                                    startActivity(intent);

                                }

                            } catch (ApiException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "发生未知错误!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        // 请求位置权限
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 权限未被授予
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // 将经纬度转换为地址
                            fetchAddressFromCoordinates(location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }

    private void fetchAddressFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                addressString = address.getAddressLine(0); // 获取完整地址
                runOnUiThread(() -> {
                    Log.d("Location", "Address: " + addressString);
                    //Toast.makeText(MainActivity.this, "Address: " + addressString, Toast.LENGTH_LONG).show();
                });
            } else {
                runOnUiThread(() -> {
                    Log.d("Location", "No address found");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
