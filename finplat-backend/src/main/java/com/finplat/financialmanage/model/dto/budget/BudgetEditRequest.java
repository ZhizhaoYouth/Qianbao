package com.finplat.financialmanage.model.dto.budget;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 编辑预算请求
 */
@Data
public class BudgetEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 预算描述
     */
    private String budgetDesc;

    /**
     * 账户 id
     */
    private Long accountId;

    /**
     * 预算金额
     */
    private BigDecimal amount;

    /**
     * 预算类别（如餐饮、娱乐）
     */
    private String category;

    /**
     * 预算开始时间
     */
    private Date startDate;

    /**
     * 预算结束时间
     */
    private Date endDate;

    private static final long serialVersionUID = 1L;
}