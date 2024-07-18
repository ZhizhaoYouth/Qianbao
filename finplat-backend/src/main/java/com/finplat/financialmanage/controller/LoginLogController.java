package com.finplat.financialmanage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finplat.financialmanage.annotation.AuthCheck;
import com.finplat.financialmanage.common.BaseResponse;
import com.finplat.financialmanage.common.DeleteRequest;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.common.ResultUtils;
import com.finplat.financialmanage.constant.UserConstant;
import com.finplat.financialmanage.exception.BusinessException;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.model.dto.loginlog.LoginLogAddRequest;
import com.finplat.financialmanage.model.dto.loginlog.LoginLogEditRequest;
import com.finplat.financialmanage.model.dto.loginlog.LoginLogQueryRequest;
import com.finplat.financialmanage.model.dto.loginlog.LoginLogUpdateRequest;
import com.finplat.financialmanage.model.entity.LoginLog;
import com.finplat.financialmanage.model.entity.User;
import com.finplat.financialmanage.model.vo.LoginLogVO;
import com.finplat.financialmanage.service.LoginLogService;
import com.finplat.financialmanage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登陆日志接口
 */
@RestController
@RequestMapping("/loginLog")
@Slf4j
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建登陆日志
     * @param loginLogAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addLoginLog(@RequestBody LoginLogAddRequest loginLogAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(loginLogAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        LoginLog loginLog = new LoginLog();
        BeanUtils.copyProperties(loginLogAddRequest, loginLog);
        // 数据校验
        loginLogService.validLoginLog(loginLog, true);
        // 填充默认值
        User loginUser = userService.getLoginUser(request);
        loginLog.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = loginLogService.save(loginLog);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newLoginLogId = loginLog.getId();
        return ResultUtils.success(newLoginLogId);
    }

    /**
     * 删除登陆日志（因为是日志，所以用户不应该能够编辑）
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteLoginLog(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        LoginLog oldLoginLog = loginLogService.getById(id);
        ThrowUtils.throwIf(oldLoginLog == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldLoginLog.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = loginLogService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取登陆日志（封装类）
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<LoginLogVO> getLoginLogVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        LoginLog loginLog = loginLogService.getById(id);
        ThrowUtils.throwIf(loginLog == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(loginLogService.getLoginLogVO(loginLog, request));
    }

    /**
     * 分页获取登陆日志列表（仅管理员可用）
     * @param loginLogQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<LoginLog>> listLoginLogByPage(@RequestBody LoginLogQueryRequest loginLogQueryRequest) {
        long current = loginLogQueryRequest.getCurrent();
        long size = loginLogQueryRequest.getPageSize();
        // 查询数据库
        Page<LoginLog> loginLogPage = loginLogService.page(new Page<>(current, size),
                loginLogService.getQueryWrapper(loginLogQueryRequest));
        return ResultUtils.success(loginLogPage);
    }

    /**
     * 分页获取当前登录用户创建的登陆日志列表
     * @param loginLogQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<LoginLogVO>> listMyLoginLogVOByPage(@RequestBody LoginLogQueryRequest loginLogQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(loginLogQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        loginLogQueryRequest.setUserId(loginUser.getId());
        long current = loginLogQueryRequest.getCurrent();
        long size = loginLogQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<LoginLog> loginLogPage = loginLogService.page(new Page<>(current, size),
                loginLogService.getQueryWrapper(loginLogQueryRequest));
        // 获取封装类
        return ResultUtils.success(loginLogService.getLoginLogVOPage(loginLogPage, request));
    }

    // endregion
}
