<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.FamilyMember, com.model.Staff, com.model.SysUser" %>
<%
    // 权限检查 - 只有HR可以访问此页面
    SysUser currentUser = (SysUser) session.getAttribute("currentUser");
    if (currentUser == null || !currentUser.getRole().equals("hr")) {
        response.sendRedirect("access_denied.jsp");
        return;
    }

    Staff staff = (Staff) request.getAttribute("staff");
    List<FamilyMember> familyMembers = (List<FamilyMember>) request.getAttribute("familyMembers");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>家庭成员管理</title>

</head>
<body>
<div class="container">
    <a href="StaffServlet" class="btn btn-back">返回员工列表</a>
    <h1>家庭成员管理</h1>

    <div class="staff-info">
        <h3>员工信息</h3>
        <p><strong>姓名：</strong><%= staff.getName() %></p>
        <p><strong>员工编号：</strong><%= staff.getStaffCode() %></p>
<%--        <p><strong>部门：</strong><%= staff.getDepartment() %></p>--%>
    </div>

    <button class="btn btn-add" onclick="document.getElementById('addModal').style.display='block'">添加家庭成员</button>

    <table border="1" cellspacing="0" cellpadding="8">
        <thead>
        <tr>
            <th>姓名</th>
            <th>身份证号</th>
            <th>关系</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <% if (familyMembers != null && !familyMembers.isEmpty()) {
            for (FamilyMember member : familyMembers) {
        %>
        <tr>
            <td><%= member.getName() %></td>
            <td><%= member.getIdNumber().replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1**********$2") %></td>
            <td><%= member.getRelation() %></td>
            <td class="action-buttons">
                <form action="FamilyMemberServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="memberId" value="<%= member.getId() %>">
                    <input type="hidden" name="staffCode" value="<%= staff.getStaffCode() %>">
                    <button type="submit" class="btn btn-delete" onclick="return confirm('确定要删除家庭成员 <%= member.getName() %> 吗？')">删除</button>
                </form>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="4" style="text-align: center;">暂无家庭成员数据</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<!-- 添加家庭成员模态框 -->
<div id="addModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="document.getElementById('addModal').style.display='none'">&times;</span>
        <h2>添加家庭成员</h2>
        <form action="FamilyMemberServlet" method="post">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="staffCode" value="<%= staff.getStaffCode() %>">
            <div class="form-group">
                <label for="addName">姓名</label>
                <input type="text" id="addName" name="name" required>
            </div>
            <div class="form-group">
                <label for="addIdNumber">身份证号</label>
                <input type="text" id="addIdNumber" name="idNumber" required pattern="\d{18}">
            </div>
            <div class="form-group">
                <label for="addRelation">关系</label>
                <select id="addRelation" name="relation" required>
                    <option value="">-- 请选择关系 --</option>
                    <option value="配偶">配偶</option>
                    <option value="子女">子女</option>
                    <option value="父亲">父亲</option>
                    <option value="母亲">母亲</option>
                    <option value="其他">其他</option>
                </select>
            </div>
            <div class="form-actions">
                <button type="button" class="btn" onclick="document.getElementById('addModal').style.display='none'">取消</button>
                <button type="submit" class="btn btn-add">保存</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>

<script>
    // 关闭模态框当点击模态框外部
    window.onclick = function(event) {
        if (event.target == document.getElementById('addModal')) {
            document.getElementById('addModal').style.display = 'none';
        }
    }
</script>

<style>
    body {
        font-family: Arial, sans-serif;
        margin: 20px;
        background-color: #f5f5f5;
    }
    .container {
        max-width: 1000px;
        margin: 0 auto;
        background-color: white;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h1 {
        color: #333;
        text-align: center;
    }
    .staff-info {
        background-color: #f8f9fa;
        padding: 15px;
        border-radius: 5px;
        margin-bottom: 20px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    th, td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
    }
    tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    .action-buttons {
        display: flex;
        gap: 5px;
    }
    .btn {
        padding: 5px 10px;
        border: none;
        border-radius: 3px;
        cursor: pointer;
        text-decoration: none;
        font-size: 14px;
    }
    .btn-back {
        background-color: #6c757d;
        color: white;
        margin-bottom: 15px;
    }
    .btn-add {
        background-color: #2196F3;
        color: white;
        margin-bottom: 15px;
    }
    .btn-delete {
        background-color: #f44336;
        color: white;
    }
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.4);
    }
    .modal-content {
        background-color: #fefefe;
        margin: 10% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 50%;
        border-radius: 5px;
    }
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
    }
    .form-group {
        margin-bottom: 15px;
    }
    .form-group label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }
    .form-group input, .form-group select {
        width: 100%;
        padding: 8px;
        box-sizing: border-box;
        border: 1px solid #ddd;
        border-radius: 3px;
    }
    .form-actions {
        text-align: right;
        margin-top: 20px;
    }
</style>