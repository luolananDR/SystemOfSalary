package com.control;
import com.dao.SalaryRecordDao;
import com.dao.SpecialDeductionDao;
import com.model.SalaryRecord;
import com.model.SpecialDeduction;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@WebServlet("/AddSalaryServlet")
public class AddSalaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String staffName = request.getParameter("staffName");
        String departmentName = request.getParameter("department");
        String salaryMonth = request.getParameter("salaryMonth");
        BigDecimal baseSalary = getBigDecimalParam(request, "baseSalary");
        BigDecimal positionAllowance = getBigDecimalParam(request, "positionAllowance");
        BigDecimal lunchAllowance = getBigDecimalParam(request, "lunchAllowance");
        BigDecimal overtimePay = getBigDecimalParam(request, "overtimePay");
        BigDecimal fullAttendanceBonus = getBigDecimalParam(request, "fullAttendanceBonus");
        BigDecimal socialInsurance = getBigDecimalParam(request, "socialInsurance");
        BigDecimal housingFund = getBigDecimalParam(request, "housingFund");
        BigDecimal leaveDeduction = getBigDecimalParam(request, "leaveDeduction");
        // 应发工资
        BigDecimal totalSalary = baseSalary
                .add(positionAllowance)
                .add(lunchAllowance)
                .add(overtimePay)
                .add(fullAttendanceBonus);

        // 获取专项附加扣除
        SpecialDeductionDao specialDeductionDao = new SpecialDeductionDao();
        SpecialDeduction deduction = specialDeductionDao.getspecialDeductionBystaffName(staffName);
        BigDecimal specialDeduction = deduction.getTotalDeduction();

        // 应纳税所得额 = 应发工资 - 社保 - 公积金 - 起征点(5000) - 专项附加扣除
        BigDecimal taxableIncome = totalSalary
                .subtract(socialInsurance)
                .subtract(housingFund)
                .subtract(BigDecimal.valueOf(5000))
                .subtract(specialDeduction);

        if (taxableIncome.compareTo(BigDecimal.ZERO) < 0) {
            taxableIncome = BigDecimal.ZERO;
        }

        // 计算个税
        BigDecimal incomeTax = calculateMonthlyTax(taxableIncome);

        // 实发工资 = 应发工资 - 社保 - 公积金 - 个税 - 请假扣款
        BigDecimal actualSalary = totalSalary
                .subtract(socialInsurance)
                .subtract(housingFund)
                .subtract(incomeTax)
                .subtract(leaveDeduction);


        SalaryRecordDao salaryDAO=new SalaryRecordDao();
        boolean isSuccess = salaryDAO.addSalary(staffName, departmentName, salaryMonth,
                baseSalary, positionAllowance, lunchAllowance, overtimePay,
                fullAttendanceBonus, socialInsurance, housingFund,
                incomeTax, leaveDeduction, actualSalary);
        if(!isSuccess){
            request.setAttribute("msg", "录入失败！");
            request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
            return;
        }
        else {
            request.setAttribute("msg", "录入成功！");
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
