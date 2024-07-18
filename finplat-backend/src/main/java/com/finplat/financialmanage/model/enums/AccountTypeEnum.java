package com.finplat.financialmanage.model.enums;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户类型枚举
 */
public enum AccountTypeEnum {
    CREDIT("信用卡",0),

    DEBIT("储蓄卡",1),

    WALLET("电子钱包",2);

    private final String text;
    private final int value;

    AccountTypeEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static AccountTypeEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (AccountTypeEnum anEnum : AccountTypeEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }

    public static AccountTypeEnum getEnumByBankText(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        //根据DC或者CC返回对应的枚举类
        if(text.contains("DC")){
            return AccountTypeEnum.DEBIT;
        }else if(text.contains("CC")){
            return AccountTypeEnum.CREDIT;
        }
        return null;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
