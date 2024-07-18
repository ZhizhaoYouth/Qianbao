package com.example.myapplication3;

import java.math.BigDecimal;

public class MyAccount {
    private String account_id;
    private String accountName;
    private String accountNumber;
    private String cardType;
    private String bankLogoURL;
    private String balance;

    public MyAccount(String account_id, String accountName, String accountNumber, String cardType, String bankLogoURL,String balance) {
        this.account_id = account_id;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.cardType = cardType;
        this.bankLogoURL = bankLogoURL;
        this.balance = balance;
    }

    // Getter methods
    public String getAccount_Id() { return account_id; }
    public String getAccountName() { return accountName; }
    public String getAccountNumber() { return accountNumber; }
    public String getCardType() { return cardType; }
    public String getBankLogoURL() { return bankLogoURL; }
    public String getBalance() { return balance; }
}
