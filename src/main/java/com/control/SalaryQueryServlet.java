package com.control;

import com.dao.SalaryRecordDao;
import com.model.SalaryRecord;
import com.model.SalaryView;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SalaryQueryServlet")
public class SalaryQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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
        String salaryMonth = request.getParameter("salaryMonth");
        SalaryRecordDao salaryDAO = new SalaryRecordDao();
        List<SalaryView> salary = salaryDAO.FuzzyfindWithStaffInfo(staffName, departmentName, salaryMonth);
        request.setAttribute("salary", salary);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/salaryManage.jsp");
        rd.forward(request, response);
    }
}
