package com.filter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
@WebFilter("/*") // 拦截所有请求
public class SessionTimeoutFilter implements Filter {
    // 超时时间（毫秒）: 30分钟
    private static final long TIMEOUT = //1000;
                                         30 * 60 * 1000;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // 排除登录页面、静态资源等
        if (uri.contains("login.jsp") || uri.contains("LoginServlet")|| uri.contains("RegisterServlet") || uri.contains("ChangePasswordServlet")|| uri.contains("register.jsp")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null ) {
            // 未登录
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        Long lastAccessTime = (Long) session.getAttribute("lastAccessTime");
        long now = System.currentTimeMillis();

        if (lastAccessTime != null && now - lastAccessTime > TIMEOUT) {
            // 超时：销毁会话并重定向
            session.invalidate();
            res.sendRedirect(req.getContextPath() + "/login.jsp?timeout=true");
            return;
        }

        // 更新操作时间
        session.setAttribute("lastAccessTime", now);
        chain.doFilter(request, response);
    }
}