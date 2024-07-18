declare namespace API {
  type Account = {
    accountName?: string;
    accountNumber?: string;
    accountType?: number;
    balance?: number;
    bankName?: string;
    createTime?: string;
    id?: number;
    isDelete?: number;
    reviewMessage?: string;
    reviewStatus?: number;
    reviewTime?: string;
    reviewerId?: number;
    updateTime?: string;
    userId?: number;
  };

  type AccountAddRequest = {
    accountName?: string;
    accountNumber?: string;
    accountType?: number;
    balance?: number;
    bankName?: string;
  };

  type AccountDetailsVO = {
    accountName?: string;
    accountNumber?: string;
    accountType?: number;
    balance?: number;
    bankInfo?: BankInfo;
    bankName?: string;
    budgetVOPage?: PageBudgetVO_;
    createTime?: string;
    financialRecordVOPage?: PageFinancialRecordVO_;
    id?: number;
    reviewMessage?: string;
    reviewStatus?: number;
    reviewTime?: string;
    updateTime?: string;
    userId?: number;
  };

  type AccountEditRequest = {
    accountName?: string;
    accountNumber?: string;
    accountType?: number;
    bankName?: string;
    id?: number;
  };

  type AccountQueryRequest = {
    accountName?: string;
    accountNumber?: string;
    accountType?: number;
    balance?: number;
    bankName?: string;
    current?: number;
    id?: number;
    notId?: number;
    pageSize?: number;
    reviewMessage?: string;
    reviewStatus?: number;
    reviewTime?: string;
    reviewerId?: number;
    searchText?: string;
    sortField?: string;
    sortOrder?: string;
    userId?: number;
  };

  type AccountVO = {
    accountName?: string;
    accountNumber?: string;
    accountType?: number;
    balance?: number;
    bankInfo?: BankInfo;
    bankName?: string;
    createTime?: string;
    id?: number;
    reviewMessage?: string;
    reviewStatus?: number;
    reviewTime?: string;
    updateTime?: string;
    userId?: number;
  };

  type BankInfo = {
    bank?: string;
    bankLogoURL?: string;
    bankName?: string;
    cardType?: string;
    key?: string;
    stat?: string;
  };

  type BaseResponseAccountDetailsVO_ = {
    code?: number;
    data?: AccountDetailsVO;
    message?: string;
  };

  type BaseResponseAccountVO_ = {
    code?: number;
    data?: AccountVO;
    message?: string;
  };

  type BaseResponseBankInfo_ = {
    code?: number;
    data?: BankInfo;
    message?: string;
  };

  type BaseResponseBigdecimal_ = {
    code?: number;
    data?: number;
    message?: string;
  };

  type BaseResponseBoolean_ = {
    code?: number;
    data?: boolean;
    message?: string;
  };

  type BaseResponseBudgetCategoryVO_ = {
    code?: number;
    data?: BudgetCategoryVO;
    message?: string;
  };

  type BaseResponseBudgetVO_ = {
    code?: number;
    data?: BudgetVO;
    message?: string;
  };

  type BaseResponseFinancialRecordVO_ = {
    code?: number;
    data?: FinancialRecordVO;
    message?: string;
  };

  type BaseResponseListString_ = {
    code?: number;
    data?: string[];
    message?: string;
  };

  type BaseResponseLoginLogVO_ = {
    code?: number;
    data?: LoginLogVO;
    message?: string;
  };

  type BaseResponseLoginUserVO_ = {
    code?: number;
    data?: LoginUserVO;
    message?: string;
  };

  type BaseResponseLong_ = {
    code?: number;
    data?: number;
    message?: string;
  };

  type BaseResponsePageAccount_ = {
    code?: number;
    data?: PageAccount_;
    message?: string;
  };

  type BaseResponsePageAccountVO_ = {
    code?: number;
    data?: PageAccountVO_;
    message?: string;
  };

  type BaseResponsePageBudget_ = {
    code?: number;
    data?: PageBudget_;
    message?: string;
  };

  type BaseResponsePageBudgetCategory_ = {
    code?: number;
    data?: PageBudgetCategory_;
    message?: string;
  };

  type BaseResponsePageBudgetCategoryVO_ = {
    code?: number;
    data?: PageBudgetCategoryVO_;
    message?: string;
  };

  type BaseResponsePageBudgetVO_ = {
    code?: number;
    data?: PageBudgetVO_;
    message?: string;
  };

  type BaseResponsePageFinancialRecord_ = {
    code?: number;
    data?: PageFinancialRecord_;
    message?: string;
  };

  type BaseResponsePageFinancialRecordVO_ = {
    code?: number;
    data?: PageFinancialRecordVO_;
    message?: string;
  };

  type BaseResponsePageLoginLog_ = {
    code?: number;
    data?: PageLoginLog_;
    message?: string;
  };

  type BaseResponsePageLoginLogVO_ = {
    code?: number;
    data?: PageLoginLogVO_;
    message?: string;
  };

  type BaseResponsePageUser_ = {
    code?: number;
    data?: PageUser_;
    message?: string;
  };

  type BaseResponseReportVO_ = {
    code?: number;
    data?: ReportVO;
    message?: string;
  };

  type BaseResponseString_ = {
    code?: number;
    data?: string;
    message?: string;
  };

  type BaseResponseUser_ = {
    code?: number;
    data?: User;
    message?: string;
  };

  type Budget = {
    accountId?: number;
    amount?: number;
    budgetDesc?: string;
    category?: string;
    createTime?: string;
    endDate?: string;
    id?: number;
    isDelete?: number;
    startDate?: string;
    updateTime?: string;
    userId?: number;
  };

  type BudgetAddRequest = {
    accountId?: number;
    amount?: number;
    budgetDesc?: string;
    category?: string;
    endDate?: string;
    startDate?: string;
  };

  type BudgetCategory = {
    categoryName?: string;
    createTime?: string;
    id?: number;
    isDefault?: number;
    isDelete?: number;
    updateTime?: string;
    userId?: number;
  };

  type BudgetCategoryAddRequest = {
    categoryName?: string;
    isDefault?: number;
  };

  type BudgetCategoryQueryRequest = {
    categoryName?: string;
    current?: number;
    id?: number;
    isDefault?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    userId?: number;
  };

  type BudgetCategoryVO = {
    categoryName?: string;
    createTime?: string;
    id?: number;
    isDefault?: number;
    updateTime?: string;
    userId?: number;
  };

  type BudgetEditRequest = {
    accountId?: number;
    amount?: number;
    budgetDesc?: string;
    category?: string;
    endDate?: string;
    id?: number;
    startDate?: string;
  };

  type BudgetQueryRequest = {
    accountId?: number;
    amount?: number;
    budgetDesc?: string;
    category?: string;
    current?: number;
    endDate?: string;
    id?: number;
    notId?: number;
    pageSize?: number;
    searchText?: string;
    sortField?: string;
    sortOrder?: string;
    startDate?: string;
    userId?: number;
  };

  type BudgetVO = {
    accountId?: number;
    amount?: number;
    budgetDesc?: string;
    category?: string;
    createTime?: string;
    endDate?: string;
    id?: number;
    startDate?: string;
    updateTime?: string;
    userId?: number;
  };

  type CardRequest = {
    key?: string;
  };

  type DeleteRequest = {
    id?: number;
  };

  type FinancialRecord = {
    accountId?: number;
    amount?: number;
    category?: string;
    createTime?: string;
    id?: number;
    isDelete?: number;
    transactionDesc?: string;
    transactionTime?: string;
    transactionType?: number;
    updateTime?: string;
    userId?: number;
  };

  type FinancialRecordAddRequest = {
    accountId?: number;
    amount?: number;
    category?: string;
    transactionDesc?: string;
    transactionTime?: string;
    transactionType?: number;
  };

  type FinancialRecordEditRequest = {
    accountId?: number;
    amount?: number;
    category?: string;
    id?: number;
    transactionDesc?: string;
    transactionTime?: string;
    transactionType?: number;
  };

  type FinancialRecordQueryRequest = {
    accountId?: number;
    amount?: number;
    category?: string;
    current?: number;
    id?: number;
    notId?: number;
    pageSize?: number;
    searchText?: string;
    sortField?: string;
    sortOrder?: string;
    transactionDesc?: string;
    transactionTime?: string;
    transactionType?: number;
    userId?: number;
  };

  type FinancialRecordVO = {
    accountId?: number;
    amount?: number;
    category?: string;
    createTime?: string;
    id?: number;
    transactionDesc?: string;
    transactionTime?: string;
    transactionType?: number;
    updateTime?: string;
    userId?: number;
  };

  type getAccountDetailsPagesUsingPOSTParams = {
    /** budgetCurrent */
    budgetCurrent?: number;
    /** budgetSize */
    budgetSize?: number;
    /** id */
    id: number;
    /** recordCurrent */
    recordCurrent?: number;
    /** recordSize */
    recordSize?: number;
  };

  type getAccountVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getBudgetCategoryVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getBudgetVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getFinancialRecordVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getLoginLogVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getUserByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type LoginLog = {
    browserInfo?: string;
    createTime?: string;
    deviceType?: string;
    id?: number;
    ipAddress?: string;
    isDelete?: number;
    location?: string;
    loginTime?: string;
    operatingSystem?: string;
    updateTime?: string;
    userId?: number;
  };

  type LoginLogAddRequest = {
    browserInfo?: string;
    deviceType?: string;
    ipAddress?: string;
    location?: string;
    loginTime?: string;
    operatingSystem?: string;
  };

  type LoginLogQueryRequest = {
    browserInfo?: string;
    current?: number;
    deviceType?: string;
    id?: number;
    ipAddress?: string;
    location?: string;
    loginTime?: string;
    operatingSystem?: string;
    pageSize?: number;
    searchText?: string;
    sortField?: string;
    sortOrder?: string;
    userId?: number;
  };

  type LoginLogVO = {
    browserInfo?: string;
    createTime?: string;
    deviceType?: string;
    id?: number;
    ipAddress?: string;
    location?: string;
    loginTime?: string;
    operatingSystem?: string;
    updateTime?: string;
    userId?: number;
  };

  type LoginUserVO = {
    createTime?: string;
    id?: number;
    updateTime?: string;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type OrderItem = {
    asc?: boolean;
    column?: string;
  };

  type PageAccount_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: Account[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageAccountVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: AccountVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageBudget_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: Budget[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageBudgetCategory_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: BudgetCategory[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageBudgetCategoryVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: BudgetCategoryVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageBudgetVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: BudgetVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageFinancialRecord_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: FinancialRecord[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageFinancialRecordVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: FinancialRecordVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageLoginLog_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: LoginLog[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageLoginLogVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: LoginLogVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageUser_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: User[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type ReportQueryRequest = {
    endDate?: string;
    startDate?: string;
  };

  type ReportVO = {
    categoryExpenseSummary?: Record<string, any>;
    categoryIncomeSummary?: Record<string, any>;
    endDate?: string;
    netIncome?: number;
    startDate?: string;
    totalExpenses?: number;
    totalIncome?: number;
  };

  type ReviewRequest = {
    id?: number;
    reviewMessage?: string;
    reviewStatus?: number;
  };

  type User = {
    createTime?: string;
    id?: number;
    isDelete?: number;
    mpOpenId?: string;
    unionId?: string;
    updateTime?: string;
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userPassword?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserAddRequest = {
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userRole?: string;
  };

  type UserLoginRequest = {
    userAccount?: string;
    userPassword?: string;
  };

  type UserQueryRequest = {
    current?: number;
    id?: number;
    mpOpenId?: string;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    unionId?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserRegisterRequest = {
    checkPassword?: string;
    userAccount?: string;
    userPassword?: string;
  };

  type UserUpdateMyRequest = {
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
  };

  type UserUpdateRequest = {
    id?: number;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };
}
