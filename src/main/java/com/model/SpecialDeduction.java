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
public class SpecialDeduction {//专项附加扣除
    private Integer id;
    private Integer staffId;
    private Integer deductionYear;
    private BigDecimal childEducation;
    private BigDecimal continuingEducation;
    private BigDecimal majorDisease;
    private BigDecimal housingLoanInterest;
    private BigDecimal housingRent;
    private BigDecimal elderSupport;
    private BigDecimal infantCare;
    private Timestamp createdAt;
}