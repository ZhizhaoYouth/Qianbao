package com.finplat.financialmanage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.constant.CommonConstant;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.LoginLogMapper;
import com.finplat.financialmanage.model.dto.loginlog.LoginLogQueryRequest;
import com.finplat.financialmanage.model.entity.LoginLog;
import com.finplat.financialmanage.model.vo.LoginLogVO;
import com.finplat.financialmanage.service.LoginLogService;
import com.finplat.financialmanage.service.UserService;
import com.finplat.financialmanage.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登陆日志服务实现
 */
@Service
@Slf4j
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Resource
    private UserService userService;

    /**
     * 校验数据
     * @param loginLog
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validLoginLog(LoginLog loginLog, boolean add) {
        ThrowUtils.throwIf(loginLog == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        Date loginTime = loginLog.getLoginTime();
        String deviceType = loginLog.getDeviceType();
        String ipAddress = loginLog.getIpAddress();
        String location = loginLog.getLocation();
        String browserInfo = loginLog.getBrowserInfo();
        String operatingSystem = loginLog.getOperatingSystem();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(ObjectUtils.isEmpty(loginTime), ErrorCode.PARAMS_ERROR, "登陆时间不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(deviceType), ErrorCode.PARAMS_ERROR, "设备类型不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(ipAddress), ErrorCode.PARAMS_ERROR, "IP地址不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(location), ErrorCode.PARAMS_ERROR, "位置不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(browserInfo), ErrorCode.PARAMS_ERROR, "浏览器信息不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(operatingSystem), ErrorCode.PARAMS_ERROR, "操作系统不能为空");
        }
        // 修改数据时，有参数则校验
        // 补充校验规则
        if (StringUtils.isNotBlank(browserInfo)) {
            ThrowUtils.throwIf(browserInfo.length() > 20, ErrorCode.PARAMS_ERROR, "浏览器信息过长");
        }
    }

    /**
     * 获取查询条件
     *
     * @param loginLogQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<LoginLog> getQueryWrapper(LoginLogQueryRequest loginLogQueryRequest) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        if (loginLogQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = loginLogQueryRequest.getId();
        Long userId = loginLogQueryRequest.getUserId();
        Date loginTime = loginLogQueryRequest.getLoginTime();
        String deviceType = loginLogQueryRequest.getDeviceType();
        String ipAddress = loginLogQueryRequest.getIpAddress();
        String location = loginLogQueryRequest.getLocation();
        String browserInfo = loginLogQueryRequest.getBrowserInfo();
        String operatingSystem = loginLogQueryRequest.getOperatingSystem();
        String searchText = loginLogQueryRequest.getSearchText();
        String sortField=loginLogQueryRequest.getSortField();
        String sortOrder=loginLogQueryRequest.getSortOrder();
        // 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("deviceType", searchText)
                    .or()
                    .like("browserInfo", searchText)
                    .or()
                    .like("operatingSystem", searchText)
                    .or()
                    .like("location", searchText)
            );
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(deviceType), "deviceType", deviceType);
        queryWrapper.like(StringUtils.isNotBlank(browserInfo), "content", browserInfo);
        queryWrapper.like(StringUtils.isNotBlank(operatingSystem), "operatingSystem", operatingSystem);
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(loginTime), "loginTime", loginTime);
        queryWrapper.like(StringUtils.isNotBlank(ipAddress), "ipAddress", ipAddress);
        queryWrapper.like(StringUtils.isNotBlank(location), "location", location);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取登陆日志封装
     *
     * @param loginLog
     * @param request
     * @return
     */
    @Override
    public LoginLogVO getLoginLogVO(LoginLog loginLog, HttpServletRequest request) {
        // 对象转封装类
        LoginLogVO loginLogVO = LoginLogVO.objToVo(loginLog);
        return loginLogVO;
    }

    /**
     * 分页获取登陆日志封装
     *
     * @param loginLogPage
     * @param request
     * @return
     */
    @Override
    public Page<LoginLogVO> getLoginLogVOPage(Page<LoginLog> loginLogPage, HttpServletRequest request) {
        List<LoginLog> loginLogList = loginLogPage.getRecords();
        Page<LoginLogVO> loginLogVOPage = new Page<>(loginLogPage.getCurrent(), loginLogPage.getSize(), loginLogPage.getTotal());
        if (CollUtil.isEmpty(loginLogList)) {
            return loginLogVOPage;
        }
        // 对象列表 => 封装对象列表
        List<LoginLogVO> loginLogVOList = loginLogList.stream().map(loginLog -> {
            return LoginLogVO.objToVo(loginLog);
        }).collect(Collectors.toList());
        loginLogVOPage.setRecords(loginLogVOList);
        return loginLogVOPage;
    }

}
