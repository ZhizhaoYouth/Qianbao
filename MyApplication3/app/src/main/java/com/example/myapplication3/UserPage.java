package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class UserPage extends AppCompatActivity {
    private String sessionId;
    private String userRole;
    private String userName;
    private String userAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        sessionId = getIntent().getStringExtra("SESSION_ID");
        userRole = getIntent().getStringExtra("USER_ROLE");
        userName = getIntent().getStringExtra("USER_NAME");
        userAvatar = getIntent().getStringExtra("USER_AVATAR");
        System.out.println("User Role: " + userRole);
        System.out.println("User Name: " + userName);
        System.out.println("User Avatar: " + userAvatar);

        TextView usernameTextView = findViewById(R.id.uesrname_textview);
        ImageView userImageView = findViewById(R.id.imageView5);

        // 设置用户名
        usernameTextView.setText(userName);

        if (userAvatar != null && !userAvatar.isEmpty()) {
            Glide.with(this)
                    .load(userAvatar)
                    .placeholder(R.drawable.icon_user) // 加载中的占位符
                    .error(R.drawable.icon_user) // 加载失败的占位符
                    .into(userImageView);
        } else {
            userImageView.setImageResource(R.drawable.icon_user);
        }


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
                intent.putExtra("USER_ROLE", userRole);
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("USER_AVATAR", userAvatar);
                startActivity(intent);
                finish();
            }
        });

        Button btn_getreport = findViewById(R.id.btn_getreport);
        btn_getreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ReportPage.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
            }
        });


    }
}
