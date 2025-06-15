<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.model.UserRole" %>
<%
    UserRole role = (UserRole) session.getAttribute("userRole");
    String username = (String) session.getAttribute("username");
    request.setAttribute("role", role);
    request.setAttribute("username", username);
%>
<c:if test="${empty role}">
    <c:redirect url="login.jsp" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>人员工资管理系统 - 控制台</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 40px;
            color: #333;
        }

        h2 {
            color: #2e6da4;
            margin-bottom: 30px;
        }

        .menu {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }

        .menu button {
            background-color: #337ab7;
            border: none;
            color: white;
            padding: 12px 24px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .menu button:hover {
            background-color: #23527c;
        }

        .menu p {
            color: red;
            font-weight: bold;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h2>欢迎，${username}（角色：${role}）</h2>

<div class="menu">
    <!-- 系统管理员权限 -->
    <c:if test="${role == 'admin'}">
    <button onclick="location.href='UserServlet'">用户管理</button>
    </c:if>

    <!-- 人事管理员权限 -->
    <c:if test="${role == 'admin' or role eq 'hr'}">
        <button onclick="location.href='StaffServlet'">人员管理</button>
    </c:if>

    <!-- 财务管理员权限 -->
     <c:if test="${role == 'admin' or role == 'finance' or role =='ceo'}">
    <button onclick="location.href='SalaryQueryServlet'">工资管理</button>
    <button onclick="location.href='salaryImport.jsp'">工资导入</button>
    </c:if>


    <!-- 总经理权限 -->
    <c:if test="${role == 'ceo' or role =='admin'}">
    <button onclick="location.href='salaryQuery.jsp'">工资查询</button>
    </c:if>

    <!-- 审计员权限 -->
    <c:if test="${role =='audit' or role =='admin'}">
    <button onclick="location.href='AuditLogQueryServlet'">日志审计</button>
    </c:if>
    <c:if test="${role == 'unauthorized'}">
        <p>您没有权限访问此系统，请联系管理员。</p>
    </c:if>
    <button onclick="location.href='login.jsp'">退出登录</button>
</div>

</body>
</html>
