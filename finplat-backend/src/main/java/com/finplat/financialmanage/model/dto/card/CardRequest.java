package com.finplat.financialmanage.model.dto.card;

import lombok.Data;

import java.io.Serializable;

/**
 * 检查银行卡号请求
 */
@Data
public class CardRequest implements Serializable {

    /**
     * 银行卡号
     */
    private String key;


    private static final long serialVersionUID = 1L;
}