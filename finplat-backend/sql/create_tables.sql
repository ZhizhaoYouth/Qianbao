-- 数据库初始化

-- 创建库
create database if not exists finplat;

-- 切换库
use finplat;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 账户表
create table if not exists account
(
    id              bigint auto_increment comment 'id' primary key,
    accountName         varchar(128)                       not null comment '账户名',
    accountNumber         varchar(256)                      null comment '账户号码',
    accountType         tinyint  default 0                 not null comment '账户类型（0-信用卡，1-储蓄卡，2-电子钱包）',
    bankName         varchar(64)            default '无'          not null comment '开户行名称',
    balance         decimal(18,2)        default 0.00      not null comment'账户余额', 
    reviewStatus    int      default 0                 not null comment '审核状态：0-待审核, 1-通过, 2-拒绝',
    reviewMessage   varchar(512)                       null comment '审核信息',
    reviewerId      bigint                             null comment '审核人 id',
    reviewTime      datetime                           null comment '审核时间',
    userId          bigint                             not null comment '创建用户 id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除',
    index idx_accountName (accountName),
    index idx_userId (userId)
)comment '账户' collate = utf8mb4_unicode_ci;

-- 财务记录表
create table if not exists financial_record
(
    id              bigint auto_increment comment 'id' primary key,
    transactionDesc  text                               null comment '交易描述',
    accountId           bigint                             not null comment '账户 id',
    userId          bigint                             not null comment '创建用户 id',
    amount  decimal(18,2) not null comment '交易金额',
    transactionType    tinyint   default 0  not null comment '交易类型（0-收入，1-支出）',
    category  varchar(255) null comment '交易类别（如餐饮、娱乐）',
    transactionTime      datetime  not null comment '交易时间',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除',
    index idx_accountId (accountId),
    index idx_userId (userId)
) comment '财务记录' collate = utf8mb4_unicode_ci;

-- 预算表
create table if not exists budget
(
    id              bigint auto_increment comment 'id' primary key,
    budgetDesc  text                            null comment '预算描述',
    accountId           bigint                             not null comment '账户 id',
    userId          bigint                             not null comment '创建用户 id',
    amount  decimal(18,2) not null comment '预算金额',
    category  varchar(255) null comment '预算类别（如餐饮、娱乐）',
    startDate datetime default CURRENT_TIMESTAMP not null comment '预算开始时间',
    endDate datetime default CURRENT_TIMESTAMP not null comment '预算结束时间',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除',
    index idx_accountId (accountId),
    index idx_userId (userId),
    index idx_category (category)
)comment '预算' collate = utf8mb4_unicode_ci;

-- 登陆日志表
create table if not exists login_log
(
    id              bigint auto_increment comment 'id' primary key,
    userId          bigint                             not null comment '用户 id',
    loginTime       datetime default CURRENT_TIMESTAMP      not null comment '登录时间',
    deviceType      varchar(50)                             not null comment '设备类型：PC、手机、平板等',
    ipAddress       varchar(45)                             not null comment '登录 IP 地址',
    location        varchar(256)                            null comment '登录地点',
    browserInfo     varchar(256)                            null comment '浏览器信息',
    operatingSystem varchar(256)                            null comment '操作系统',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '登录日志' collate = utf8mb4_unicode_ci;

-- 预算类别表
CREATE TABLE IF NOT EXISTS budget_category (
    id            bigint auto_increment comment 'id' primary key,
    userId        bigint       not null comment '创建用户 id',
    categoryName  varchar(256) not null comment '预算类别名称',
    isDefault     tinyint(1)   default 0 not null comment '是否为系统默认类别,1表示是默认类别',
    createTime      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete        tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId),
    unique key uk_user_category (userId, categoryName) where isDelete=0
) comment '预算类别表' collate = utf8mb4_unicode_ci;
