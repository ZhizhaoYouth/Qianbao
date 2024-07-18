package com.finplat.financialmanage.service;

import com.finplat.financialmanage.model.dto.report.ReportQueryRequest;
import com.finplat.financialmanage.model.vo.ReportVO;


/**
 * 报表服务
 */
public interface ReportService{
    ReportVO generateReport(ReportQueryRequest reportQueryRequest, Long UserId);
}
