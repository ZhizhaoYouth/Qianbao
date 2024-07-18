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
import com.finplat.financialmanage.mapper.BudgetMapper;
import com.finplat.financialmanage.model.dto.budget.BudgetAddRequest;
import com.finplat.financialmanage.model.dto.budget.BudgetEditRequest;
import com.finplat.financialmanage.model.dto.budget.BudgetQueryRequest;
import com.finplat.financialmanage.model.dto.budget.BudgetUpdateRequest;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.Budget;
import com.finplat.financialmanage.model.entity.FinancialRecord;
import com.finplat.financialmanage.model.entity.User;
import com.finplat.financialmanage.model.enums.TransactionTypeEnum;
import com.finplat.financialmanage.model.vo.BudgetVO;
import com.finplat.financialmanage.service.AccountService;
import com.finplat.financialmanage.service.BudgetService;
import com.finplat.financialmanage.service.FinancialRecordService;
import com.finplat.financialmanage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预算接口
 */
@RestController
@RequestMapping("/budget")
@Slf4j
public class BudgetController {

    @Resource
    private BudgetService budgetService;

    @Resource
    private UserService userService;

    @Resource
    private AccountService accountService;

    @Resource
    private BudgetMapper budgetMapper;

    @Resource
    private FinancialRecordService financialRecordService;

    // region 增删改查

    /**
     * 创建预算
     * @param budgetAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addBudget(@RequestBody BudgetAddRequest budgetAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(budgetAddRequest == null, ErrorCode.PARAMS_ERROR);
        // 在此处将实体类和 DTO 进行转换
        Budget budget = new Budget();
        BeanUtils.copyProperties(budgetAddRequest, budget);
        // 数据校验
        Long AccountId=budgetAddRequest.getAccountId();
        Long UserId=userService.getLoginUser(request).getId();
        Account account = accountService.getById(AccountId);
        // 添加记录的账户必须存在
        if (account == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户不存在");
        }
        //账户必须已经过审才能开始添加记录
        if(!accountService.isPassed(account)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户未过审");
        }
        //如果当前登录用户不是管理员，则账户必须属于当前登录用户,并且过审
        if(!userService.isAdmin(request)){
            if(!account.getUserId().equals(UserId)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户不属于当前用户");
            }
        }
        // 同一账户的同一类别预算只能有一个
        BudgetQueryRequest budgetQueryRequest=new BudgetQueryRequest();
        budgetQueryRequest.setAccountId(AccountId);
        budgetQueryRequest.setCategory(budgetAddRequest.getCategory());
        QueryWrapper<Budget> queryWrapper=budgetService.getQueryWrapper(budgetQueryRequest);
        if(budgetService.count(queryWrapper)>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"该账户已存在该类别的预算");
        }
        // 填充默认值
        budget.setUserId(UserId);
        budgetService.validBudget(budget, true);
        // 写入数据库
        boolean result = budgetService.save(budget);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newBudgetId = budget.getId();
        return ResultUtils.success(newBudgetId);
    }

    /**
     * 删除预算
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteBudget(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Budget oldBudget = budgetService.getById(id);
        ThrowUtils.throwIf(oldBudget == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldBudget.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = budgetService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取预算（封装类）
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<BudgetVO> getBudgetVOById(long id, HttpServletRequest request) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Budget budget = budgetService.getById(id);
        ThrowUtils.throwIf(budget == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(budgetService.getBudgetVO(budget, request));
    }

    /**
     * 分页获取预算列表（仅管理员可用）
     * @param budgetQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Budget>> listBudgetByPage(@RequestBody BudgetQueryRequest budgetQueryRequest) {
        long current = budgetQueryRequest.getCurrent();
        long size = budgetQueryRequest.getPageSize();
        // 查询数据库
        Page<Budget> budgetPage = budgetService.page(new Page<>(current, size),
                budgetService.getQueryWrapper(budgetQueryRequest));
        return ResultUtils.success(budgetPage);
    }

    /**
     * 分页获取当前登录用户创建的预算列表
     * @param budgetQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<BudgetVO>> listMyBudgetVOByPage(@RequestBody BudgetQueryRequest budgetQueryRequest,
                                                                 HttpServletRequest request) {
        ThrowUtils.throwIf(budgetQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser(request);
        budgetQueryRequest.setUserId(loginUser.getId());
        long current = budgetQueryRequest.getCurrent();
        long size = budgetQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<Budget> budgetPage = budgetService.page(new Page<>(current, size),
                budgetService.getQueryWrapper(budgetQueryRequest));
        // 获取封装类
        return ResultUtils.success(budgetService.getBudgetVOPage(budgetPage, request));
    }

    /**
     * 编辑预算（给用户使用）
     * @param budgetEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editBudget(@RequestBody BudgetEditRequest budgetEditRequest, HttpServletRequest request) {
        if (budgetEditRequest == null || budgetEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 在此处将实体类和 DTO 进行转换
        Budget budget = new Budget();
        BeanUtils.copyProperties(budgetEditRequest, budget);
        // 数据校验
        User loginUser = userService.getLoginUser(request);
        budgetService.validBudget(budget, false);
        // 判断是否存在
        long id = budgetEditRequest.getId();
        Budget oldBudget = budgetService.getById(id);
        ThrowUtils.throwIf(oldBudget == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldBudget.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = budgetService.updateById(budget);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion

    /**
     * 获取当前登录用户的所有账户的预算超支情况
     * @param request
     * @return
     */
    @GetMapping("/check")
    public BaseResponse<List<String>> checkBudgetExceed(HttpServletRequest request) {
        // 获取当前登录用户信息
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();

        // 获取当前登录用户的所有账户
        List<Account> accountList = accountService.list(new QueryWrapper<Account>().eq("userId", userId));

        // 记录超支情况的列表
        List<String> exceededBudgetMessages = new ArrayList<>();

        // 遍历每个账户
        for (Account account : accountList) {
            // 获取账户ID
            Long accountId = account.getId();
            String accountName = account.getAccountName();

            // 查询账户的所有种类的预算信息
            List<Budget> budgets = budgetService.list(new QueryWrapper<Budget>().eq("accountId", accountId));

            // 遍历每种预算
            for (Budget budget : budgets) {
                // 获取预算ID和时间段
                Date startDate = budget.getStartDate();
                Date endDate = budget.getEndDate();
                String category = budget.getCategory();
                BigDecimal amount=budget.getAmount();
                //如果预算已经结束，则跳过
                Date now=new Date();
                if(now.after(endDate)){
                    continue;
                }
                // 计算在时间段内此类别的总支出金额
                QueryWrapper<FinancialRecord> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("category",category).eq("accountId",accountId).between("transactionTime",startDate,endDate);
                List<FinancialRecord> financialRecords = financialRecordService.list(queryWrapper);
                // 计算净支出
                BigDecimal totalExpenditure = BigDecimal.ZERO;
                for(FinancialRecord record:financialRecords){
                    TransactionTypeEnum type=TransactionTypeEnum.getEnumByValue(record.getTransactionType());
                    if(type == TransactionTypeEnum.EXPENSE) {
                        totalExpenditure = totalExpenditure.add(record.getAmount());
                    }else if(type == TransactionTypeEnum.INCOME){
                        totalExpenditure = totalExpenditure.subtract(record.getAmount());
                    }
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 比较支出与预算金额
                if (totalExpenditure.compareTo(amount) > 0) {
                    String message = String.format("账户“%s”超出了时间为%s到%s的“%s”类别的预算%s元！",
                            accountName, dateFormat.format(startDate), dateFormat.format(endDate),category,totalExpenditure.subtract(amount));
                    exceededBudgetMessages.add(message);
                }
            }
        }
        return ResultUtils.success(exceededBudgetMessages);
    }
}
