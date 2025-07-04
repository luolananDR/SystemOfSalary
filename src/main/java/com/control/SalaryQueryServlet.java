package com.control;

import com.dao.SalaryRecordDao;
import com.model.SalaryRecord;
import com.model.SalaryView;
import com.model.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import javax.swing.text.html.CSS;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SalaryQueryServlet")
public class SalaryQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute("userRole");
        String username = (String) session.getAttribute("username");
        request.setAttribute("role", role);
        request.setAttribute("username", username);
        SalaryRecordDao salaryDAO = new SalaryRecordDao();
        List<SalaryView> salary = salaryDAO.findAllWithStaffInfo();
        request.setAttribute("salary", salary);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/salaryManage.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String staffName = request.getParameter("staffName");
        String departmentName = request.getParameter("department");
        String startDate = request.getParameter("start");
        String endDate = request.getParameter("end");
        SalaryRecordDao salaryDAO = new SalaryRecordDao();
        List<SalaryView> salary = salaryDAO.FuzzyfindWithStaffInfo(staffName, departmentName, startDate, endDate);
        request.setAttribute("salary", salary);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/salaryManage.jsp");
        rd.forward(request, response);
    }
}
