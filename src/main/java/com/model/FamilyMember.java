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
public class FamilyMember {
    private Integer id;
    private Integer staffId;
    private String name;
    private String idNumber;
    private String relation;
    private Timestamp createdAt;
}