package com.control;

import com.dao.DepartmentDao;
import com.dao.SalaryRecordDao;
import com.dao.StaffDao;
import com.model.Department;
import com.model.SalaryRecord;
import com.model.Staff;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/SalaryEditServlet")
public class SalaryEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));

        SalaryRecordDao salaryDao = new SalaryRecordDao();
        SalaryRecord salary = salaryDao.findById(id);

        StaffDao staffDao = new StaffDao();
        Staff staff = staffDao.findByStaffCode(salary.getStaffCode());

        DepartmentDao deptDao = new DepartmentDao();
        Department department = deptDao.findById(staff.getDepartmentId());

        request.setAttribute("salary", salary);
        request.setAttribute("staff", staff);
        request.setAttribute("department", department);

        request.getRequestDispatcher("/salaryEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String staffName = request.getParameter("staffName");
        String departmentName = request.getParameter("departmentName");
        String salaryMonth = request.getParameter("salaryMonth");
        BigDecimal baseSalary = getBigDecimalParam(request, "baseSalary");
        BigDecimal positionAllowance = getBigDecimalParam(request, "positionAllowance");
        BigDecimal lunchAllowance = getBigDecimalParam(request, "lunchAllowance");
        BigDecimal overtimePay = getBigDecimalParam(request, "overtimePay");
        BigDecimal fullAttendanceBonus = getBigDecimalParam(request, "fullAttendanceBonus");
        BigDecimal socialInsurance = getBigDecimalParam(request, "socialInsurance");
        BigDecimal housingFund = getBigDecimalParam(request, "housingFund");
        BigDecimal personalIncomeTax = getBigDecimalParam(request, "personalIncomeTax");
        BigDecimal leaveDeduction = getBigDecimalParam(request, "leaveDeduction");
        BigDecimal actualSalary = baseSalary.add(positionAllowance).add(lunchAllowance)
                .add(overtimePay).add(fullAttendanceBonus)
                .subtract(socialInsurance).subtract(housingFund)
                .subtract(personalIncomeTax).subtract(leaveDeduction);

        SalaryRecordDao salaryDAO=new SalaryRecordDao();
        boolean isSuccess = salaryDAO.updateSalary(staffName, departmentName, salaryMonth,
                baseSalary, positionAllowance, lunchAllowance, overtimePay,
                fullAttendanceBonus, socialInsurance, housingFund,
                personalIncomeTax, leaveDeduction, actualSalary);
        if(!isSuccess){
            request.setAttribute("msg", "修改失败！");
            request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
            return;
        }
        else {
            request.setAttribute("msg", "修改成功！");
            request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
            return;
        }
    }
    private BigDecimal getBigDecimalParam(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}
