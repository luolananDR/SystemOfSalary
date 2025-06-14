package com.dao;

import com.model.SpecialDeduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpecialDeductionDao extends BaseDao{
    public boolean addSpecialDeduction(SpecialDeduction specialDeduction) {
        String sql = "INSERT INTO special_deduction (staff_code, child_education, continue_education, housing_loan_interest, housing_rent, elderly_support, serious_illness, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, specialDeduction.getStaffCode());
            ps.setBigDecimal(2, specialDeduction.getChildEducation());
            ps.setBigDecimal(3, specialDeduction.getContinueEducation());
            ps.setBigDecimal(4, specialDeduction.getHousingLoanInterest());
            ps.setBigDecimal(5, specialDeduction.getHousingRent());
            ps.setBigDecimal(6, specialDeduction.getElderlySupport());
            ps.setBigDecimal(7, specialDeduction.getSeriousIllness());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public SpecialDeduction getspecialDeductionBystaffCode(Integer staffCode) {
        String sql = "SELECT * FROM special_deduction WHERE staff_code = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, staffCode);
            var rs = ps.executeQuery();
            if (rs.next()) {
                SpecialDeduction deduction = new SpecialDeduction();
                deduction.setId(rs.getInt("id"));
                deduction.setStaffCode(rs.getInt("staff_code"));
                deduction.setChildEducation(rs.getBigDecimal("child_education"));
                deduction.setContinueEducation(rs.getBigDecimal("continue_education"));
                deduction.setHousingLoanInterest(rs.getBigDecimal("housing_loan_interest"));
                deduction.setHousingRent(rs.getBigDecimal("housing_rent"));
                deduction.setElderlySupport(rs.getBigDecimal("elderly_support"));
                deduction.setSeriousIllness(rs.getBigDecimal("serious_illness"));
                deduction.setCreatedAt(rs.getTimestamp("created_at"));
                return deduction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SpecialDeduction getspecialDeductionBystaffName(String staffName) {
    String sql = "SELECT sd.* FROM special_deduction sd JOIN staff s ON sd.staff_code = s.staff_code WHERE s.name = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, staffName);
            var rs = ps.executeQuery();
            if (rs.next()) {
                SpecialDeduction deduction = new SpecialDeduction();
                deduction.setId(rs.getInt("id"));
                deduction.setStaffCode(rs.getInt("staff_code"));
                deduction.setChildEducation(rs.getBigDecimal("child_education"));
                deduction.setContinueEducation(rs.getBigDecimal("continue_education"));
                deduction.setHousingLoanInterest(rs.getBigDecimal("housing_loan_interest"));
                deduction.setHousingRent(rs.getBigDecimal("housing_rent"));
                deduction.setElderlySupport(rs.getBigDecimal("elderly_support"));
                deduction.setSeriousIllness(rs.getBigDecimal("serious_illness"));
                deduction.setCreatedAt(rs.getTimestamp("created_at"));
                return deduction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
