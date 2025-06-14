package com.control;

import com.dao.SysUserDao;
import com.model.SysUser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        SysUser currentUser = (SysUser) session.getAttribute("user");
        String password =(String)  request.getAttribute("encrypted_password");
        currentUser.setPassword(password);
        currentUser.setLastPasswordChange(new Timestamp(System.currentTimeMillis()));
        SysUserDao userDao = new SysUserDao();
        try {
            if (userDao.updateUser(currentUser)) {
                session.setAttribute("user", currentUser);
                request.setAttribute("successMessage", "密码修改成功，请重新登录");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "密码修改失败，请稍后再试");
                request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "发生错误，请稍后再试");
            request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
        }
    }
}
