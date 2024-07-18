package com.finplat.financialmanage.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 开户行
 */
@Data
public class BankInfo implements Serializable {

    /**
     * 银行卡类型
     */
    private String cardType;

    /**
     * 银行缩写
     */
    private String bank;

    /**
     * 银行卡号
     */
    private String key;

    /**
     * 银行卡LOGO
     */
    private String bankLogoURL;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 状态
     */
    private String stat;

}