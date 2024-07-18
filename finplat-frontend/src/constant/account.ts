// 审核状态枚举
export const REVIEW_STATUS_ENUM = {
  // 待审核
  REVIEWING: 0,
  // 通过
  PASS: 1,
  // 拒绝
  REJECT: 2,
};

// 审核状态映射
export const REVIEW_STATUS_MAP = {
  0: "待审核",
  1: "通过",
  2: "拒绝",
};

// 审核状态映射
export const ACCOUNT_TYPE_MAP = {
  0: "信用卡",
  1: "储蓄卡",
  2: "电子钱包",
};

// 交易类型
export const RECORD_TYPE_MAP = {
  0: "收入",
  1: "支出",
};
// 交易类型是否默认
export const IS_DEFAULT_MAP = {
  0: "自定义类别",
  1: "系统默认",
};
