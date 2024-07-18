package com.example.myapplication3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LoginLogAdapter extends RecyclerView.Adapter<LoginLogAdapter.LoginLogViewHolder> {

    private List<MyLoginLog> loginLogList;

    public LoginLogAdapter(List<MyLoginLog> loginLogList) {
        this.loginLogList = loginLogList;
    }

    @NonNull
    @Override
    public LoginLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loginlog, parent, false);
        return new LoginLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoginLogViewHolder holder, int position) {
        MyLoginLog loginLog = loginLogList.get(position);
        holder.id.setText(loginLog.getId());
        holder.userId.setText(loginLog.getUserId());
        holder.loginTime.setText(loginLog.getLoginTime());
        holder.deviceType.setText(loginLog.getDeviceType());
        holder.ipAddress.setText(loginLog.getIpAddress());
        holder.location.setText(loginLog.getLocation());
        holder.browserInfo.setText(loginLog.getBrowserInfo());
        holder.operatingSystem.setText(loginLog.getOperatingSystem());
        holder.createTime.setText(loginLog.getCreateTime());
        holder.updateTime.setText(loginLog.getUpdateTime());
    }

    @Override
    public int getItemCount() {
        return loginLogList.size();
    }

    public static class LoginLogViewHolder extends RecyclerView.ViewHolder {
        TextView id, userId, loginTime, deviceType, ipAddress, location, browserInfo, operatingSystem, createTime, updateTime;

        public LoginLogViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textid);
            userId = itemView.findViewById(R.id.textuserId);
            loginTime = itemView.findViewById(R.id.textloginTime);
            deviceType = itemView.findViewById(R.id.textdeviceType);
            ipAddress = itemView.findViewById(R.id.textipAddress);
            location = itemView.findViewById(R.id.textlocation);
            browserInfo = itemView.findViewById(R.id.textbrowserInfo);
            operatingSystem = itemView.findViewById(R.id.textoperatingSystem);
            createTime = itemView.findViewById(R.id.textcreateTime);
            updateTime = itemView.findViewById(R.id.textupdateTime);
        }
    }
}
