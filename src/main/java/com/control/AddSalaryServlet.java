package com.control;
import com.dao.SalaryRecordDao;
import com.model.SalaryRecord;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigDecimal;

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
        BigDecimal personalIncomeTax = getBigDecimalParam(request, "personalIncomeTax");
        BigDecimal leaveDeduction = getBigDecimalParam(request, "leaveDeduction");
        BigDecimal actualSalary = baseSalary.add(positionAllowance).add(lunchAllowance)
                .add(overtimePay).add(fullAttendanceBonus)
                .subtract(socialInsurance).subtract(housingFund)
                .subtract(personalIncomeTax).subtract(leaveDeduction);

        SalaryRecordDao salaryDAO=new SalaryRecordDao();
        boolean isSuccess = salaryDAO.addSalary(staffName, departmentName, salaryMonth,
                baseSalary, positionAllowance, lunchAllowance, overtimePay,
                fullAttendanceBonus, socialInsurance, housingFund,
                personalIncomeTax, leaveDeduction, actualSalary);
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
}
