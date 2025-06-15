package com.dao;

import com.model.AuditLog;
import com.model.UserRole;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDao extends BaseDao {

    public ArrayList<AuditLog> fingAllLogs() {
        String sql = "SELECT * FROM audit_log";
        ArrayList<AuditLog> logs = new ArrayList<>();
        try(Connection cn=dataSource.getConnection();
            PreparedStatement ps= cn.prepareStatement(sql)){
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                AuditLog log = new AuditLog();
                log.setUsername(rst.getString("username"));
                log.setRole(rst.getString("role"));
                log.setOperationType(rst.getString("operation_type"));
                log.setOperationObject(rst.getString("operation_object"));
                log.setIpAddress(rst.getString("ip_address"));
                log.setTimestamp(rst.getTimestamp("timestamp"));
                log.setResult(rst.getString("result"));
                log.setRemarks(rst.getString("remarks"));
                logs.add(log);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return logs;
    }

    public ArrayList<AuditLog> queryLogs(String username, String type, String startDate, String endDate) {
        ArrayList<AuditLog> logs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM audit_log WHERE 1=1");
        List<Object> paramList = new ArrayList<>();

        if (username != null && !username.trim().isEmpty()) {
            sql.append(" AND username LIKE ?");
            paramList.add("%" + username.trim() + "%");
        }

        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND operation_type LIKE ?");
            paramList.add("%" + type.trim() + "%");
        }

        if (startDate != null && !startDate.trim().isEmpty()
                && endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND timestamp BETWEEN ? AND ?");
            paramList.add(startDate.trim());
            paramList.add(endDate.trim());
        }
        try (Connection cn = dataSource.getConnection();
                PreparedStatement ps = cn.prepareStatement(String.valueOf(sql))) {
                    for (int i = 0; i < paramList.size(); i++) {
                        ps.setObject(i + 1, paramList.get(i));
                    }
                 ResultSet rst = ps.executeQuery();
                while (rst.next()) {
                    AuditLog log = new AuditLog();
                    log.setUsername(rst.getString("username"));
                    log.setRole(rst.getString("role"));
                    log.setOperationType(rst.getString("operation_type"));
                    log.setOperationObject(rst.getString("operation_object"));
                    log.setIpAddress(rst.getString("ip_address"));
                    log.setTimestamp(rst.getTimestamp("timestamp"));
                    log.setResult(rst.getString("result"));
                    log.setRemarks(rst.getString("remarks"));
                    logs.add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        return logs;

    }

    public void insert(AuditLog log) {
        String sql = "INSERT INTO audit_log (username, role, operation_type, operation_object, ip_address, timestamp, result, remarks) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, log.getUsername());
            ps.setString(2, log.getRole());
            ps.setString(3, log.getOperationType());
            ps.setString(4, log.getOperationObject());
            ps.setString(5, log.getIpAddress());
            ps.setTimestamp(6, log.getTimestamp());
            ps.setString(7, log.getResult());
            ps.setString(8, log.getRemarks());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
