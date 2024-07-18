package com.finplat.financialmanage.model.vo;

import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.BankInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户视图
 */
@Data
public class AccountVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账户号码
     */
    private String accountNumber;

    /**
     * 账户类型（0-信用卡，1-储蓄卡，2-电子钱包）
     */
    private Integer accountType;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 审核状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    private String reviewMessage;

    /**
     * 审核时间
     */
    private Date reviewTime;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 银行信息
     */
    private BankInfo bankInfo;

    /**
     * 封装类转对象
     *
     * @param accountVO
     * @return
     */
    public static Account voToObj(AccountVO accountVO) {
        if (accountVO == null) {
            return null;
        }
        Account account = new Account();
        BeanUtils.copyProperties(accountVO, account);
        return account;
    }

    /**
     * 对象转封装类
     *
     * @param account
     * @return
     */
    public static AccountVO objToVo(Account account) {
        if (account == null) {
            return null;
        }
        AccountVO accountVO = new AccountVO();
        BeanUtils.copyProperties(account, accountVO);
        return accountVO;
    }
}
