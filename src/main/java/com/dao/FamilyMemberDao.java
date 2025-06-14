package com.dao;

import com.model.FamilyMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyMemberDao extends BaseDao{
    public List<FamilyMember> getFamilyMembersByStaffCode(String staffCode) {
        List<FamilyMember> familyMembers = new ArrayList<>();
        String sql = "SELECT * FROM family_member WHERE staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FamilyMember member = new FamilyMember();
                member.setId(rs.getInt("id"));
                member.setStaffCode(rs.getInt("staff_code"));
                member.setName(rs.getString("name"));
                member.setRelation(rs.getString("relation"));
                member.setIdNumber(rs.getString("id_number"));
                member.setBirthDate(rs.getDate("birth_date"));
                member.setIsStudent(rs.getBoolean("is_student"));
                member.setHasMortgage(rs.getBoolean("has_mortgage"));
                member.setIsRenting(rs.getBoolean("is_renting"));
                member.setIsMajorDisease(rs.getBoolean("is_major_disease"));
                member.setCreatedAt(rs.getTimestamp("created_at"));
                member.setUpdatedAt(rs.getTimestamp("updated_at"));
                familyMembers.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();}
        return familyMembers;
    }

    public void addFamilyMember(FamilyMember member) {
        String sql = "INSERT INTO family_member (staff_code, name, relation, id_number, birth_date, is_student, has_mortgage, is_renting, is_major_disease, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, member.getStaffCode());
            ps.setString(2, member.getName());
            ps.setString(3, member.getRelation());
            ps.setString(4, member.getIdNumber());
            ps.setDate(5, member.getBirthDate());
            ps.setBoolean(6, member.getIsStudent());
            ps.setBoolean(7, member.getHasMortgage());
            ps.setBoolean(8, member.getIsRenting());
            ps.setBoolean(9, member.getIsMajorDisease());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFamilyMember(String memberId) {
        String sql = "DELETE FROM family_member WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
