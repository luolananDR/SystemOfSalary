package com.control;

import com.dao.SysUserDao;
import com.filter.AuditLogFilter;
import com.model.SysUser;
import com.model.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password =(String)  request.getAttribute("encrypted_password");
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "用户名或密码不能为空");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        SysUserDao UserDao = new SysUserDao();
        SysUser user = UserDao.getUserByUsername(username);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        UserRole userRole = user != null ? user.getRole() : null;
        if (user != null) {
            // 先判断是否锁定中
            if ( user.getAccountLockedUntil() != null && user.getAccountLockedUntil().after(now)) {
                AuditLogFilter.log(request, "登录", "系统", "失败", "账号已锁定");
                request.setAttribute("errorMessage", "账号已锁定，请于 " + user.getAccountLockedUntil() + " 后再试");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }
            else {
                user.setIsLocked(false);
                if (password.equals(user.getPassword())) {
                    HttpSession session = request.getSession();
                    user.setFailedLoginCount(0);
                    user.setAccountLockedUntil(null);
                    session.setAttribute("user", user);
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("userRole", userRole);
                    session.setAttribute("lastAccessTime", now.getTime());
                    AuditLogFilter.log(request, "登录", "系统", "成功", "登录成功");
                    // 重定向到首页
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                    UserDao.updateUser(user);
                    return;
                }else{
                    int failCount = user.getFailedLoginCount() == null ? 1 : user.getFailedLoginCount() + 1;
                    user.setFailedLoginCount(failCount);
                    user.setLastFailedLoginTime(now);

                    if (failCount >= 5) {
                        // 锁定30分钟
                        user.setIsLocked(true);
                        user.setAccountLockedUntil(new Timestamp(now.getTime() + 30 * 60 * 1000));
                        request.setAttribute("errorMessage", "登录失败5次，账号已锁定30分钟");
                    } else {
                        AuditLogFilter.log(request, "登录", "系统", "失败", "用户名或密码错误，失败次数: " + failCount);
                        request.setAttribute("errorMessage", "用户名或密码错误，您已失败 " + failCount + " 次");
                    }
                }
            }
            UserDao.updateUser(user);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        else {
            AuditLogFilter.log(request, "登录", "系统", "失败", "用户不存在");
            request.setAttribute("errorMessage", "用户不存在");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
