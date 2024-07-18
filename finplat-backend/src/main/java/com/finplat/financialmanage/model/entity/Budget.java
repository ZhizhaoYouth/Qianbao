package com.finplat.financialmanage.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预算
 * @TableName budget
 */
@TableName(value ="budget")
@Data
public class Budget implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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