package com.finplat.financialmanage.model.vo;

import com.finplat.financialmanage.model.entity.Budget;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预算视图

 */
@Data
public class BudgetVO implements Serializable {

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
     * @param budgetVO
     * @return
     */
    public static Budget voToObj(BudgetVO budgetVO) {
        if (budgetVO == null) {
            return null;
        }
        Budget budget = new Budget();
        BeanUtils.copyProperties(budgetVO, budget);
        return budget;
    }

    /**
     * 对象转封装类
     *
     * @param budget
     * @return
     */
    public static BudgetVO objToVo(Budget budget) {
        if (budget == null) {
            return null;
        }
        BudgetVO budgetVO = new BudgetVO();
        BeanUtils.copyProperties(budget, budgetVO);
        return budgetVO;
    }
}
