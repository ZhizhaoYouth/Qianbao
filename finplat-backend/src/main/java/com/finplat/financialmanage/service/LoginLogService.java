package com.finplat.financialmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.finplat.financialmanage.model.dto.loginlog.LoginLogQueryRequest;
import com.finplat.financialmanage.model.entity.LoginLog;
import com.finplat.financialmanage.model.vo.LoginLogVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆日志服务
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 校验数据
     *
     * @param loginLog
     * @param add 对创建的数据进行校验
     */
    void validLoginLog(LoginLog loginLog, boolean add);

    /**
     * 获取查询条件
     *
     * @param loginLogQueryRequest
     * @return
     */
    QueryWrapper<LoginLog> getQueryWrapper(LoginLogQueryRequest loginLogQueryRequest);
    
    /**
     * 获取登陆日志封装
     *
     * @param loginLog
     * @param request
     * @return
     */
    LoginLogVO getLoginLogVO(LoginLog loginLog, HttpServletRequest request);

    /**
     * 分页获取登陆日志封装
     *
     * @param loginLogPage
     * @param request
     * @return
     */
    Page<LoginLogVO> getLoginLogVOPage(Page<LoginLog> loginLogPage, HttpServletRequest request);
}
