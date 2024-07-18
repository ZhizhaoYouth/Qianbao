package com.finplat.financialmanage.model.dto.financialrecord;

import com.finplat.financialmanage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询财务记录请求

 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FinancialRecordQueryRequest extends PageRequest implements Serializable {

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
     * 创建用户 id
     */
    private Long userId;

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