package com.example.myapplication3;

public class MyFinanceRecord {
    private String financeRecordId;
    private String transactionDesc;
    private String accountId;
    private String amount;
    private String transactionType;
    private String category;
    private String transactionTime;

    public MyFinanceRecord(String financeRecordId, String transactionDesc, String accountId, String amount, String transactionType, String category, String transactionTime) {
        this.financeRecordId = financeRecordId;
        this.transactionDesc = transactionDesc;
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.category = category;
        this.transactionTime = transactionTime;
    }

    public String getFinanceRecordId() {
        return financeRecordId;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType.equals("1") ? "支出" : "收入";
    }

    public String getCategory() {
        return category;
    }

    public String getTransactionTime() {
        return transactionTime;
    }
}
