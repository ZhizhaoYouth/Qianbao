package com.finplat.financialmanage.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class UserSessionService {

    private static final int MAX_CONCURRENT_DEVICES = 3; // 最大并发登录设备数
    private final ConcurrentHashMap<Long, List<HttpSession>> userSessions = new ConcurrentHashMap<>();

    /**
     * 用户登录，处理并发登录控制和自动下线逻辑
     *
     * @param userId  用户ID
     * @param request HttpServletRequest对象
     */
    public synchronized void loginUser(Long userId, HttpServletRequest request) {
        // 如果用户ID不存在于sessions中，则初始化一个空列表
        if (!userSessions.containsKey(userId)) {
            userSessions.put(userId, new ArrayList<>());
        }
        List<HttpSession> sessions = userSessions.get(userId);

        // 清理过期会话
        cleanupExpiredSessions(userId);

        // 添加当前会话
        HttpSession currentSession = request.getSession();
        sessions.add(currentSession);
        System.out.println("用户 " + userId + " 登录成功。会话 ID: " + currentSession.getId());

        // 如果超出最大并发设备数，则移除最早登录的设备
        if (sessions.size() > MAX_CONCURRENT_DEVICES) {
            sessions.sort(Comparator.comparing(HttpSession::getLastAccessedTime)); // 按登录时间排序
            HttpSession earliestSession = sessions.remove(0); // 移除最早登录的设备
            closeSession(userId, earliestSession); // 关闭会话
            System.out.println("用户 " + userId + " 超出最大并发设备数，关闭会话: " + earliestSession.getId());
        }
    }

    /**
     * 实际关闭会话操作
     *
     * @param userId  用户ID
     * @param session HttpSession对象
     */
    private void closeSession(Long userId, HttpSession session) {
        // 存储提示信息到会话属性中
        session.setAttribute("logoutReason", "您的账号在其他设备登录，当前设备被强制下线。");
        System.out.println("关闭会话: " + session.getId() + "，用户: " + userId);
        session.invalidate(); // 使会话无效
    }

    /**
     * 清理过期的会话
     *
     * @param userId 用户ID
     */
    private synchronized void cleanupExpiredSessions(Long userId) {
        List<HttpSession> sessions = userSessions.get(userId);
        if (sessions != null) {
            sessions.removeIf(this::isSessionExpired); // 使用removeIf安全删除过期会话
            System.out.println("清理用户 " + userId + " 的过期会话");
        }
    }

    /**
     * 检查会话是否已过期
     *
     * @param session HttpSession对象
     * @return true 如果会话已过期，否则 false
     */
    private boolean isSessionExpired(HttpSession session) {
        try {
            long maxInactiveInterval = session.getMaxInactiveInterval() * 1000;
            long lastAccessedTime = session.getLastAccessedTime();
            long now = System.currentTimeMillis();
            boolean expired = (now - lastAccessedTime) > maxInactiveInterval;
            if (expired) {
                System.out.println("会话 " + session.getId() + " 已过期。");
            }
            return expired;
        } catch (IllegalStateException e) {
            // Session is already invalidated
            return true;
        }
    }
}
