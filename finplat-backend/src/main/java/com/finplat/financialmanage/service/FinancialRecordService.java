package com.finplat.financialmanage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.finplat.financialmanage.model.dto.financialrecord.FinancialRecordQueryRequest;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.FinancialRecord;
import com.finplat.financialmanage.model.vo.FinancialRecordVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 财务记录服务
 */
public interface FinancialRecordService extends IService<FinancialRecord> {

    /**
     * 校验数据
     *
     * @param financialRecord
     * @param add 对创建的数据进行校验
     */
    void validFinancialRecord(FinancialRecord financialRecord, boolean add);

    /**
     * 获取查询条件
     *
     * @param financialRecordQueryRequest
     * @return
     */
    QueryWrapper<FinancialRecord> getQueryWrapper(FinancialRecordQueryRequest financialRecordQueryRequest);
    
    /**
     * 获取财务记录封装
     *
     * @param financialRecord
     * @param request
     * @return
     */
    FinancialRecordVO getFinancialRecordVO(FinancialRecord financialRecord, HttpServletRequest request);

    /**
     * 分页获取财务记录封装
     *
     * @param financialRecordPage
     * @param request
     * @return
     */
    Page<FinancialRecordVO> getFinancialRecordVOPage(Page<FinancialRecord> financialRecordPage, HttpServletRequest request);

    /**
     * 修改余额
     * @param transactionType
     * @param amount
     * @return
     */
    boolean changeBalance(Integer transactionType, BigDecimal amount, Account account);
}
