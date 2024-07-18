package com.finplat.financialmanage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.constant.CommonConstant;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.BudgetCategoryMapper;
import com.finplat.financialmanage.model.dto.budgetcategory.BudgetCategoryQueryRequest;
import com.finplat.financialmanage.model.entity.BudgetCategory;
import com.finplat.financialmanage.model.vo.BudgetCategoryVO;
import com.finplat.financialmanage.service.BudgetCategoryService;
import com.finplat.financialmanage.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预算类别服务实现
 */
@Service
@Slf4j
public class BudgetCategoryServiceImpl extends ServiceImpl<BudgetCategoryMapper, BudgetCategory> implements BudgetCategoryService {


    /**
     * 校验数据
     *
     * @param budgetCategory
     * @param add            对创建的数据进行校验
     */
    @Override
    public void validBudgetCategory(BudgetCategory budgetCategory, boolean add) {
        ThrowUtils.throwIf(budgetCategory == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String categoryName = budgetCategory.getCategoryName();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(categoryName), ErrorCode.PARAMS_ERROR, "类别名称不能为空");
        }
        // 修改数据时，有参数则校验
        // 补充校验规则
        if (StringUtils.isNotBlank(categoryName)) {
            ThrowUtils.throwIf(categoryName.length() > 20, ErrorCode.PARAMS_ERROR, "类别名称不能超过20个字符");
        }
    }

    /**
     * 获取查询条件
     * @param budgetCategoryQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<BudgetCategory> getQueryWrapper(BudgetCategoryQueryRequest budgetCategoryQueryRequest) {
        QueryWrapper<BudgetCategory> queryWrapper = new QueryWrapper<>();
        if (budgetCategoryQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = budgetCategoryQueryRequest.getId();
        Long userId = budgetCategoryQueryRequest.getUserId();
        String categoryName = budgetCategoryQueryRequest.getCategoryName();
        Integer isDefault = budgetCategoryQueryRequest.getIsDefault();
        String sortField = budgetCategoryQueryRequest.getSortField();
        String sortOrder = budgetCategoryQueryRequest.getSortOrder();
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(categoryName), "categoryName", categoryName);
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        // 修改部分：用户ID和系统默认类别的组合查询
        // 如果有用户信息，说明是从“我的类别”里访问的，返回该用户自定义类别加上系统默认类别
        if (ObjectUtils.isNotEmpty(userId)) {
            queryWrapper.and(wrapper ->
                    wrapper.eq("userId", userId)
                            .or()
                            .eq("isDefault", isDefault));
        } else {
            //如果没有用户信息，说明是管理员在做查询，正常查询
            queryWrapper.eq(ObjectUtils.isNotEmpty(isDefault),"isDefault", isDefault);
            queryWrapper.eq(ObjectUtils.isNotEmpty(userId),"userId", userId);
        }
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取预算类别封装
     *
     * @param budgetCategory
     * @param request
     * @return
     */
    @Override
    public BudgetCategoryVO getBudgetCategoryVO(BudgetCategory budgetCategory, HttpServletRequest request) {
        // 对象转封装类
        BudgetCategoryVO budgetCategoryVO = BudgetCategoryVO.objToVo(budgetCategory);
        return budgetCategoryVO;
    }

    /**
     * 分页获取预算类别封装
     *
     * @param budgetCategoryPage
     * @param request
     * @return
     */
    @Override
    public Page<BudgetCategoryVO> getBudgetCategoryVOPage(Page<BudgetCategory> budgetCategoryPage, HttpServletRequest request) {
        List<BudgetCategory> budgetCategoryList = budgetCategoryPage.getRecords();
        Page<BudgetCategoryVO> budgetCategoryVOPage = new Page<>(budgetCategoryPage.getCurrent(), budgetCategoryPage.getSize(), budgetCategoryPage.getTotal());
        if (CollUtil.isEmpty(budgetCategoryList)) {
            return budgetCategoryVOPage;
        }
        // 对象列表 => 封装对象列表
        List<BudgetCategoryVO> budgetCategoryVOList = budgetCategoryList.stream().map(budgetCategory -> {
            return BudgetCategoryVO.objToVo(budgetCategory);
        }).collect(Collectors.toList());
        budgetCategoryVOPage.setRecords(budgetCategoryVOList);
        return budgetCategoryVOPage;
    }

    /**
     * 检查是否是用户可用的类别
     *
     * @param category
     * @param userId
     * @return
     */
    @Override
    public boolean isAvailableCategory(String category, Long userId) {
        System.out.println("category = " + category + ", userId = " + userId);

        if (StringUtils.isBlank(category) || userId == null) {
            return false;
        }



        // 查询用户自定义的类别和系统默认的类别
        QueryWrapper<BudgetCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("categoryName", category)
                .and(wrapper -> wrapper.eq("userId", userId)
                        .or().eq("isDefault", 1));

        System.out.println(queryWrapper);
        BudgetCategory budgetCategory = this.getOne(queryWrapper);
        return budgetCategory != null;
    }
}
