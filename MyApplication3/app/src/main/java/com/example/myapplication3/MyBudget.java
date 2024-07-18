package com.example.myapplication3;

public class MyBudget {
    private String budget_id;
    private String budgetdesc;
    private String account_id;
    private String budget;
    private String bgt_category;
    private String startDate;
    private String endDate;

    public MyBudget(String budget_id, String budgetdesc, String account_id, String budget, String bgt_category,String startDate, String endDate) {
        this.budget_id = budget_id;
        this.budgetdesc = budgetdesc;
        this.account_id = account_id;
        this.budget = budget;
        this.bgt_category = bgt_category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter methods
    public String getAccount_Id() { return account_id; }
    public String getBudget_Id() { return budget_id; }
    public String getBudgetdesc() { return budgetdesc; }
    public String getBudget() { return budget; }
    public String getBgt_category() { return bgt_category; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }

}
