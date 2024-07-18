package com.finplat.financialmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.model.dto.report.ReportQueryRequest;
import com.finplat.financialmanage.model.entity.FinancialRecord;
import com.finplat.financialmanage.model.vo.ReportVO;
import com.finplat.financialmanage.service.FinancialRecordService;
import com.finplat.financialmanage.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 报表服务实现类
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Resource
    FinancialRecordService financialRecordService;

    /**
     * 生成报表
     *
     * @param reportQueryRequest
     * @param UserId
     * @return
     */
    @Override
    public ReportVO generateReport(ReportQueryRequest reportQueryRequest, Long UserId) {

        Date startDate = reportQueryRequest.getStartDate();
        Date endDate = reportQueryRequest.getEndDate();
        //校验开始时间不能晚于结束时间
        if (startDate != null && endDate != null && startDate.after(endDate)) {
            ThrowUtils.throwIf(true, ErrorCode.PARAMS_ERROR,"开始时间不能晚于结束时间");
        }
        //拼接查询条件
        QueryWrapper<FinancialRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", UserId);
        queryWrapper.ge(startDate != null, "transactionTime", startDate);
        queryWrapper.le(endDate != null, "transactionTime", endDate);

        // 查询此时段内财务记录数据
        List<FinancialRecord> financialRecordList = financialRecordService.list(queryWrapper);

        //计算总收入与总支出，净收入
        BigDecimal totalIncome=financialRecordList.stream()
                .filter(record -> record.getTransactionType() == 0)
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses=financialRecordList.stream()
                .filter(record -> record.getTransactionType() == 1)
                .map(FinancialRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netIncome = totalIncome.subtract(totalExpenses);


        // 计算每个类别的收入与支出
        Map<String, BigDecimal> incomeByCategory = financialRecordList.stream()
                .filter(record -> record.getTransactionType() == 0)
                .collect(Collectors.groupingBy(FinancialRecord::getCategory
                        , Collectors.reducing(BigDecimal.ZERO, FinancialRecord::getAmount, BigDecimal::add)));
        Map<String, BigDecimal> expenseByCategory = financialRecordList.stream()
                .filter(record -> record.getTransactionType() == 1)
                .collect(Collectors.groupingBy(FinancialRecord::getCategory
                        , Collectors.reducing(BigDecimal.ZERO, FinancialRecord::getAmount, BigDecimal::add)));

        // 填充值
        ReportVO reportVO = new ReportVO();
        reportVO.setStartDate(startDate);
        reportVO.setEndDate(endDate);
        reportVO.setCategoryIncomeSummary(incomeByCategory);
        reportVO.setCategoryExpenseSummary(expenseByCategory);
        reportVO.setNetIncome(netIncome);
        reportVO.setTotalIncome(totalIncome);
        reportVO.setTotalExpenses(totalExpenses);

        return reportVO;
    }

}
