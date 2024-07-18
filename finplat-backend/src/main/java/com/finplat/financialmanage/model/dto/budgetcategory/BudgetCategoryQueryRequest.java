package com.finplat.financialmanage.model.dto.budgetcategory;

import com.finplat.financialmanage.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询预算类别请求

 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BudgetCategoryQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 创建用户 id
     */
    private Long userId;

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