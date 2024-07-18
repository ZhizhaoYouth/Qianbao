package com.finplat.financialmanage.model.dto.loginlog;

import com.finplat.financialmanage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询登陆日志请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLogQueryRequest extends PageRequest implements Serializable {

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

    /**
     * 搜索内容
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}