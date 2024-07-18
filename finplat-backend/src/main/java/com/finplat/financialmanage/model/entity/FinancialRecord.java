package com.finplat.financialmanage.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务记录
 * @TableName financial_record
 */
@TableName(value ="financial_record")
@Data
public class FinancialRecord implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}