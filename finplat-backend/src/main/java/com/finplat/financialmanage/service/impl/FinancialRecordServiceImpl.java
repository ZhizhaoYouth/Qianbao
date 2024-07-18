package com.finplat.financialmanage.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.constant.CommonConstant;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.FinancialRecordMapper;
import com.finplat.financialmanage.model.dto.financialrecord.FinancialRecordQueryRequest;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.FinancialRecord;
import com.finplat.financialmanage.model.enums.TransactionTypeEnum;
import com.finplat.financialmanage.model.vo.FinancialRecordVO;
import com.finplat.financialmanage.service.AccountService;
import com.finplat.financialmanage.service.BudgetCategoryService;
import com.finplat.financialmanage.service.FinancialRecordService;
import com.finplat.financialmanage.service.UserService;
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
 * 财务记录服务实现
 */
@Service
@Slf4j
public class FinancialRecordServiceImpl extends ServiceImpl<FinancialRecordMapper, FinancialRecord> implements FinancialRecordService {

    @Resource
    private UserService userService;
    @Resource
    private BudgetCategoryService budgetCategoryService;

    @Resource
    private AccountService accountService;

    /**
     * 校验数据
     *
     * @param financialRecord
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validFinancialRecord(FinancialRecord financialRecord, boolean add) {
        ThrowUtils.throwIf(financialRecord == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String transactionDesc = financialRecord.getTransactionDesc();
        Long accountId = financialRecord.getAccountId();
        BigDecimal amount = financialRecord.getAmount();
        Integer transactionType = financialRecord.getTransactionType();
        String category = financialRecord.getCategory();
        Date transactionTime = financialRecord.getTransactionTime();
        Long userId=financialRecord.getUserId();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(accountId==null||accountId<=0, ErrorCode.PARAMS_ERROR,"账户id非法");
            ThrowUtils.throwIf(amount==null, ErrorCode.PARAMS_ERROR,"金额非法");
            ThrowUtils.throwIf(StringUtils.isBlank(transactionDesc), ErrorCode.PARAMS_ERROR,"交易描述不能为空");
            TransactionTypeEnum transactionTypeEnum=TransactionTypeEnum.getEnumByValue(transactionType);
            ThrowUtils.throwIf(transactionTypeEnum==null, ErrorCode.PARAMS_ERROR,"交易类型非法");
            ThrowUtils.throwIf(StringUtils.isBlank(category), ErrorCode.PARAMS_ERROR,"分类不能为空");
            ThrowUtils.throwIf(transactionTime==null, ErrorCode.PARAMS_ERROR,"交易时间不能为空");
        }
        // 修改数据时，有参数则校验
        // 补充校验规则
        if (StringUtils.isNotBlank(transactionDesc)) {
            ThrowUtils.throwIf(transactionDesc.length() > 80, ErrorCode.PARAMS_ERROR, "交易描述不要大于80字符");
            ThrowUtils.throwIf(transactionDesc.length() < 2, ErrorCode.PARAMS_ERROR, "交易描述不要小于2字符");
        }
        if(StringUtils.isNotBlank(category)){
            if(!budgetCategoryService.isAvailableCategory(category,userId)){
                ThrowUtils.throwIf(true, ErrorCode.PARAMS_ERROR, "类别不可用");
            }
        }
        //金额不能小于等于0
        if (amount != null) {
            ThrowUtils.throwIf(amount.compareTo(BigDecimal.ZERO) <= 0, ErrorCode.PARAMS_ERROR, "金额必须大于0");
        }
    }

    /**
     * 获取查询条件
     * @param financialRecordQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<FinancialRecord> getQueryWrapper(FinancialRecordQueryRequest financialRecordQueryRequest) {
        QueryWrapper<FinancialRecord> queryWrapper = new QueryWrapper<>();
        if (financialRecordQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = financialRecordQueryRequest.getId();
        String transactionDesc = financialRecordQueryRequest.getTransactionDesc();
        Long accountId = financialRecordQueryRequest.getAccountId();
        Long userId = financialRecordQueryRequest.getUserId();
        BigDecimal amount = financialRecordQueryRequest.getAmount();
        Integer transactionType = financialRecordQueryRequest.getTransactionType();
        String category = financialRecordQueryRequest.getCategory();
        Date transactionTime = financialRecordQueryRequest.getTransactionTime();
        Long notId = financialRecordQueryRequest.getNotId();
        String searchText = financialRecordQueryRequest.getSearchText();
        String sortField = financialRecordQueryRequest.getSortField();
        String sortOrder = financialRecordQueryRequest.getSortOrder();

        // 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("transactionDesc", searchText).or().like("category", searchText));
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(transactionDesc), "transactionDesc", transactionDesc);

        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(accountId), "accountId", accountId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(amount), "amount", amount);
        queryWrapper.eq(ObjectUtils.isNotEmpty(transactionType), "transactionType", transactionType);
        queryWrapper.eq(StringUtils.isNotBlank(category), "category", category);
        queryWrapper.eq(ObjectUtils.isNotEmpty(transactionTime), "transactionTime", transactionTime);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取财务记录封装
     *
     * @param financialRecord
     * @param request
     * @return
     */
    @Override
    public FinancialRecordVO getFinancialRecordVO(FinancialRecord financialRecord, HttpServletRequest request) {
        // 对象转封装类
        FinancialRecordVO financialRecordVO = FinancialRecordVO.objToVo(financialRecord);
        return financialRecordVO;
    }

    /**
     * 分页获取财务记录封装
     *
     * @param financialRecordPage
     * @param request
     * @return
     */
    @Override
    public Page<FinancialRecordVO> getFinancialRecordVOPage(Page<FinancialRecord> financialRecordPage, HttpServletRequest request) {
        List<FinancialRecord> financialRecordList = financialRecordPage.getRecords();
        Page<FinancialRecordVO> financialRecordVOPage = new Page<>(financialRecordPage.getCurrent(), financialRecordPage.getSize(), financialRecordPage.getTotal());
        if (CollUtil.isEmpty(financialRecordList)) {
            return financialRecordVOPage;
        }
        // 对象列表 => 封装对象列表
        List<FinancialRecordVO> financialRecordVOList = financialRecordList.stream().map(financialRecord -> {
            return FinancialRecordVO.objToVo(financialRecord);
        }).collect(Collectors.toList());
        financialRecordVOPage.setRecords(financialRecordVOList);
        return financialRecordVOPage;
    }

    /**
     * 修改余额
     * @param transactionType
     * @param amount
     * @return
     */
    @Override
    public boolean changeBalance(Integer transactionType, BigDecimal amount, Account account) {
        TransactionTypeEnum transactionTypeEnum=TransactionTypeEnum.getEnumByValue(transactionType);
        if(transactionTypeEnum==null){
            ThrowUtils.throwIf(true,ErrorCode.PARAMS_ERROR,"交易类型错误");
        }
        if(transactionTypeEnum==TransactionTypeEnum.INCOME){
            //是收入，修改账户里的余额
            account.setBalance(account.getBalance().add(amount));
        }else{
            //是支出
            account.setBalance(account.getBalance().subtract(amount));
        }
        //操作数据库
        boolean result= accountService.updateById(account);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return true;
    }
}
