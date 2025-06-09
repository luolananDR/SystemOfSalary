package com.dao;

import com.model.FamilyMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FamilyMemberDao extends BaseDao{
    public List<FamilyMember> getFamilyMembersByStaffId(String staffId) {
        List<FamilyMember> familyMembers = null;
        String sql = "SELECT * FROM family_member WHERE staff_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, staffId);
            try (ResultSet rst = pstmt.executeQuery()) {
                while (rst.next()) {
                    FamilyMember member = new FamilyMember();
                    member.setId(rst.getInt("id"));
                    member.setStaffId(rst.getInt("staff_id"));
                    member.setName(rst.getString("name"));
                    member.setIdNumber(rst.getString("id_number"));
                    member.setRelation(rst.getString("relation"));
                    member.setCreatedAt(rst.getTimestamp("created_at"));
                    familyMembers.add(member);
                }
            }
        } catch (SQLException se) {
            return null;
        }
        return familyMembers;
    }

    public void addFamilyMember(FamilyMember member) {
        String sql = "INSERT INTO family_member (staff_id, name, id_number, relation, created_at) VALUES (?, ?, ?, ?, current_timestamp)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, member.getStaffId());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getIdNumber());
            pstmt.setString(4, member.getRelation());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFamilyMember(String memberId) {
        String sql = "DELETE FROM family_member WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
