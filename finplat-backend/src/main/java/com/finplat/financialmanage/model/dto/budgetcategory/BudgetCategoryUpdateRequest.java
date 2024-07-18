package com.finplat.financialmanage.model.dto.budgetcategory;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新预算类别请求

 */
@Data
public class BudgetCategoryUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 预算类别名称
     */
    private String categoryName;

    /**
     * 是否为系统默认类别,1表示是默认类别
     */
    private Integer isDefault;

    private static final long serialVersionUID = 1L;
}