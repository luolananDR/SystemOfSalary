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
public class SysUser {
    private Integer id;
    private String username;
    private String passwordHash;
    private String realName;
    private String phone;
    private String idNumber;
    private String address;
    private Integer roleId;
    private Boolean isLocked;
    private Timestamp lastPasswordChange;
    private Timestamp createdAt;
}