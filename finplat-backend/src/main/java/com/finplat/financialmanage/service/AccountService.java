package com.finplat.financialmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.finplat.financialmanage.model.dto.account.AccountQueryRequest;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.vo.AccountVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 账户服务
 */
public interface AccountService extends IService<Account> {

    /**
     * 校验数据
     *
     * @param account
     * @param add 对创建的数据进行校验
     */
    void validAccount(Account account, boolean add);

    /**
     * 是否已经过审
     * @param account
     */
    boolean isPassed(Account account);

    /**
     * 获取查询条件
     *
     * @param accountQueryRequest
     * @return
     */
    QueryWrapper<Account> getQueryWrapper(AccountQueryRequest accountQueryRequest);
    
    /**
     * 获取账户封装
     *
     * @param account
     * @param request
     * @return
     */
    AccountVO getAccountVO(Account account, HttpServletRequest request);

    /**
     * 分页获取账户封装
     *
     * @param accountPage
     * @param request
     * @return
     */
    Page<AccountVO> getAccountVOPage(Page<Account> accountPage, HttpServletRequest request);

    /**
     * 获取我的余额
     * @param userId
     * @return
     */
    BigDecimal getAllMyBalance(Long userId);
}
