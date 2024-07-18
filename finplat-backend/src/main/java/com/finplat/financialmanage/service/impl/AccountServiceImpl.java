package com.finplat.financialmanage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.constant.CommonConstant;
import com.finplat.financialmanage.exception.BusinessException;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.AccountMapper;
import com.finplat.financialmanage.model.dto.account.AccountQueryRequest;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.BankInfo;
import com.finplat.financialmanage.model.enums.AccountTypeEnum;
import com.finplat.financialmanage.model.enums.ReviewStatusEnum;
import com.finplat.financialmanage.model.vo.AccountVO;
import com.finplat.financialmanage.service.AccountService;
import com.finplat.financialmanage.service.BankCardService;
import com.finplat.financialmanage.service.UserService;
import com.finplat.financialmanage.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 账户服务实现
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    private UserService userService;

    @Resource
    private BankCardService bankCardService;

    /**
     * 校验数据
     *
     * @param account
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validAccount(Account account, boolean add) {
        ThrowUtils.throwIf(account == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String accountName = account.getAccountName();
        String accountNumber = account.getAccountNumber();
        Integer accountType = account.getAccountType();
        BigDecimal balance = account.getBalance();
        String bankName = account.getBankName();
        Integer reviewStatus = account.getReviewStatus();

        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(accountName), ErrorCode.PARAMS_ERROR,"账户名称不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(accountNumber), ErrorCode.PARAMS_ERROR,"账户号码不能为空");
            AccountTypeEnum accountTypeEnum = AccountTypeEnum.getEnumByValue(accountType);
            ThrowUtils.throwIf(accountTypeEnum == null, ErrorCode.PARAMS_ERROR,"账户类型非法");
            if(accountTypeEnum != AccountTypeEnum.WALLET){
                ThrowUtils.throwIf(StringUtils.isBlank(bankName), ErrorCode.PARAMS_ERROR,"银行名称不能为空");
            }
            ThrowUtils.throwIf(balance == null, ErrorCode.PARAMS_ERROR,"账户余额不能为空");
        }
        // 修改数据时，有参数则校验
        // 补充校验规则
        if (StringUtils.isNotBlank(accountName)) {
            ThrowUtils.throwIf(accountName.length() > 20, ErrorCode.PARAMS_ERROR, "账户名称不能超过20个字符");
            ThrowUtils.throwIf(accountName.length() < 2, ErrorCode.PARAMS_ERROR, "账户名称不能少于2个字符");
        }
        if (reviewStatus != null) {
            ReviewStatusEnum reviewStatusEnum = ReviewStatusEnum.getEnumByValue(reviewStatus);
            ThrowUtils.throwIf(reviewStatusEnum == null, ErrorCode.PARAMS_ERROR, "审核状态非法");
        }

    }

    /**
     * 是否已经过审
     * @param account
     * @return
     */
    @Override
    public boolean isPassed(Account account) {
        Integer reviewStatus = account.getReviewStatus();
        if(reviewStatus == 1){
            return true;
        }
        return false;
    }

    /**
     * 获取查询条件
     *
     * @param accountQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Account> getQueryWrapper(AccountQueryRequest accountQueryRequest) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        if (accountQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = accountQueryRequest.getId();
        String accountName = accountQueryRequest.getAccountName();
        String accountNumber = accountQueryRequest.getAccountNumber();
        Integer accountType = accountQueryRequest.getAccountType();
        BigDecimal balance = accountQueryRequest.getBalance();
        String bankName = accountQueryRequest.getBankName();
        Integer reviewStatus = accountQueryRequest.getReviewStatus();
        String reviewMessage = accountQueryRequest.getReviewMessage();
        Long reviewerId = accountQueryRequest.getReviewerId();
        Long userId = accountQueryRequest.getUserId();
        Long notId = accountQueryRequest.getNotId();
        String searchText = accountQueryRequest.getSearchText();
        String sortField = accountQueryRequest.getSortField();
        String sortOrder = accountQueryRequest.getSortOrder();

        // 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("accountName", searchText).or().like("bankName", searchText));
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(accountName), "accountName", accountName);
        queryWrapper.like(StringUtils.isNotBlank(bankName), "bankName", bankName);
        queryWrapper.like(StringUtils.isNotBlank(reviewMessage), "reviewMessage", reviewMessage);
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(accountType), "accountType", accountType);
        queryWrapper.eq(ObjectUtils.isNotEmpty(reviewStatus), "reviewStatus", reviewStatus);
        queryWrapper.eq(ObjectUtils.isNotEmpty(reviewerId), "reviewerId", reviewerId);
        queryWrapper.eq(StringUtils.isNotBlank(accountNumber), "accountNumber", accountNumber);
        queryWrapper.eq(ObjectUtils.isNotEmpty(balance), "balance", balance);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取账户封装
     *
     * @param account
     * @param request
     * @return
     */
    @Override
    public AccountVO getAccountVO(Account account, HttpServletRequest request) {
        // 对象转封装类
        AccountVO accountVO = AccountVO.objToVo(account);
        // 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 关联查询银行信息
        String accountNumber = account.getAccountNumber();
        Integer accountType = account.getAccountType();
        AccountTypeEnum accountTypeEnum = AccountTypeEnum.getEnumByValue(accountType);
        BankInfo bankInfo = null;
        if(accountNumber != null && accountTypeEnum != AccountTypeEnum.WALLET) {
            bankInfo = bankCardService.getBankCardInfo(accountNumber);
        }
        if (bankInfo != null) {
            accountVO.setBankInfo(bankInfo);
        }
        // endregion

        return accountVO;
    }

    /**
     * 分页获取账户封装
     *
     * @param accountPage
     * @param request
     * @return
     */
    @Override
    public Page<AccountVO> getAccountVOPage(Page<Account> accountPage, HttpServletRequest request) {
        List<Account> accountList = accountPage.getRecords();
        Page<AccountVO> accountVOPage = new Page<>(accountPage.getCurrent(), accountPage.getSize(), accountPage.getTotal());
        if (CollUtil.isEmpty(accountList)) {
            return accountVOPage;
        }
        // 对象列表 => 封装对象列表
        List<AccountVO> accountVOList = accountList.stream().map(account -> {
            return AccountVO.objToVo(account);
        }).collect(Collectors.toList());

        // 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 关联查询银行信息
        // 1. 获取所有需要查询银行信息的账户号码
        Set<String> accountNumberSet = accountList.stream()
                .filter(account -> account.getAccountType() != 2)
                .map(Account::getAccountNumber)
                .collect(Collectors.toSet());

        // 2. 关联查询银行信息
        Map<String, BankInfo> accountNumberMap = accountNumberSet.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        accountNumber -> bankCardService.getBankCardInfo(accountNumber)
                ));

        // 3. 填充信息
        accountVOList.forEach(accountVO -> {
            String accountNumber = accountVO.getAccountNumber();
            if (accountVO.getAccountType() != 2) {
                BankInfo bankInfo = accountNumberMap.get(accountNumber);
                accountVO.setBankInfo(bankInfo);
            }
        });
        // endregion

        accountVOPage.setRecords(accountVOList);
        return accountVOPage;
    }

    /**
     * 获取用户所有账户余额
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getAllMyBalance(Long userId) {
        if(userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户id为空");
        }
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        List<Account> accountList = this.list(queryWrapper);
        return accountList.stream().filter(this::isPassed).map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
