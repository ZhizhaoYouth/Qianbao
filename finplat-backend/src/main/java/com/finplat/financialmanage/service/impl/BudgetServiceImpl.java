package com.finplat.financialmanage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.constant.CommonConstant;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.BudgetMapper;
import com.finplat.financialmanage.model.dto.budget.BudgetQueryRequest;
import com.finplat.financialmanage.model.entity.Budget;
import com.finplat.financialmanage.model.vo.BudgetVO;
import com.finplat.financialmanage.service.BudgetCategoryService;
import com.finplat.financialmanage.service.BudgetService;
import com.finplat.financialmanage.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预算服务实现
 */
@Service
@Slf4j
public class BudgetServiceImpl extends ServiceImpl<BudgetMapper, Budget> implements BudgetService {


    @Resource
    private BudgetCategoryService budgetCategoryService;

    /**
     * 校验数据
     *
     * @param budget
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validBudget(Budget budget, boolean add) {
        ThrowUtils.throwIf(budget == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String budgetDesc = budget.getBudgetDesc();
        Long accountId = budget.getAccountId();
        BigDecimal amount = budget.getAmount();
        String category = budget.getCategory();
        Date startDate = budget.getStartDate();
        Date endDate = budget.getEndDate();

        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(budgetDesc), ErrorCode.PARAMS_ERROR,"描述不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(accountId), ErrorCode.PARAMS_ERROR,"账户不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(amount), ErrorCode.PARAMS_ERROR,"金额不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(startDate), ErrorCode.PARAMS_ERROR,"开始时间不能为空");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(endDate), ErrorCode.PARAMS_ERROR,"结束时间不能为空");
            //开始时间不能晚于结束时间
            ThrowUtils.throwIf(startDate.getTime() > endDate.getTime(), ErrorCode.PARAMS_ERROR,"开始时间不能晚于结束时间");
        }
        // 修改数据时，有参数则校验
        // 补充校验规则
        if (StringUtils.isNotBlank(budgetDesc)) {
            ThrowUtils.throwIf(budgetDesc.length() > 80, ErrorCode.PARAMS_ERROR, "描述长度不能超过80");
            ThrowUtils.throwIf(budgetDesc.length() < 1, ErrorCode.PARAMS_ERROR, "描述长度不能少于1");
        }
        //金额不能小于等于0
        if (amount != null) {
            ThrowUtils.throwIf(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.PARAMS_ERROR, "金额必须大于0");
        }
    }

    /**
     * 获取查询条件
     *
     * @param budgetQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Budget> getQueryWrapper(BudgetQueryRequest budgetQueryRequest) {
        QueryWrapper<Budget> queryWrapper = new QueryWrapper<>();
        if (budgetQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = budgetQueryRequest.getId();
        String budgetDesc = budgetQueryRequest.getBudgetDesc();
        Long accountId = budgetQueryRequest.getAccountId();
        Long userId = budgetQueryRequest.getUserId();
        BigDecimal amount = budgetQueryRequest.getAmount();
        String category = budgetQueryRequest.getCategory();
        Date startDate = budgetQueryRequest.getStartDate();
        Date endDate = budgetQueryRequest.getEndDate();
        Long notId = budgetQueryRequest.getNotId();
        String searchText = budgetQueryRequest.getSearchText();
        String sortField = budgetQueryRequest.getSortField();
        String sortOrder = budgetQueryRequest.getSortOrder();

        // 补充需要的查询条件
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(budgetDesc), "budgetDesc", budgetDesc);
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(accountId), "accountId", accountId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(amount), "amount", amount);
        queryWrapper.eq(StringUtils.isNotBlank(category), "category", category);
        queryWrapper.between(ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate), "startDate", startDate, endDate);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取预算封装
     *
     * @param budget
     * @param request
     * @return
     */
    @Override
    public BudgetVO getBudgetVO(Budget budget, HttpServletRequest request) {
        // 对象转封装类
        BudgetVO budgetVO = BudgetVO.objToVo(budget);
        return budgetVO;
    }

    /**
     * 分页获取预算封装
     *
     * @param budgetPage
     * @param request
     * @return
     */
    @Override
    public Page<BudgetVO> getBudgetVOPage(Page<Budget> budgetPage, HttpServletRequest request) {
        List<Budget> budgetList = budgetPage.getRecords();
        Page<BudgetVO> budgetVOPage = new Page<>(budgetPage.getCurrent(), budgetPage.getSize(), budgetPage.getTotal());
        if (CollUtil.isEmpty(budgetList)) {
            return budgetVOPage;
        }
        // 对象列表 => 封装对象列表
        List<BudgetVO> budgetVOList = budgetList.stream().map(budget -> {
            return BudgetVO.objToVo(budget);
        }).collect(Collectors.toList());
        budgetVOPage.setRecords(budgetVOList);
        return budgetVOPage;
    }

}
