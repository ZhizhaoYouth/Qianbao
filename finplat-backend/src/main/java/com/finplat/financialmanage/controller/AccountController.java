package com.finplat.financialmanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finplat.financialmanage.annotation.AuthCheck;
import com.finplat.financialmanage.common.*;
import com.finplat.financialmanage.constant.UserConstant;
import com.finplat.financialmanage.exception.BusinessException;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.BudgetMapper;
import com.finplat.financialmanage.mapper.FinancialRecordMapper;
import com.finplat.financialmanage.model.dto.account.AccountAddRequest;
import com.finplat.financialmanage.model.dto.account.AccountEditRequest;
import com.finplat.financialmanage.model.dto.account.AccountQueryRequest;
import com.finplat.financialmanage.model.dto.account.AccountUpdateRequest;
import com.finplat.financialmanage.model.dto.card.CardRequest;
import com.finplat.financialmanage.model.entity.*;
import com.finplat.financialmanage.model.enums.AccountTypeEnum;
import com.finplat.financialmanage.model.enums.ReviewStatusEnum;
import com.finplat.financialmanage.model.vo.AccountDetailsVO;
import com.finplat.financialmanage.model.vo.AccountVO;
import com.finplat.financialmanage.model.vo.BudgetVO;
import com.finplat.financialmanage.model.vo.FinancialRecordVO;
import com.finplat.financialmanage.service.AccountService;
import com.finplat.financialmanage.service.BankCardService;
import com.finplat.financialmanage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户接口
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Resource
    private AccountService accountService;

    @Resource
    private UserService userService;

    @Resource
    private BankCardService bankCardService;


    @Resource
    private FinancialRecordMapper financialRecordMapper;

    @Resource
    private BudgetMapper budgetMapper;

    // region 增删改查

    /**
     * 创建账户
     * @param accountAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addAccount(@RequestBody AccountAddRequest accountAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(accountAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        Account account = new Account();
        BeanUtils.copyProperties(accountAddRequest, account);
        // 如果不是电子钱包，是银行卡，则获取账户银行信息
        if (AccountTypeEnum.getEnumByValue(account.getAccountType()) != AccountTypeEnum.WALLET) {
            BankInfo bankInfo = bankCardService.getBankCardInfo(account.getAccountNumber());
            ThrowUtils.throwIf(bankInfo == null, ErrorCode.PARAMS_ERROR, "银行卡信息获取失败");
            account.setBankName(bankInfo.getBankName());
            System.out.println(bankInfo.getCardType());
            AccountTypeEnum accountTypeEnum = AccountTypeEnum.getEnumByBankText(bankInfo.getCardType());
            System.out.println(accountTypeEnum);
            ThrowUtils.throwIf(accountTypeEnum == null, ErrorCode.PARAMS_ERROR, "银行卡类型获取失败");
            account.setAccountType(accountTypeEnum.getValue());
        }else{
            account.setBankName("电子钱包");
        }
        // 数据校验
        accountService.validAccount(account, true);
        //账户唯一性
        AccountQueryRequest accountQueryRequest = new AccountQueryRequest();
        accountQueryRequest.setAccountNumber(account.getAccountNumber());
        QueryWrapper<Account> queryWrapper = accountService.getQueryWrapper(accountQueryRequest);
        ThrowUtils.throwIf(accountService.count(queryWrapper) > 0, ErrorCode.PARAMS_ERROR, "账户已存在");
        // 填充默认值
        User loginUser = userService.getLoginUser(request);
        account.setUserId(loginUser.getId());
        account.setReviewStatus(ReviewStatusEnum.REVIEWING.getValue());
        // 写入数据库
        boolean result = accountService.save(account);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "账户创建失败");
        // 返回新写入的数据 id
        long newAccountId = account.getId();
        return ResultUtils.success(newAccountId);
    }

    /**
     * 删除账户
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteAccount(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Account oldAccount = accountService.getById(id);
        ThrowUtils.throwIf(oldAccount == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldAccount.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = accountService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取账户（封装类）
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<AccountVO> getAccountVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Account account = accountService.getById(id);
        ThrowUtils.throwIf(account == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(accountService.getAccountVO(account, request));
    }

    /**
     * 分页获取账户列表（仅管理员可用）
     * @param accountQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Account>> listAccountByPage(@RequestBody AccountQueryRequest accountQueryRequest) {
        long current = accountQueryRequest.getCurrent();
        long size = accountQueryRequest.getPageSize();
        // 查询数据库
        Page<Account> accountPage = accountService.page(new Page<>(current, size),
                accountService.getQueryWrapper(accountQueryRequest));
        return ResultUtils.success(accountPage);
    }

    /**
     * 分页获取当前登录用户创建的账户列表,主要的用户接口
     * @param accountQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<AccountVO>> listMyAccountVOByPage(@RequestBody AccountQueryRequest accountQueryRequest,
                                                               HttpServletRequest request) {
        ThrowUtils.throwIf(accountQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        accountQueryRequest.setUserId(loginUser.getId());
        long current = accountQueryRequest.getCurrent();
        long size = accountQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Account> accountPage = accountService.page(new Page<>(current, size),
                accountService.getQueryWrapper(accountQueryRequest));
        // 获取封装类
        return ResultUtils.success(accountService.getAccountVOPage(accountPage, request));
    }

    /**
     * 编辑账户（给用户使用）
     * @param accountEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editAccount(@RequestBody AccountEditRequest accountEditRequest, HttpServletRequest request) {
        if (accountEditRequest == null || accountEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        Account account = new Account();
        BeanUtils.copyProperties(accountEditRequest, account);
        // 数据校验
        accountService.validAccount(account, false);
        User loginUser = userService.getLoginUser(request);
        // 判断是否存在
        long id = accountEditRequest.getId();
        Account oldAccount = accountService.getById(id);
        ThrowUtils.throwIf(oldAccount == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldAccount.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 重置审核状态
        account.setReviewStatus(ReviewStatusEnum.REVIEWING.getValue());
        // 操作数据库
        boolean result = accountService.updateById(account);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 获取账户详细信息，包括关联的财务记录和预算
     * @param id      账户id
     * @param request
     * @return
     */
    @PostMapping("/details")
    public BaseResponse<AccountDetailsVO> getAccountDetailsPages(@RequestParam(defaultValue = "1") long recordCurrent,
                                                                 @RequestParam(defaultValue = "10") long recordSize,
                                                                 @RequestParam(defaultValue = "1") long budgetCurrent,
                                                                 @RequestParam(defaultValue = "10") long budgetSize,
                                                                 @RequestParam long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);

        // 查询数据库
        Account account = accountService.getById(id);
        if (account == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "账户不存在");
        }

        AccountDetailsVO accountDetailsVO = new AccountDetailsVO();
        BeanUtils.copyProperties(account, accountDetailsVO);

        // 关联查询财务记录
        Page<FinancialRecord> financialRecordPage = new Page<>(recordCurrent, recordSize);
        QueryWrapper<FinancialRecord> financialRecordQueryWrapper = new QueryWrapper<>();
        financialRecordQueryWrapper.eq("accountId", id);
        financialRecordPage = financialRecordMapper.selectPage(financialRecordPage, financialRecordQueryWrapper);
        Page<FinancialRecordVO> financialRecordVOPage = new Page<>(financialRecordPage.getCurrent(), financialRecordPage.getSize(), financialRecordPage.getTotal());
        List<FinancialRecordVO> financialRecordVOList = financialRecordPage
                .getRecords()
                .stream()
                .map(FinancialRecordVO::objToVo)
                .collect(Collectors.toList());
        financialRecordVOPage.setRecords(financialRecordVOList);
        accountDetailsVO.setFinancialRecordVOPage(financialRecordVOPage);

        // 关联查询预算
        Page<Budget> budgetPage = new Page<>(budgetCurrent, budgetSize);
        QueryWrapper<Budget> budgetQueryWrapper = new QueryWrapper<>();
        budgetQueryWrapper.eq("accountId", id);
        budgetPage = budgetMapper.selectPage(budgetPage, budgetQueryWrapper);
        Page<BudgetVO> budgetVOPage = new Page<>(budgetPage.getCurrent(), budgetPage.getSize(), budgetPage.getTotal());
        List<BudgetVO> budgetVOList = budgetPage.getRecords().stream().map(BudgetVO::objToVo).collect(Collectors.toList());
        budgetVOPage.setRecords(budgetVOList);
        accountDetailsVO.setBudgetVOPage(budgetVOPage);

        // 返回分页结果
        return ResultUtils.success(accountDetailsVO);
    }

    // endregion

    /**
     * 审核账户
     * @param reviewRequest
     * @param request
     * @return
     */
    @PostMapping("/review")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> doAccountReview(@RequestBody ReviewRequest reviewRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(reviewRequest == null, ErrorCode.PARAMS_ERROR);
        Long id = reviewRequest.getId();
        Integer reviewStatus = reviewRequest.getReviewStatus();
        //校验
        ReviewStatusEnum reviewStatusEnum = ReviewStatusEnum.getEnumByValue(reviewStatus);
        if (id == null || reviewStatusEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断是否存在
        Account oldAccount = accountService.getById(id);
        ThrowUtils.throwIf(oldAccount == null, ErrorCode.NOT_FOUND_ERROR);
        //已是该审核状态
        if (oldAccount.getReviewStatus().equals(reviewStatus)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请勿重复审核");
        }
        //更新审核状态
        User loginUser = userService.getLoginUser(request);
        Account account = new Account();
        account.setId(id);
        account.setReviewStatus(reviewStatus);
        account.setReviewerId(loginUser.getId());
        account.setReviewMessage(reviewRequest.getReviewMessage());
        account.setReviewTime(new Date());
        boolean result = accountService.updateById(account);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // 获取当前登录用户的所有账户的总余额
    @GetMapping("/balance")
    public BaseResponse<BigDecimal> getAllMyBalance(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        BigDecimal balance = accountService.getAllMyBalance(userId);
        return ResultUtils.success(balance);
    }

    /**
     * 根据输入的账户号码查询银行信息
     * @param cardRequest
     * @return
     */
    @PostMapping("/check")
    public BaseResponse<BankInfo> checkAccount(@RequestBody CardRequest cardRequest) {
        return ResultUtils.success(bankCardService.getBankCardInfo(cardRequest.getKey()));
    }
}
