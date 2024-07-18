package com.finplat.financialmanage.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户类型枚举
 *
 */
public enum BudgetCategoryIsDefaultEnum {
    CUSTOM("用户自定义类型",0),

    DEFAULT("系统默认类型",1);

    private final String text;
    private final int value;

    BudgetCategoryIsDefaultEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static BudgetCategoryIsDefaultEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (BudgetCategoryIsDefaultEnum anEnum : BudgetCategoryIsDefaultEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
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
