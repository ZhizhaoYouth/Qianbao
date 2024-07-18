package com.finplat.financialmanage.model.dto.budget;

import com.finplat.financialmanage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询预算请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BudgetQueryRequest extends PageRequest implements Serializable {

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
     * 创建用户 id
     */
    private Long userId;

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

    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;


    private static final long serialVersionUID = 1L;
}