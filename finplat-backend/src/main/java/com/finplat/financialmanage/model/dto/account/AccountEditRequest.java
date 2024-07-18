package com.finplat.financialmanage.model.dto.account;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑账户请求
 */
@Data
public class AccountEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账户号码
     */
    private String accountNumber;

    /**
     * 账户类型（0-信用卡，1-储蓄卡，2-电子钱包）
     */
    private Integer accountType;


    /**
     * 开户行名称
     */
    private String bankName;


    private static final long serialVersionUID = 1L;
}