package com.finplat.financialmanage.model.vo;

import com.finplat.financialmanage.model.entity.FinancialRecord;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务记录视图

 */
@Data
public class FinancialRecordVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 交易描述
     */
    private String transactionDesc;

    /**
     * 账户 id
     */
    private Long accountId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易类型（0-收入，1-支出）
     */
    private Integer transactionType;

    /**
     * 交易类别（如餐饮、娱乐）
     */
    private String category;

    /**
     * 交易时间
     */
    private Date transactionTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 封装类转对象
     *
     * @param financialRecordVO
     * @return
     */
    public static FinancialRecord voToObj(FinancialRecordVO financialRecordVO) {
        if (financialRecordVO == null) {
            return null;
        }
        FinancialRecord financialRecord = new FinancialRecord();
        BeanUtils.copyProperties(financialRecordVO, financialRecord);
        return financialRecord;
    }

    /**
     * 对象转封装类
     *
     * @param financialRecord
     * @return
     */
    public static FinancialRecordVO objToVo(FinancialRecord financialRecord) {
        if (financialRecord == null) {
            return null;
        }
        FinancialRecordVO financialRecordVO = new FinancialRecordVO();
        BeanUtils.copyProperties(financialRecord, financialRecordVO);
        return financialRecordVO;
    }
}
