package com.example.myapplication3;


public class MyLoginLog {
    private String id;
    private String userId;
    private String loginTime;
    private String deviceType;
    private String ipAddress;
    private String location;
    private String browserInfo;
    private String operatingSystem;
    private String createTime;
    private String updateTime;

    public MyLoginLog(String id, String userId, String loginTime, String deviceType, String ipAddress, String location, String browserInfo, String operatingSystem, String createTime, String updateTime) {
        this.id = id;
        this.userId = userId;
        this.loginTime = loginTime;
        this.deviceType = deviceType;
        this.ipAddress = ipAddress;
        this.location = location;
        this.browserInfo = browserInfo;
        this.operatingSystem = operatingSystem;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getters and setters for each field
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
