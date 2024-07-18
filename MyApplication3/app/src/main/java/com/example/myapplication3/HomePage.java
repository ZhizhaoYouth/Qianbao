package com.example.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.model.Account;

public class HomePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AccountAdapter accountAdapter;
    private List<Account> accountList;
    private Context mContext;
    String sessionId;
    String userRole;
    String userName;
    String userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        sessionId = getIntent().getStringExtra("SESSION_ID");
        userRole = getIntent().getStringExtra("USER_ROLE");
        userName = getIntent().getStringExtra("USER_NAME");
        userAvatar = getIntent().getStringExtra("USER_AVATAR");
        System.out.println("Session ID: " + sessionId);

        mContext = HomePage.this;

        Button btn_profile1 = findViewById(R.id.btn_profile1);
        btn_profile1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(userRole.equals("admin")){
                    Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                    intent.putExtra("SESSION_ID", sessionId);
                    intent.putExtra("USER_ROLE", userRole);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), UserPage.class);
                    intent.putExtra("SESSION_ID", sessionId);
                    intent.putExtra("USER_ROLE", userRole);
                    intent.putExtra("USER_NAME", userName);
                    intent.putExtra("USER_AVATAR", userAvatar);
                    startActivity(intent);
                    finish();
                }
            }
        });

        ImageButton btn_account = findViewById(R.id.btn_account);
        btn_account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                initPopWindow(v);
            }
        });

        ImageButton btn_budget = findViewById(R.id.btn_budget);
        btn_budget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                initPopWindowbgt(v);
            }
        });

        ImageButton btn_financerecord = findViewById(R.id.btn_financerecord);
        btn_financerecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                initPopWindowfrd(v);
            }
        });





    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popip, null, false);
        Button btn_addaccount = (Button) view.findViewById(R.id.btn_addaccount);
        Button btn_checkaccount = (Button) view.findViewById(R.id.btn_checkaccount);
        Button btn_deleteaccount = (Button) view.findViewById(R.id.btn_deleteaccount);
        Button btn_editaccount = (Button) view.findViewById(R.id.btn_editaccount);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_addaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddAccount.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "添加账户", Toast.LENGTH_SHORT).show();
            }
        });
        btn_checkaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckAccount.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "查看账户", Toast.LENGTH_SHORT).show();
            }
        });
        btn_deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteAccount.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "删除账户", Toast.LENGTH_SHORT).show();
            }
        });
        btn_editaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditAccount.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "编辑账户", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPopWindowbgt(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popbgt, null, false);
        Button btn_addbudget = (Button) view.findViewById(R.id.btn_addbudget);
        Button btn_checkbudget = (Button) view.findViewById(R.id.btn_checkbudget);
        Button btn_deletebudget = (Button) view.findViewById(R.id.btn_deletebudget);
        Button btn_editbudget = (Button) view.findViewById(R.id.btn_editbudget);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_addbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddBudget.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "添加预算", Toast.LENGTH_SHORT).show();
            }
        });
        btn_checkbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckBudget.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "查看预算", Toast.LENGTH_SHORT).show();
            }
        });
        btn_deletebudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteBudget.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "删除预算", Toast.LENGTH_SHORT).show();
            }
        });
        btn_editbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditBudget.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "编辑预算", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initPopWindowfrd(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popfrd, null, false);
        Button btn_addfrd = (Button) view.findViewById(R.id.btn_addfrd);
        Button btn_checkfrd = (Button) view.findViewById(R.id.btn_checkfrd);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_addfrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFinanceRecord.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "添加交易记录", Toast.LENGTH_SHORT).show();
            }
        });
        btn_checkfrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckFinanceRecord.class);
                intent.putExtra("SESSION_ID", sessionId);
                startActivity(intent);
                Toast.makeText(HomePage.this, "查看交易记录", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
