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
public class OperationLog {
    private Integer id;
    private Integer userId;
    private Timestamp operationTime;
    private String operationType;
    private String targetTable;
    private Integer targetId;
    private String details;
    private String hmacSm3;
}