package com.finplat.financialmanage.model.dto.account;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 更新账户请求
 */
@Data
public class AccountUpdateRequest implements Serializable {

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
     * 审核人 id
     */
    private Long reviewerId;

    /**
     * 审核时间
     */
    private Date reviewTime;

    private static final long serialVersionUID = 1L;
}