package com.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRecord {
    private Integer id;
    private Integer staffCode;
    private Date salaryMonth;
    private BigDecimal baseSalary;//基本工资
    private BigDecimal positionAllowance;//岗位津贴
    private BigDecimal lunchAllowance;//午餐补贴
    private BigDecimal overtimePay;//加班工资
    private BigDecimal fullAttendanceBonus;//全勤奖
    private BigDecimal socialInsurance;//社保
    private BigDecimal housingFund;//公积金
    private BigDecimal personalIncomeTax;//个人所得税
    private BigDecimal leaveDeduction;//请假扣款
    private BigDecimal actualSalary;//实际工资
    private Timestamp createdAt;
}