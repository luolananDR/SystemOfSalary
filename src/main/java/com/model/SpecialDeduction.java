package com.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialDeduction {
    private Integer id;                           // 主键ID
    private Integer staffCode;                    // 员工编号（关联员工信息）
    private BigDecimal childEducation;            // 子女教育
    private BigDecimal continueEducation;         // 继续教育
    private BigDecimal housingLoanInterest;       // 住房贷款利息
    private BigDecimal housingRent;               // 住房租金
    private BigDecimal elderlySupport;            // 赡养老人
    private BigDecimal seriousIllness;            // 大病医疗
    private Timestamp createdAt;                  // 创建时间

    public BigDecimal getTotalDeduction() {
        return Stream.of(
                childEducation, continueEducation, housingLoanInterest,
                housingRent, elderlySupport, seriousIllness
        ).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}