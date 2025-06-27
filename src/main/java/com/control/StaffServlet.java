package com.control;
import java.io.IOException;
import java.util.List;

import com.dao.StaffDao;
import com.filter.AuditLogFilter;
import com.model.Staff;
import jakarta.servlet.RequestDispatcher;
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
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            // 显示员工列表（带分页）
            AuditLogFilter.log(request, "查询", "员工信息", "成功", "通过过滤器记录");

            // 获取分页参数
            int page = 1;
            int recordsPerPage = 7; // 每页显示7条记录

            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                // 使用默认值
            }

            // 获取总记录数
            int totalRecords = staffDao.getTotalStaffCount();

            // 计算总页数
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            // 确保当前页在有效范围内
            if (page < 1) page = 1;
            if (page > totalPages && totalPages > 0) page = totalPages;

            // 获取当前页的员工列表
            List<Staff> staffList = staffDao.getStaffByPage((page - 1) * recordsPerPage, recordsPerPage);

            // 设置请求属性
            request.setAttribute("staffList", staffList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalRecords", totalRecords);

            RequestDispatcher rd = getServletContext().getRequestDispatcher("/staffManage.jsp");
            rd.forward(request, response);
        } else {
            // 其他GET请求处理（如果有）
            response.sendRedirect("staffManage.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // 添加新员工
            AuditLogFilter.log(request, "添加", "员工信息", "成功", "通过过滤器记录");
            Staff staff = new Staff();
            staff.setName(request.getParameter("name"));
            staff.setDepartmentId(Integer.valueOf(request.getParameter("department")));
            staff.setPosition(request.getParameter("position"));
            staff.setStaffCode(request.getParameter("staffCode"));
            staff.setIdNumber((String) request.getAttribute("encrypted_idNumber"));
            staff.setPhone((String) request.getAttribute("encrypted_phone"));
            staff.setAddress((String) request.getAttribute("encrypted_address"));

            staffDao.addStaff(staff);

        } else if ("update".equals(action)) {
            // 更新员工信息
            Staff staff = new Staff();
            staff.setStaffCode(request.getParameter("staffCode"));
            staff.setName(request.getParameter("name"));
            staff.setDepartmentId(Integer.valueOf(request.getParameter("department")));
            staff.setPosition(request.getParameter("position"));
            staff.setIdNumber((String) request.getAttribute("encrypted_idNumber"));
            staff.setPhone((String) request.getAttribute("encrypted_phone"));
            staff.setAddress((String) request.getAttribute("encrypted_address"));

            staffDao.updateStaff(staff);

        } else if ("delete".equals(action)) {
            // 删除员工
            AuditLogFilter.log(request, "删除", "员工信息", "成功", "通过过滤器记录");
            String staffId = request.getParameter("staffId");
            staffDao.deleteStaff(staffId);
        }

        response.sendRedirect("StaffServlet?action=list");
    }
}