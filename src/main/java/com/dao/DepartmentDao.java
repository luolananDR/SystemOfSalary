package com.dao;

import com.model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepartmentDao extends BaseDao{
    public Department findById(Integer departmentId) {
        String sql = "SELECT * FROM department WHERE id = ?";
        Department department=null;
        try(Connection cn=dataSource.getConnection();
            PreparedStatement ps=cn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rst = ps.executeQuery()) {
                if (rst.next()) {
                    department = new Department();
                    department.setId(rst.getInt("id"));
                    department.setName(rst.getString("name"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return department;
    }
}
