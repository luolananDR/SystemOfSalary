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
    <link rel="stylesheet" type="text/css" href="sidebar.css">
</head>
<body>
<jsp:include page="sidebar.jsp"  />
<div class="main">
    <h2>欢迎，${username}（角色：${role}）</h2>
    <p>请通过左侧菜单选择操作。</p>
</div>

</body>
</html>