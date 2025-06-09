package com.control;
import java.io.IOException;
import java.util.List;

import com.dao.StaffDao;
import com.model.Staff;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StaffServlet")
public class StaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffDao staffDao = new StaffDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 显示所有员工
        List<Staff> staffList = staffDao.getAllStaff();
        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("employee_management.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // 添加新员工
            Staff staff = new Staff();
            staff.setName(request.getParameter("name"));
            staff.setDepartmentId(Integer.valueOf(request.getParameter("department")));
            staff.setPosition(request.getParameter("position"));
            staff.setIdNumber(request.getParameter("idCard"));
            staff.setPhone(request.getParameter("phone"));
            staff.setAddress(request.getParameter("address"));

            staffDao.addStaff(staff);

        } else if ("update".equals(action)) {
            // 更新员工信息
            Staff staff = new Staff();
            staff.setStaffCode(request.getParameter("staffId"));
            staff.setName(request.getParameter("name"));
            staff.setDepartmentId(Integer.valueOf(request.getParameter("department")));
            staff.setPosition(request.getParameter("position"));
            staff.setIdNumber(request.getParameter("idCard"));
            staff.setPhone(request.getParameter("phone"));
            staff.setAddress(request.getParameter("address"));

            staffDao.updateStaff(staff);

        } else if ("delete".equals(action)) {
            // 删除员工
            String staffId = request.getParameter("staffId");
            staffDao.deleteStaff(staffId);
        }

        response.sendRedirect("StaffServlet");
    }
}