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
    private Integer id;
    private Integer staffCode;
    private BigDecimal childEducation;
    private BigDecimal continueEducation;
    private BigDecimal housingLoanInterest;
    private BigDecimal housingRent;
    private BigDecimal elderlySupport;
    private BigDecimal seriousIllness;
    private Timestamp createdAt;

    public BigDecimal getTotalDeduction() {
        return Stream.of(
                childEducation, continueEducation, housingLoanInterest,
                housingRent, elderlySupport, seriousIllness
        ).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}