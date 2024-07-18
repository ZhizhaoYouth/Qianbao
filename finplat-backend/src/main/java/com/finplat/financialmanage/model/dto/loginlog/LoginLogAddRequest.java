package com.finplat.financialmanage.model.dto.loginlog;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建登陆日志请求
 */
@Data
public class LoginLogAddRequest implements Serializable {

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 设备类型：PC、手机、平板等
     */
    private String deviceType;

    /**
     * 登录 IP 地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器信息
     */
    private String browserInfo;

    /**
     * 操作系统
     */
    private String operatingSystem;

    private static final long serialVersionUID = 1L;
}