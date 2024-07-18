package com.finplat.financialmanage.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finplat.financialmanage.common.ErrorCode;
import com.finplat.financialmanage.exception.ThrowUtils;
import com.finplat.financialmanage.mapper.AccountMapper;
import com.finplat.financialmanage.model.entity.Account;
import com.finplat.financialmanage.model.entity.BankInfo;
import com.finplat.financialmanage.service.BankCardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 账户服务实现
 */
@Service
@Slf4j
public class BankCardServiceImpl extends ServiceImpl<AccountMapper, Account> implements BankCardService {


    @Autowired
    private Environment env;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 获取银行卡信息
     *
     * @param key
     * @return
     */
    @Override
    public BankInfo getBankCardInfo(String key) {
        String bankInfoUrl = env.getProperty("bank.info.url");
        String fileUrl = env.getProperty("bank.file.url");
        String logoUrl = env.getProperty("bank.logo.url");
        // 使用Hutool发送HTTP请求
        HttpResponse response = HttpRequest.get(bankInfoUrl + key + "&cardBinCheck=true").execute();
        String body = response.body();

        System.out.println(response.body().toString());
        // 使用Hutool解析JSON
        BankInfo bankInfo = JSONUtil.toBean(body, BankInfo.class);
        System.out.println(bankInfo.toString());
        if (!bankInfo.getStat().equalsIgnoreCase("ok")) {
            // 查询银行卡信息失败
            ThrowUtils.throwIf(true,ErrorCode.NOT_FOUND_ERROR, "查询银行卡信息失败");
        }
        System.out.println(bankInfo.getBank());
        if (StringUtils.isBlank(bankInfo.getBank())){
            // 查询银行卡信息失败
            ThrowUtils.throwIf(true,ErrorCode.NOT_FOUND_ERROR, "查询银行卡信息失败");
        }
        /*String bankJsonPath = this.getClass().getClassLoader().getResource(fileUrl).getPath();
        System.out.println("bankjson的位置是"+bankJsonPath);
        File bankJsonFile = new File(bankJsonPath);
        if (!bankJsonFile.exists()) {
            throw new RuntimeException("bank.json 文件不存在");
        }

        // 读取JSON内容并解析
        String jsonStr = FileUtil.readString(bankJsonFile, StandardCharsets.UTF_8);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        String bankName = jsonObject.getStr(bankInfo.getBank());*/
        // 使用ResourceLoader读取文件
        Resource resource = resourceLoader.getResource("classpath:" + fileUrl);
        if (!resource.exists()) {
            throw new RuntimeException("bank.json 文件不存在");
        }

        String jsonStr;
        try (InputStream inputStream = resource.getInputStream()) {
            jsonStr = IoUtil.read(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("读取bank.json 文件失败", e);
        }

        // 读取JSON内容并解析
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        String bankName = jsonObject.getStr(bankInfo.getBank());

        // 设置银行名称和Logo URL
        bankInfo.setBankName(bankName);
        bankInfo.setBankLogoURL(logoUrl + bankInfo.getBank());

        return bankInfo;
    }
}
