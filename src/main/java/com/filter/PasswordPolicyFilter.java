package com.filter;
import com.model.SysUser;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@WebFilter("/index.jsp")
public class PasswordPolicyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();
        // 不需要过滤的路径

        if (session != null && session.getAttribute("user") != null) {
            SysUser user = (SysUser) session.getAttribute("user");
            Timestamp lastChange = user.getLastPasswordChange();
            if (lastChange != null && Duration.between(lastChange.toInstant(), Instant.now()).toDays() > 90) {
                session.setAttribute("errorMessage", "密码超过90天未更换，请重置密码");
                res.sendRedirect(req.getContextPath()+"/changePassword.jsp");
                return;
            }

        }
        chain.doFilter(request, response);
    }
}