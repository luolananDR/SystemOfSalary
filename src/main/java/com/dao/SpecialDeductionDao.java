package com.dao;

import com.model.SpecialDeduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpecialDeductionDao extends BaseDao{
    public boolean addSpecialDeduction(SpecialDeduction specialDeduction) {
        String sql = "INSERT INTO special_deduction (staff_code, deduction_month, child_education, continue_education, housing_loan_interest, housing_rent, elderly_support, serious_illness, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        try(Connection cn=dataSource.getConnection();
            PreparedStatement ps=cn.prepareStatement(sql)){
            ps.setString(1, String.valueOf(specialDeduction.getStaffCode()));
            ps.setDate(2, specialDeduction.getDeductionMonth());
            ps.setBigDecimal(3, specialDeduction.getChildEducation());
            ps.setBigDecimal(4, specialDeduction.getContinueEducation());
            ps.setBigDecimal(5, specialDeduction.getHousingLoanInterest());
            ps.setBigDecimal(6, specialDeduction.getHousingRent());
            ps.setBigDecimal(7, specialDeduction.getElderlySupport());
            ps.setBigDecimal(8, specialDeduction.getSeriousIllness());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
           e.printStackTrace();
           return false;
        }

    }
}
