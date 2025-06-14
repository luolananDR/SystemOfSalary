package com.control;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.dao.FamilyMemberDao;
import com.dao.StaffDao;
import com.model.FamilyMember;
import com.model.Staff;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FamilyMemberServlet")
public class FamilyMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FamilyMemberDao familyMemberDao = new FamilyMemberDao();
    private StaffDao staffDao = new StaffDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            // 显示家庭成员列表
            String staffId = request.getParameter("staffId");
            Staff staff = staffDao.getStaffById(staffId);
            List<FamilyMember> familyMembers = familyMemberDao.getFamilyMembersByStaffCode(staffId);

            request.setAttribute("staff", staff);
            request.setAttribute("familyMembers", familyMembers);
            request.getRequestDispatcher("family_member_management.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String staffId = request.getParameter("staffId");

        if ("add".equals(action)) {
            // 添加新家庭成员
            FamilyMember member = new FamilyMember();
            member.setStaffCode(Integer.valueOf(staffId));
            member.setName(request.getParameter("name"));
            member.setIdNumber(request.getParameter("idNumber"));
            member.setRelation(request.getParameter("relation"));
            member.setBirthDate(Date.valueOf(request.getParameter("birthDate")));
            member.setIsStudent(Boolean.valueOf(request.getParameter("isStudent")));
            member.setIsRenting(Boolean.valueOf(request.getParameter("isRenting")));
            member.setHasMortgage(Boolean.valueOf(request.getParameter("hasMortgage")));
            member.setIsMajorDisease(Boolean.valueOf(request.getParameter("isMajorDisease")));
            familyMemberDao.addFamilyMember(member);

        } else if ("delete".equals(action)) {
            // 删除家庭成员
            String memberId = request.getParameter("memberId");
            familyMemberDao.deleteFamilyMember(memberId);
        }

        response.sendRedirect("FamilyMemberServlet?action=list&staffId=" + staffId);
    }
}
