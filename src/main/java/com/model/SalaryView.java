package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryView {
    private Integer id;
    private Integer staffCode;
    private String staffName;
    private String department;
    private Date salaryMonth;
    private BigDecimal baseSalary;
    private BigDecimal positionAllowance;
    private BigDecimal lunchAllowance;
    private BigDecimal overtimePay;
    private BigDecimal fullAttendanceBonus;
    private BigDecimal socialInsurance;
    private BigDecimal housingFund;
    private BigDecimal personalIncomeTax;
    private BigDecimal leaveDeduction;
    private BigDecimal actualSalary;


}
