package com.finplat.financialmanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.BankInfo;

/**
 * 账户服务

 */
public interface BankCardService extends IService<Account> {
    /**
     * 获取银行卡信息
     *
     * @param key
     * @return
     */
    BankInfo getBankCardInfo(String key);

}
