package com.finplat.financialmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.finplat.financialmanage.model.dto.budget.BudgetQueryRequest;
import com.finplat.financialmanage.model.entity.Budget;
import com.finplat.financialmanage.model.vo.BudgetVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 预算服务

 */
public interface BudgetService extends IService<Budget> {

    /**
     * 校验数据
     *
     * @param budget
     * @param add 对创建的数据进行校验
     */
    void validBudget(Budget budget, boolean add);

    /**
     * 获取查询条件
     *
     * @param budgetQueryRequest
     * @return
     */
    QueryWrapper<Budget> getQueryWrapper(BudgetQueryRequest budgetQueryRequest);
    
    /**
     * 获取预算封装
     *
     * @param budget
     * @param request
     * @return
     */
    BudgetVO getBudgetVO(Budget budget, HttpServletRequest request);

    /**
     * 分页获取预算封装
     *
     * @param budgetPage
     * @param request
     * @return
     */
    Page<BudgetVO> getBudgetVOPage(Page<Budget> budgetPage, HttpServletRequest request);
}
