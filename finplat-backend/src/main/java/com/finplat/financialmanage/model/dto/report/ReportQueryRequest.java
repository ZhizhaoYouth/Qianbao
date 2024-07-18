package com.finplat.financialmanage.model.dto.report;

import lombok.Data;

import java.util.Date;

@Data
public class ReportQueryRequest {
    private Date startDate;
    private Date endDate;
}
