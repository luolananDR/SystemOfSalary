package com.control;

import com.dao.AuditLogDao;
import com.model.AuditLog;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/AuditLogQueryServlet")
public class AuditLogQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        AuditLogDao AuditLogDao = new AuditLogDao();
        ArrayList<AuditLog> logs = AuditLogDao.fingAllLogs();
        request.setAttribute("logs", logs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/auditLog.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String type = request.getParameter("type");
        String startDate = request.getParameter("start");
        String endDate = request.getParameter("end");
        AuditLogDao AuditLogDao = new AuditLogDao();
        ArrayList<AuditLog> logs = AuditLogDao.queryLogs(username, type, startDate, endDate);
        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/auditLog.jsp").forward(request, response);

    }
}
