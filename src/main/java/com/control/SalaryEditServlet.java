package com.control;

import com.dao.DepartmentDao;
import com.dao.SalaryRecordDao;
import com.dao.SpecialDeductionDao;
import com.dao.StaffDao;
import com.model.Department;
import com.model.SalaryRecord;
import com.model.SpecialDeduction;
import com.model.Staff;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
        String staffCode = request.getParameter("staffCode");
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
        BigDecimal leaveDeduction = getBigDecimalParam(request, "leaveDeduction");


        // 2. 计算应发工资
        BigDecimal totalSalary = baseSalary
                .add(positionAllowance)
                .add(lunchAllowance)
                .add(overtimePay)
                .add(fullAttendanceBonus);

        // 3. 获取专项附加扣除
        SpecialDeductionDao deductionDao = new SpecialDeductionDao();
        SpecialDeduction deduction = deductionDao.getByStaffCode(staffCode);
        BigDecimal specialDeduction = (deduction != null) ? deduction.getTotalDeduction() : BigDecimal.ZERO;

        // 4. 应纳税所得额
        BigDecimal taxableIncome = totalSalary
                .subtract(socialInsurance)
                .subtract(housingFund)
                .subtract(BigDecimal.valueOf(5000))
                .subtract(specialDeduction);

        if (taxableIncome.compareTo(BigDecimal.ZERO) < 0) {
            taxableIncome = BigDecimal.ZERO;
        }

        // 5. 个税
        BigDecimal personalIncomeTax = calculateMonthlyTax(taxableIncome);

        // 6. 实发工资
        BigDecimal actualSalary = totalSalary
                .subtract(socialInsurance)
                .subtract(housingFund)
                .subtract(personalIncomeTax)
                .subtract(leaveDeduction);

        SalaryRecordDao salaryDAO = new SalaryRecordDao();
        boolean isSuccess = salaryDAO.updateSalary(staffCode,
                staffName, salaryMonth,
                baseSalary, positionAllowance, lunchAllowance,
                overtimePay, fullAttendanceBonus, socialInsurance,
                housingFund, personalIncomeTax, leaveDeduction, actualSalary);

        request.setAttribute("msg", isSuccess ? "修改成功！" : "修改失败！");
        request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
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
    private BigDecimal calculateMonthlyTax(BigDecimal taxableIncome) {
        BigDecimal tax;
        double income = taxableIncome.doubleValue();

        if (income <= 3000) {
            tax = BigDecimal.valueOf(income * 0.03);
        } else if (income <= 12000) {
            tax = BigDecimal.valueOf(income * 0.10 - 210);
        } else if (income <= 25000) {
            tax = BigDecimal.valueOf(income * 0.20 - 1410);
        } else if (income <= 35000) {
            tax = BigDecimal.valueOf(income * 0.25 - 2660);
        } else if (income <= 55000) {
            tax = BigDecimal.valueOf(income * 0.30 - 4420);
        } else if (income <= 80000) {
            tax = BigDecimal.valueOf(income * 0.35 - 7120);
        } else {
            tax = BigDecimal.valueOf(income * 0.45 - 15320);
        }

        return tax.setScale(2, RoundingMode.HALF_UP);
    }
}
