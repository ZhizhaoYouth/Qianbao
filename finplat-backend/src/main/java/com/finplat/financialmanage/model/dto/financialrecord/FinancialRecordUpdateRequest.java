package com.finplat.financialmanage.model.dto.financialrecord;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 更新财务记录请求

 */
@Data
public class FinancialRecordUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 交易描述
     */
    private String transactionDesc;

    /**
     * 账户 id
     */
    private Long accountId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易类型（0-收入，1-支出）
     */
    private Integer transactionType;

    /**
     * 交易类别（如餐饮、娱乐）
     */
    private String category;

    /**
     * 交易时间
     */
    private Date transactionTime;

    private static final long serialVersionUID = 1L;
}