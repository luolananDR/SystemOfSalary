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
public class LoginLog {
    private Integer id;
    private Integer userId;
    private Timestamp loginTime;
    private String loginIp;
    private Boolean success;
}