package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {
    private String sessionId;
    private String userRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);

        sessionId = getIntent().getStringExtra("SESSION_ID");
        userRole = getIntent().getStringExtra("USER_ROLE");

        Button quitbutton = findViewById(R.id.QuitButton);
        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn_home2 = findViewById(R.id.btn_home2);
        btn_home2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                intent.putExtra("SESSION_ID", sessionId);
                intent.putExtra("USER_ROLE",userRole);
                startActivity(intent);
                finish();
            }
        });

        Button btn_loginlog = findViewById(R.id.loginlog);
        btn_loginlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), LoginLogPage.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
            }
        });




    }
}
