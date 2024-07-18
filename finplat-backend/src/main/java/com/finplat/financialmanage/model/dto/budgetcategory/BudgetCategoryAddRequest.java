package com.finplat.financialmanage.model.dto.budgetcategory;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建预算类别请求

 */
@Data
public class BudgetCategoryAddRequest implements Serializable {

    /**
     * 预算类别名称
     */
    private String categoryName;

    /**
     * 是否为系统默认类别,1表示是默认类别,用户添加的默认为0,管理员添加的默认为1
     */
    private Integer isDefault;

    private static final long serialVersionUID = 1L;
}