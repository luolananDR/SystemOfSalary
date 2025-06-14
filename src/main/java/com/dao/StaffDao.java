package com.dao;

import com.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDao extends BaseDao{
    public Staff getStaffById(String staffId) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("department_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    public Staff getStaffByStaffCode(String staffCode) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staffCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setStaffCode(rs.getString("staff_code"));
                staff.setName(rs.getString("name"));
                staff.setDepartmentId(rs.getInt("department_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    public void addStaff(Staff staff) {
        String sql = "INSERT INTO staff (name, department_id, staff_code) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setInt(2, staff.getDepartmentId());
            ps.setString(3, staff.getStaffCode());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff staff) {
        String sql = "UPDATE staff SET name = ?, department_id = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setInt(2, staff.getDepartmentId());
            ps.setInt(3, staff.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff(String staffId) {
        String sql = "DELETE FROM staff WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setStaffCode(rs.getString("staff_code"));
                staff.setName(rs.getString("name"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("department_id")));
                staffList.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public Staff findByStaffCode(Integer staffCode) {
    Staff staff = null;
        String sql = "SELECT * FROM staff WHERE staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("department_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }
}
