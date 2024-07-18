package com.finplat.financialmanage.controller;

import com.finplat.financialmanage.common.BaseResponse;
import com.finplat.financialmanage.common.ResultUtils;
import com.finplat.financialmanage.model.dto.report.ReportQueryRequest;
import com.finplat.financialmanage.model.vo.ReportVO;
import com.finplat.financialmanage.service.ReportService;
import com.finplat.financialmanage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 报表接口
 */
@RestController
@Slf4j
@RequestMapping("/report")
public class ReportController {

    @Resource
    private UserService userService;
    @Resource
    private ReportService reportService;
    /**
     * 获取报表
     * @param reportQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/generate")
    public BaseResponse<ReportVO> getReport(@RequestBody ReportQueryRequest reportQueryRequest, HttpServletRequest request) {
        Long userId=userService.getLoginUser(request).getId();
        ReportVO reportVO=reportService.generateReport(reportQueryRequest,userId);
        return ResultUtils.success(reportVO);
    }
}
