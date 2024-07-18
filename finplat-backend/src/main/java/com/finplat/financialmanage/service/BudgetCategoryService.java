package com.finplat.financialmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.finplat.financialmanage.model.dto.budgetcategory.BudgetCategoryQueryRequest;
import com.finplat.financialmanage.model.entity.BudgetCategory;
import com.finplat.financialmanage.model.vo.BudgetCategoryVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 预算类别服务
 */
public interface BudgetCategoryService extends IService<BudgetCategory> {

    /**
     * 校验数据
     *
     * @param budgetCategory
     * @param add 对创建的数据进行校验
     */
    void validBudgetCategory(BudgetCategory budgetCategory, boolean add);

    /**
     * 获取查询条件
     *
     * @param budgetCategoryQueryRequest
     * @return
     */
    QueryWrapper<BudgetCategory> getQueryWrapper(BudgetCategoryQueryRequest budgetCategoryQueryRequest);
    
    /**
     * 获取预算类别封装
     *
     * @param budgetCategory
     * @param request
     * @return
     */
    BudgetCategoryVO getBudgetCategoryVO(BudgetCategory budgetCategory, HttpServletRequest request);

    /**
     * 分页获取预算类别封装
     *
     * @param budgetCategoryPage
     * @param request
     * @return
     */
    Page<BudgetCategoryVO> getBudgetCategoryVOPage(Page<BudgetCategory> budgetCategoryPage, HttpServletRequest request);

    /**
     * 检查类别是否可用
     * @param category
     * @param userId
     * @return
     */
    boolean isAvailableCategory(String category, Long userId);

}
