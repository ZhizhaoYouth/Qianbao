package com.finplat.financialmanage.model.vo;

import com.finplat.financialmanage.model.entity.LoginLog;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 登陆日志视图
 */
@Data
public class LoginLogVO implements Serializable {

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 封装类转对象
     *
     * @param loginLogVO
     * @return
     */
    public static LoginLog voToObj(LoginLogVO loginLogVO) {
        if (loginLogVO == null) {
            return null;
        }
        LoginLog loginLog = new LoginLog();
        BeanUtils.copyProperties(loginLogVO, loginLog);
        return loginLog;
    }

    /**
     * 对象转封装类
     *
     * @param loginLog
     * @return
     */
    public static LoginLogVO objToVo(LoginLog loginLog) {
        if (loginLog == null) {
            return null;
        }
        LoginLogVO loginLogVO = new LoginLogVO();
        BeanUtils.copyProperties(loginLog, loginLogVO);
        return loginLogVO;
    }
}
