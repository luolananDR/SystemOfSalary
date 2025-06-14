package com.dao;

import com.model.SpecialDeduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpecialDeductionDao extends BaseDao{
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



    public SpecialDeduction getByStaffCode(String staffCode) {
    String sql = "SELECT * FROM special_deduction WHERE staff_code = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, staffCode);
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

    public boolean update(SpecialDeduction deduction) {
        String sql = "UPDATE special_deduction SET child_education = ?, continue_education = ?, housing_loan_interest = ?, housing_rent = ?, elderly_support = ?, serious_illness = ?,created_at=? WHERE staff_code = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBigDecimal(1, deduction.getChildEducation());
            ps.setBigDecimal(2, deduction.getContinueEducation());
            ps.setBigDecimal(3, deduction.getHousingLoanInterest());
            ps.setBigDecimal(4, deduction.getHousingRent());
            ps.setBigDecimal(5, deduction.getElderlySupport());
            ps.setBigDecimal(6, deduction.getSeriousIllness());
            ps.setInt(8, deduction.getStaffCode());
            ps.setTimestamp(7, deduction.getCreatedAt());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean add(SpecialDeduction deduction) {
        String sql = "INSERT INTO special_deduction (staff_code, child_education, continue_education, housing_loan_interest, housing_rent, elderly_support, serious_illness, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, deduction.getStaffCode());
            ps.setBigDecimal(2, deduction.getChildEducation());
            ps.setBigDecimal(3, deduction.getContinueEducation());
            ps.setBigDecimal(4, deduction.getHousingLoanInterest());
            ps.setBigDecimal(5, deduction.getHousingRent());
            ps.setBigDecimal(6, deduction.getElderlySupport());
            ps.setBigDecimal(7, deduction.getSeriousIllness());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
