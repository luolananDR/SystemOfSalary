package com.control;

import com.dao.FamilyMemberDao;
import com.dao.SalaryRecordDao;
import com.dao.SpecialDeductionDao;
import com.dao.StaffDao;
import com.filter.AuditLogFilter;
import com.model.FamilyMember;
import com.model.SalaryRecord;
import com.model.SpecialDeduction;
import com.model.Staff;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/SpecialDedutionImportServlet")
public class SpecialDedutionImportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
        AuditLogFilter.log(request, "查询", "个人专项附加扣除信息", "成功", "通过过滤器记录");
        String staffCode = request.getParameter("staffCode");
        FamilyMemberDao familyDao = new FamilyMemberDao();
        SpecialDeductionDao deductionDao = new SpecialDeductionDao();
        StaffDao staffDao = new StaffDao();
        SpecialDeduction deduction = deductionDao.getByStaffCode(staffCode);
        if (deduction == null) {
            deduction = new SpecialDeduction();
            deduction.setStaffCode(Integer.valueOf(staffCode));
        }
        List<FamilyMember> familyMembers = familyDao.getByStaffCode(staffCode);
        BigDecimal childEducation = BigDecimal.ZERO;
        BigDecimal elderlySupport = BigDecimal.ZERO;
        BigDecimal seriousIllness = BigDecimal.ZERO;
        for (FamilyMember member : familyMembers) {
            if ("子女".equals(member.getRelation()) && Boolean.TRUE.equals(member.getIsStudent())) {
                childEducation = childEducation.add(BigDecimal.valueOf(1000));
            }
            if (("父亲".equals(member.getRelation()) || "母亲".equals(member.getRelation()))) {
                elderlySupport = elderlySupport.add(BigDecimal.valueOf(1000));
            }
            if (Boolean.TRUE.equals(member.getIsMajorDisease())) {
                seriousIllness = seriousIllness.add(BigDecimal.valueOf(1000));
            }

        }
        deduction.setChildEducation(childEducation);
        deduction.setElderlySupport(elderlySupport);
        deduction.setSeriousIllness(seriousIllness);
        Staff staff = staffDao.findByStaffCode(Integer.valueOf(staffCode));
        request.setAttribute("deduction", deduction);
        request.setAttribute("staff", staff);
        request.getRequestDispatcher("/specialDeduction.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffCode = request.getParameter("staffCode");
        AuditLogFilter.log(request, "修改", "个人专项附加扣除信息", "成功", "通过过滤器记录");
        BigDecimal childEducation = getBigDecimalParam(request, "childEducation");
       String continueEducation = request.getParameter("continueEducation");
       String housingLoanInterest = request.getParameter("housingLoanInterest");
        String housingRent = request.getParameter("housingRent");
        BigDecimal elderlySupport =getBigDecimalParam(request, "elderlySupport");
        BigDecimal seriousIllness =getBigDecimalParam(request, "seriousIllness");

        SpecialDeductionDao deductionDao = new SpecialDeductionDao();
        SpecialDeduction deduction = deductionDao.getByStaffCode(staffCode);
        if (deduction == null) {
            deduction = new SpecialDeduction();
            deduction.setStaffCode(Integer.valueOf(staffCode));
            deduction.setChildEducation(childEducation);
            if("有".equals(continueEducation)) {
                deduction.setContinueEducation(BigDecimal.valueOf(0));
            } else {
                deduction.setContinueEducation(BigDecimal.valueOf(400));
            }

            if("无".equals(housingLoanInterest)) {
                deduction.setHousingLoanInterest(BigDecimal.valueOf(0));
            } else {
                deduction.setHousingLoanInterest(BigDecimal.valueOf(1000));
            }

            if("无".equals(housingRent)) {
                deduction.setHousingRent(BigDecimal.valueOf(0));
            } else {
                deduction.setHousingRent(BigDecimal.valueOf(1500));
            }
            deduction.setElderlySupport(elderlySupport);
            deduction.setSeriousIllness(seriousIllness);
            deduction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            if (deductionDao.add(deduction)) {
                AuditLogFilter.log(request, "添加", "个人专项附加扣除信息", "成功", "通过过滤器记录");
                SalaryRecordDao salaryDAO = new SalaryRecordDao();
                SalaryRecord salaryRecord = salaryDAO.getByStaffCode(staffCode);
                String salaryMonth = String.valueOf(salaryRecord.getSalaryMonth());
                BigDecimal baseSalary = salaryRecord.getBaseSalary();
                BigDecimal positionAllowance = salaryRecord.getPositionAllowance();
                BigDecimal lunchAllowance = salaryRecord.getLunchAllowance();
                BigDecimal overtimePay = salaryRecord.getOvertimePay();
                BigDecimal fullAttendanceBonus = salaryRecord.getFullAttendanceBonus();
                BigDecimal socialInsurance = salaryRecord.getSocialInsurance();
                BigDecimal housingFund = salaryRecord.getHousingFund();
                BigDecimal leaveDeduction = salaryRecord.getLeaveDeduction();
                // 2. 计算应发工资
                BigDecimal totalSalary = baseSalary
                        .add(positionAllowance)
                        .add(lunchAllowance)
                        .add(overtimePay)
                        .add(fullAttendanceBonus);

                // 3. 获取专项附加扣除

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

                boolean isSuccess = salaryDAO.updateSalary(staffCode,
                        salaryMonth,
                        baseSalary, positionAllowance, lunchAllowance,
                        overtimePay, fullAttendanceBonus, socialInsurance,
                        housingFund, personalIncomeTax, leaveDeduction, actualSalary);

                request.setAttribute("msg", "个人专项附加扣除保存成功！");
                request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
                return;
            } else {
                AuditLogFilter.log(request, "添加", "个人专项附加扣除信息", "失败", "通过过滤器记录");
                request.setAttribute("msg", "个人专项附加扣除保存失败，请稍后再试！");
                request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
                return;
            }
        }
        else{
            deduction.setChildEducation(childEducation);
            if("有".equals(continueEducation)) {
                deduction.setContinueEducation(BigDecimal.valueOf(0));
            } else {
                deduction.setContinueEducation(BigDecimal.valueOf(400));
            }

            if("无".equals(housingLoanInterest)) {
                deduction.setHousingLoanInterest(BigDecimal.valueOf(0));
            } else {
                deduction.setHousingLoanInterest(BigDecimal.valueOf(1000));
            }

            if("无".equals(housingRent)) {
                deduction.setHousingRent(BigDecimal.valueOf(0));
            } else {
                deduction.setHousingRent(BigDecimal.valueOf(1500));
            }
            deduction.setElderlySupport(elderlySupport);
            deduction.setSeriousIllness(seriousIllness);
            deduction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            if (deductionDao.update(deduction)) {
                SalaryRecordDao salaryDAO = new SalaryRecordDao();
                SalaryRecord salaryRecord = salaryDAO.getByStaffCode(staffCode);
                String salaryMonth = String.valueOf(salaryRecord.getSalaryMonth());
                BigDecimal baseSalary = salaryRecord.getBaseSalary();
                BigDecimal positionAllowance = salaryRecord.getPositionAllowance();
                BigDecimal lunchAllowance = salaryRecord.getLunchAllowance();
                BigDecimal overtimePay = salaryRecord.getOvertimePay();
                BigDecimal fullAttendanceBonus = salaryRecord.getFullAttendanceBonus();
                BigDecimal socialInsurance = salaryRecord.getSocialInsurance();
                BigDecimal housingFund = salaryRecord.getHousingFund();
                BigDecimal leaveDeduction = salaryRecord.getLeaveDeduction();
                // 2. 计算应发工资
                BigDecimal totalSalary = baseSalary
                        .add(positionAllowance)
                        .add(lunchAllowance)
                        .add(overtimePay)
                        .add(fullAttendanceBonus);

                // 3. 获取专项附加扣除

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

                boolean isSuccess = salaryDAO.updateSalary(staffCode,
                        salaryMonth,
                        baseSalary, positionAllowance, lunchAllowance,
                        overtimePay, fullAttendanceBonus, socialInsurance,
                        housingFund, personalIncomeTax, leaveDeduction, actualSalary);
                request.setAttribute("msg", "个人专项附加扣除更新成功！");
                request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("msg", "个人专项附加扣除更新失败，请稍后再试！");
                request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
                return;
            }
        }


    }
    private BigDecimal getBigDecimalParam(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        if (param == null || param.trim().isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(param.trim());
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
