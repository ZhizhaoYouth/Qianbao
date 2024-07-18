package com.finplat.financialmanage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.finplat.financialmanage.annotation.AuthCheck;
import com.finplat.financialmanage.common.BaseResponse;
import com.finplat.financialmanage.common.DeleteRequest;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.common.ResultUtils;
import com.finplat.financialmanage.constant.UserConstant;
import com.finplat.financialmanage.exception.BusinessException;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.model.dto.budgetcategory.BudgetCategoryAddRequest;
import com.finplat.financialmanage.model.dto.budgetcategory.BudgetCategoryEditRequest;
import com.finplat.financialmanage.model.dto.budgetcategory.BudgetCategoryQueryRequest;
import com.finplat.financialmanage.model.dto.budgetcategory.BudgetCategoryUpdateRequest;
import com.finplat.financialmanage.model.entity.BudgetCategory;
import com.finplat.financialmanage.model.entity.User;
import com.finplat.financialmanage.model.vo.BudgetCategoryVO;
import com.finplat.financialmanage.service.BudgetCategoryService;
import com.finplat.financialmanage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 预算类别接口
 */
@RestController
@RequestMapping("/budgetCategory")
@Slf4j
public class BudgetCategoryController {

    @Resource
    private BudgetCategoryService budgetCategoryService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建预算类别
     * @param budgetCategoryAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addBudgetCategory(@RequestBody BudgetCategoryAddRequest budgetCategoryAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(budgetCategoryAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        BudgetCategory budgetCategory = new BudgetCategory();
        BeanUtils.copyProperties(budgetCategoryAddRequest, budgetCategory);
        // 数据校验
        User loginUser = userService.getLoginUser(request);
        if(budgetCategoryService.isAvailableCategory(budgetCategory.getCategoryName(),loginUser.getId())){
               throw new BusinessException(ErrorCode.PARAMS_ERROR,"该预算类别已存在");
        };
        budgetCategoryService.validBudgetCategory(budgetCategory, true);
        // 填充默认值
        budgetCategory.setUserId(loginUser.getId());
        if(budgetCategoryAddRequest.getIsDefault() == null){
            budgetCategory.setIsDefault(0);
        }
/*        if(userService.isAdmin(loginUser)){
            budgetCategory.setIsDefault(1);
        }else{
            budgetCategory.setIsDefault(0);
        }*/
        // 写入数据库
        boolean result = budgetCategoryService.save(budgetCategory);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newBudgetCategoryId = budgetCategory.getId();
        return ResultUtils.success(newBudgetCategoryId);
    }

    /**
     * 删除预算类别
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteBudgetCategory(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        BudgetCategory oldBudgetCategory = budgetCategoryService.getById(id);
        ThrowUtils.throwIf(oldBudgetCategory == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        System.out.println(oldBudgetCategory.getUserId().equals(user.getId()));
        System.out.println(userService.isAdmin(request));
        if (!oldBudgetCategory.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = budgetCategoryService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取预算类别（封装类）
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<BudgetCategoryVO> getBudgetCategoryVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        BudgetCategory budgetCategory = budgetCategoryService.getById(id);
        ThrowUtils.throwIf(budgetCategory == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(budgetCategoryService.getBudgetCategoryVO(budgetCategory, request));
    }

    /**
     * 分页获取预算类别列表（仅管理员可用）
     * @param budgetCategoryQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<BudgetCategory>> listBudgetCategoryByPage(@RequestBody BudgetCategoryQueryRequest budgetCategoryQueryRequest) {
        long current = budgetCategoryQueryRequest.getCurrent();
        long size = budgetCategoryQueryRequest.getPageSize();
        // 查询数据库
        Page<BudgetCategory> budgetCategoryPage = budgetCategoryService.page(new Page<>(current, size),
                budgetCategoryService.getQueryWrapper(budgetCategoryQueryRequest));
        return ResultUtils.success(budgetCategoryPage);
    }

    /**
     * 分页获取当前登录用户创建的预算类别列表
     * @param budgetCategoryQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<BudgetCategoryVO>> listMyBudgetCategoryVOByPage(@RequestBody BudgetCategoryQueryRequest budgetCategoryQueryRequest,
                                                                             HttpServletRequest request) {
        ThrowUtils.throwIf(budgetCategoryQueryRequest == null, ErrorCode.PARAMS_ERROR);

        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        budgetCategoryQueryRequest.setUserId(userId);
        budgetCategoryQueryRequest.setIsDefault(1);

        long current = budgetCategoryQueryRequest.getCurrent();
        long size = budgetCategoryQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);

        // 构建查询条件
        QueryWrapper<BudgetCategory> queryWrapper = budgetCategoryService.getQueryWrapper(budgetCategoryQueryRequest);

        // 查询数据库
        Page<BudgetCategory> budgetCategoryPage = budgetCategoryService.page(new Page<>(current, size), queryWrapper);

        // 获取封装类
        return ResultUtils.success(budgetCategoryService.getBudgetCategoryVOPage(budgetCategoryPage, request));
    }


    // endregion
}
