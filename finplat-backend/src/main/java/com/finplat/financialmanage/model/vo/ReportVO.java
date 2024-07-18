package com.finplat.financialmanage.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 报表视图
 */
@Data
public class ReportVO {
    /**
     *  开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 总收入
     */
    private BigDecimal totalIncome;
    /**
     * 总支出
     */
    private BigDecimal totalExpenses;
    /**
     * 净收入
     */
    private BigDecimal netIncome;
    /**
     * 收入列表
     */
    private Map<String, BigDecimal> categoryIncomeSummary;
    /**
     * 支出列表
     */
    private Map<String, BigDecimal> categoryExpenseSummary;
}
