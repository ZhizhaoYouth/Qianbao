package com.finplat.financialmanage.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finplat.financialmanage.model.entity.BankInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户详情视图
 */
@Data
public class AccountDetailsVO {
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
     * 财务记录列表
     */
    private Page<FinancialRecordVO> financialRecordVOPage;

    /**
     * 预算列表
     */
    private Page<BudgetVO> budgetVOPage;
}
