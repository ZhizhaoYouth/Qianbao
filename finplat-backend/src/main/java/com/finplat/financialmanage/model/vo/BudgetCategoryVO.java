package com.finplat.financialmanage.model.vo;

import com.finplat.financialmanage.model.entity.BudgetCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 预算类别视图
 */
@Data
public class BudgetCategoryVO implements Serializable {

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 封装类转对象
     *
     * @param budgetCategoryVO
     * @return
     */
    public static BudgetCategory voToObj(BudgetCategoryVO budgetCategoryVO) {
        if (budgetCategoryVO == null) {
            return null;
        }
        BudgetCategory budgetCategory = new BudgetCategory();
        BeanUtils.copyProperties(budgetCategoryVO, budgetCategory);
        return budgetCategory;
    }

    /**
     * 对象转封装类
     *
     * @param budgetCategory
     * @return
     */
    public static BudgetCategoryVO objToVo(BudgetCategory budgetCategory) {
        if (budgetCategory == null) {
            return null;
        }
        BudgetCategoryVO budgetCategoryVO = new BudgetCategoryVO();
        BeanUtils.copyProperties(budgetCategory, budgetCategoryVO);
        return budgetCategoryVO;
    }
}
