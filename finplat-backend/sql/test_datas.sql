use finplat;
INSERT INTO user (userAccount, userPassword, unionId, mpOpenId, userName, userAvatar, userProfile, userRole)
VALUES
    ('user1', 'password1', 'union1', 'mpOpenId1', '用户1', 'avatar1.jpg', '简介1', 'user'),
    ('user2', 'password2', 'union2', 'mpOpenId2', '用户2', 'avatar2.jpg', '简介2', 'user'),
    ('admin', 'adminpassword', 'union3', 'mpOpenId3', '管理员', 'avatar3.jpg', '简介3', 'admin'),
    ('user3', 'password3', 'union4', 'mpOpenId4', '用户3', 'avatar4.jpg', '简介4', 'user'),
    ('bannedUser', 'password4', 'union5', 'mpOpenId5', '用户4', 'avatar5.jpg', '简介5', 'ban');

INSERT INTO account (accountName, accountNumber, accountType, bankName, balance, reviewStatus, reviewMessage, reviewerId, userId)
VALUES
    ('信用卡账户1', '1234567890', 0, '中国银行', 5000.00, 1, '审核通过', 1, 1),
    ('储蓄卡账户1', '2345678901', 1, '工商银行', 8000.00, 1, '审核通过', 2, 2),
    ('电子钱包1', '3456789012', 2, '支付宝', 3000.00, 1, '审核通过', 1, 3),
    ('信用卡账户2', '4567890123', 0, '农业银行', 2000.00, 1, '审核通过', 2, 1),
    ('储蓄卡账户2', '5678901234', 1, '建设银行', 10000.00, 1, '审核通过', 1, 2);

INSERT INTO financial_record (transactionDesc, accountId, userId, amount, transactionType, category, transactionTime)
VALUES
    ('餐饮消费', 1, 1, 200.00, 1, '餐饮', '2024-07-01 12:00:00'),
    ('工资收入', 2, 2, 8000.00, 0, '工资', '2024-07-01 09:00:00'),
    ('购物消费', 3, 3, 500.00, 1, '购物', '2024-07-01 15:00:00'),
    ('转账收入', 4, 1, 1500.00, 0, '转账', '2024-07-01 10:00:00'),
    ('娱乐消费', 5, 2, 300.00, 1, '娱乐', '2024-07-01 20:00:00');

INSERT INTO budget (budgetDesc, accountId, userId, amount, category, startDate, endDate)
VALUES
    ('本月餐饮预算', 1, 1, 1000.00, '餐饮', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 MONTH)),
    ('本月娱乐预算', 2, 2, 500.00, '娱乐', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 MONTH)),
    ('本月购物预算', 3, 3, 800.00, '购物', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 MONTH)),
    ('本月交通预算', 4, 1, 300.00, '交通', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 MONTH)),
    ('本月学习预算', 5, 2, 200.00, '学习', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 MONTH));

INSERT INTO login_log (userId, loginTime, deviceType, ipAddress, location, browserInfo, operatingSystem)
VALUES
    (1, '2024-07-01 08:00:00', 'PC', '192.168.1.1', '北京', 'Chrome', 'Windows 10'),
    (2, '2024-07-01 09:00:00', '手机', '192.168.1.2', '上海', 'Safari', 'iOS'),
    (3, '2024-07-01 10:00:00', '平板', '192.168.1.3', '广州', 'Firefox', 'Android'),
    (1, '2024-07-01 11:00:00', 'PC', '192.168.1.4', '深圳', 'Edge', 'Windows 10'),
    (2, '2024-07-01 12:00:00', '手机', '192.168.1.5', '杭州', 'Safari', 'iOS');

INSERT INTO budget_category (userId, categoryName, isDefault)
VALUES
    (1, '餐饮', 1),
    (1, '娱乐', 1),
    (2, '购物', 1),
    (2, '交通', 1),
    (3, '学习', 1),
    (1, '健康', 0),
    (2, '旅行', 0),
    (3, '教育', 0),
    (1, '宠物', 0),
    (2, '房租', 0);
