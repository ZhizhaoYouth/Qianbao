package com.finplat.financialmanage.model.dto.budgetcategory;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑预算类别请求

 */
@Data
public class BudgetCategoryEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 预算类别名称
     */
    private String categoryName;

    private static final long serialVersionUID = 1L;
}