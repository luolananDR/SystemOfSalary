package com.dao;

import com.model.SalaryRecord;
import com.model.SalaryView;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryRecordDao extends BaseDao{
    public boolean insert(SalaryRecord r) {
        String sql = "INSERT INTO salary_record (staff_code, salary_month, base_salary, position_allowance, " +
                "lunch_allowance, overtime_pay, full_attendance_bonus, social_insurance, housing_fund, " +
                "personal_income_tax, leave_deduction, actual_salary, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getStaffCode());
            ps.setString(2, r.getSalaryMonth().toString());
            ps.setBigDecimal(3, r.getBaseSalary());
            ps.setBigDecimal(4, r.getPositionAllowance());
            ps.setBigDecimal(5, r.getLunchAllowance());
            ps.setBigDecimal(6, r.getOvertimePay());
            ps.setBigDecimal(7, r.getFullAttendanceBonus());
            ps.setBigDecimal(8, r.getSocialInsurance());
            ps.setBigDecimal(9, r.getHousingFund());
            ps.setBigDecimal(10, r.getPersonalIncomeTax());
            ps.setBigDecimal(11, r.getLeaveDeduction());
            ps.setBigDecimal(12, r.getActualSalary());
            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<SalaryView> findAllWithStaffInfo() {
        List<SalaryView> list = new ArrayList<>();
        String sql = "SELECT sr.*, s.name AS staff_name, d.name AS department_name " +
                "FROM salary_record sr " +
                "JOIN staff s ON sr.staff_code = s.staff_code " +
                "JOIN department d ON s.department_id = d.id " +
                "ORDER BY sr.salary_month DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SalaryView view = new SalaryView();
                view.setId(rs.getInt("id"));
                view.setStaffCode(rs.getInt("staff_code"));
                view.setStaffName(rs.getString("staff_name"));
                view.setDepartment(rs.getString("department_name"));
                view.setSalaryMonth(rs.getDate("salary_month"));
                view.setBaseSalary(rs.getBigDecimal("base_salary"));
                view.setPositionAllowance(rs.getBigDecimal("position_allowance"));
                view.setLunchAllowance(rs.getBigDecimal("lunch_allowance"));
                view.setOvertimePay(rs.getBigDecimal("overtime_pay"));
                view.setFullAttendanceBonus(rs.getBigDecimal("full_attendance_bonus"));
                view.setSocialInsurance(rs.getBigDecimal("social_insurance"));
                view.setHousingFund(rs.getBigDecimal("housing_fund"));
                view.setPersonalIncomeTax(rs.getBigDecimal("personal_income_tax"));
                view.setLeaveDeduction(rs.getBigDecimal("leave_deduction"));
                view.setActualSalary(rs.getBigDecimal("actual_salary"));

                list.add(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<SalaryView> FuzzyfindWithStaffInfo(String staffName, String departmentName, String startDate, String endDate) {
        List<SalaryView> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT sr.*, s.name AS staff_name, d.name AS department_name " +
                        "FROM salary_record sr " +
                        "JOIN staff s ON sr.staff_code = s.staff_code " +
                        "JOIN department d ON s.department_id = d.id " +
                        "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        // 动态拼接条件
        if (staffName != null && !staffName.trim().isEmpty()) {
            sql.append("AND s.name LIKE ? ");
            params.add("%" + staffName.trim() + "%");
        }
        if (departmentName != null && !departmentName.trim().isEmpty()) {
            sql.append("AND d.name LIKE ? ");
            params.add("%" + departmentName.trim() + "%");
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append("AND sr.salary_month >= ? ");
            params.add(startDate.trim());
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append("AND sr.salary_month <= ? ");
            params.add(endDate.trim());
        }

        sql.append("ORDER BY sr.salary_month DESC");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // 设置动态参数
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SalaryView view = new SalaryView();
                    view.setId(rs.getInt("id"));
                    view.setStaffCode(rs.getInt("staff_code"));
                    view.setStaffName(rs.getString("staff_name"));
                    view.setDepartment(rs.getString("department_name"));
                    view.setSalaryMonth(rs.getDate("salary_month"));
                    view.setBaseSalary(rs.getBigDecimal("base_salary"));
                    view.setPositionAllowance(rs.getBigDecimal("position_allowance"));
                    view.setLunchAllowance(rs.getBigDecimal("lunch_allowance"));
                    view.setOvertimePay(rs.getBigDecimal("overtime_pay"));
                    view.setFullAttendanceBonus(rs.getBigDecimal("full_attendance_bonus"));
                    view.setSocialInsurance(rs.getBigDecimal("social_insurance"));
                    view.setHousingFund(rs.getBigDecimal("housing_fund"));
                    view.setPersonalIncomeTax(rs.getBigDecimal("personal_income_tax"));
                    view.setLeaveDeduction(rs.getBigDecimal("leave_deduction"));
                    view.setActualSalary(rs.getBigDecimal("actual_salary"));

                    list.add(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public SalaryRecord findById(int id) {
        String sql = "SELECT * FROM salary_record WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SalaryRecord record = new SalaryRecord();
                    record.setId(rs.getInt("id"));
                    record.setStaffCode(rs.getInt("staff_code"));
                    record.setSalaryMonth(Date.valueOf(rs.getDate("salary_month").toLocalDate()));
                    record.setBaseSalary(rs.getBigDecimal("base_salary"));
                    record.setPositionAllowance(rs.getBigDecimal("position_allowance"));
                    record.setLunchAllowance(rs.getBigDecimal("lunch_allowance"));
                    record.setOvertimePay(rs.getBigDecimal("overtime_pay"));
                    record.setFullAttendanceBonus(rs.getBigDecimal("full_attendance_bonus"));
                    record.setSocialInsurance(rs.getBigDecimal("social_insurance"));
                    record.setHousingFund(rs.getBigDecimal("housing_fund"));
                    record.setPersonalIncomeTax(rs.getBigDecimal("personal_income_tax"));
                    record.setLeaveDeduction(rs.getBigDecimal("leave_deduction"));
                    record.setActualSalary(rs.getBigDecimal("actual_salary"));
                    return record;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;}
        return null;
    }



    public SalaryRecord getByStaffCode(String staffCodeStr) {
        String sql = "SELECT * FROM salary_record WHERE staff_code = ? ";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(staffCodeStr));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SalaryRecord record = new SalaryRecord();
                    record.setId(rs.getInt("id"));
                    record.setStaffCode(rs.getInt("staff_code"));
                    record.setSalaryMonth(Date.valueOf(rs.getDate("salary_month").toLocalDate()));
                    record.setBaseSalary(rs.getBigDecimal("base_salary"));
                    record.setPositionAllowance(rs.getBigDecimal("position_allowance"));
                    record.setLunchAllowance(rs.getBigDecimal("lunch_allowance"));
                    record.setOvertimePay(rs.getBigDecimal("overtime_pay"));
                    record.setFullAttendanceBonus(rs.getBigDecimal("full_attendance_bonus"));
                    record.setSocialInsurance(rs.getBigDecimal("social_insurance"));
                    record.setHousingFund(rs.getBigDecimal("housing_fund"));
                    record.setPersonalIncomeTax(rs.getBigDecimal("personal_income_tax"));
                    record.setLeaveDeduction(rs.getBigDecimal("leave_deduction"));
                    record.setActualSalary(rs.getBigDecimal("actual_salary"));
                    return record;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        return null;
        }
        return null;
    }


    public boolean updateSalary(String staffCode, String salaryMonth, BigDecimal baseSalary, BigDecimal positionAllowance, BigDecimal lunchAllowance, BigDecimal overtimePay, BigDecimal fullAttendanceBonus, BigDecimal socialInsurance, BigDecimal housingFund, BigDecimal personalIncomeTax, BigDecimal leaveDeduction, BigDecimal actualSalary) {
        String sql = "UPDATE salary_record SET base_salary = ?, position_allowance = ?, lunch_allowance = ?, " +
                "overtime_pay = ?, full_attendance_bonus = ?, social_insurance = ?, housing_fund = ?, " +
                "personal_income_tax = ?, leave_deduction = ?, actual_salary = ? " +
                "WHERE staff_code = ? AND salary_month = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps= conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, baseSalary);
            ps.setBigDecimal(2, positionAllowance);
            ps.setBigDecimal(3, lunchAllowance);
            ps.setBigDecimal(4, overtimePay);
            ps.setBigDecimal(5, fullAttendanceBonus);
            ps.setBigDecimal(6, socialInsurance);
            ps.setBigDecimal(7, housingFund);
            ps.setBigDecimal(8, personalIncomeTax);
            ps.setBigDecimal(9, leaveDeduction);
            ps.setBigDecimal(10, actualSalary);
            ps.setInt(11, Integer.parseInt(staffCode));
            ps.setString(12, salaryMonth);

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
           return false;
        }
    }
}
