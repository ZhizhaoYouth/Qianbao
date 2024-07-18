package com.finplat.financialmanage.model.dto.loginlog;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 编辑登陆日志请求Edit给用户使用，但日志不可编辑，事实上这个方法不应该对用户开放
 */
@Data
public class LoginLogEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户 id
     */
    private Long userId;

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