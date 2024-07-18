package com.finplat.financialmanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finplat.financialmanage.annotation.AuthCheck;
import com.finplat.financialmanage.common.BaseResponse;
import com.finplat.financialmanage.common.DeleteRequest;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.common.ResultUtils;
import com.finplat.financialmanage.constant.UserConstant;
import com.finplat.financialmanage.exception.BusinessException;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.FinancialRecordMapper;
import com.finplat.financialmanage.model.dto.financialrecord.FinancialRecordAddRequest;
import com.finplat.financialmanage.model.dto.financialrecord.FinancialRecordEditRequest;
import com.finplat.financialmanage.model.dto.financialrecord.FinancialRecordQueryRequest;
import com.finplat.financialmanage.model.dto.financialrecord.FinancialRecordUpdateRequest;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.FinancialRecord;
import com.finplat.financialmanage.model.entity.User;
import com.finplat.financialmanage.model.vo.FinancialRecordVO;
import com.finplat.financialmanage.service.AccountService;
import com.finplat.financialmanage.service.FinancialRecordService;
import com.finplat.financialmanage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务记录接口
 */
@RestController
@RequestMapping("/financialRecord")
@Slf4j
public class FinancialRecordController {

    @Resource
    private FinancialRecordService financialRecordService;

    @Resource
    private UserService userService;

    @Resource
    private AccountService accountService;

    @Resource
    private FinancialRecordMapper financialRecordMapper;

    // region 增删改查
    // 实现增删改同时更新用户余额
    /**
     * 创建财务记录
     * @param financialRecordAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addFinancialRecord(@RequestBody FinancialRecordAddRequest financialRecordAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(financialRecordAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        FinancialRecord financialRecord = new FinancialRecord();
        BeanUtils.copyProperties(financialRecordAddRequest, financialRecord);
        // 数据校验
        Long AccountId=financialRecordAddRequest.getAccountId();
        Long UserId=userService.getLoginUser(request).getId();
        Account account = accountService.getById(AccountId);
        // 添加记录的账户必须存在
        if (account == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户不存在");
        }
        //账户必须已经过审才能开始添加记录
        if(!accountService.isPassed(account)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户未过审");
        }
        //如果当前登录用户不是管理员，则账户必须属于当前登录用户,并且过审
        if(!userService.isAdmin(request)){
            if(!account.getUserId().equals(UserId)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户不属于当前用户");
            }
        }
        // 填充默认值
        financialRecord.setUserId(UserId);
        financialRecordService.validFinancialRecord(financialRecord, true);
        // 写入数据库
        boolean result = financialRecordService.save(financialRecord);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 修改账户余额
        financialRecordService.changeBalance(financialRecordAddRequest.getTransactionType(),financialRecordAddRequest.getAmount(),account);
        // 返回新写入的数据 id
        long newFinancialRecordId = financialRecord.getId();
        return ResultUtils.success(newFinancialRecordId);
    }

    /**
     * 删除财务记录
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteFinancialRecord(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        FinancialRecord oldFinancialRecord = financialRecordService.getById(id);
        ThrowUtils.throwIf(oldFinancialRecord == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldFinancialRecord.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = financialRecordService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 修改账户余额
        Integer transactionType=oldFinancialRecord.getTransactionType();
        financialRecordService.changeBalance(transactionType,oldFinancialRecord.getAmount().negate(),accountService.getById(oldFinancialRecord.getAccountId()));
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取财务记录（封装类）
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<FinancialRecordVO> getFinancialRecordVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        FinancialRecord financialRecord = financialRecordService.getById(id);
        ThrowUtils.throwIf(financialRecord == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(financialRecordService.getFinancialRecordVO(financialRecord, request));
    }

    /**
     * 分页获取财务记录列表（仅管理员可用）
     * @param financialRecordQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<FinancialRecord>> listFinancialRecordByPage(@RequestBody FinancialRecordQueryRequest financialRecordQueryRequest) {
        long current = financialRecordQueryRequest.getCurrent();
        long size = financialRecordQueryRequest.getPageSize();
        // 查询数据库
        Page<FinancialRecord> financialRecordPage = financialRecordService.page(new Page<>(current, size),
                financialRecordService.getQueryWrapper(financialRecordQueryRequest));
        return ResultUtils.success(financialRecordPage);
    }

    /**
     * 分页获取当前登录用户创建的财务记录列表
     * @param financialRecordQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<FinancialRecordVO>> listMyFinancialRecordVOByPage(@RequestBody FinancialRecordQueryRequest financialRecordQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(financialRecordQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        financialRecordQueryRequest.setUserId(loginUser.getId());
        long current = financialRecordQueryRequest.getCurrent();
        long size = financialRecordQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<FinancialRecord> financialRecordPage = financialRecordService.page(new Page<>(current, size),
                financialRecordService.getQueryWrapper(financialRecordQueryRequest));
        // 获取封装类
        return ResultUtils.success(financialRecordService.getFinancialRecordVOPage(financialRecordPage, request));
    }

    /**
     * 编辑财务记录（给用户使用）
     * @param financialRecordEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editFinancialRecord(@RequestBody FinancialRecordEditRequest financialRecordEditRequest, HttpServletRequest request) {
        if (financialRecordEditRequest == null || financialRecordEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        FinancialRecord financialRecord = new FinancialRecord();
        BeanUtils.copyProperties(financialRecordEditRequest, financialRecord);
        // 数据校验
        financialRecordService.validFinancialRecord(financialRecord, false);
        User loginUser = userService.getLoginUser(request);
        // 判断是否存在
        long id = financialRecordEditRequest.getId();
        FinancialRecord oldFinancialRecord = financialRecordService.getById(id);
        ThrowUtils.throwIf(oldFinancialRecord == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldFinancialRecord.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 修改账户余额前，获取旧的账户信息
        Account oldAccount = accountService.getById(oldFinancialRecord.getAccountId());
        ThrowUtils.throwIf(oldAccount == null, ErrorCode.NOT_FOUND_ERROR);

        // 计算差异并更新余额
        BigDecimal oldAmount = oldFinancialRecord.getAmount();
        BigDecimal newAmount = financialRecord.getAmount();
        Integer oldTransactionType = oldFinancialRecord.getTransactionType();
        Integer newTransactionType = financialRecord.getTransactionType();

        // 处理旧记录
        if (oldTransactionType != null && oldAmount != null) {
            financialRecordService.changeBalance(oldTransactionType, oldAmount.negate(), oldAccount);
        }
        // 处理新记录
        if (newTransactionType != null && newAmount != null) {
            financialRecordService.changeBalance(newTransactionType, newAmount, oldAccount);
        }
        // 操作数据库
        boolean result = financialRecordService.updateById(financialRecord);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
