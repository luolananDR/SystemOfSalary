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
public class Staff {
    private Integer id;
    private String staffCode;
    private String name;
    private Integer departmentId;
    private String position;
    private String title;
    private String idNumber;
    private String phone;
    private String address;
    private Timestamp createdAt;

}