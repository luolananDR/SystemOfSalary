<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/15
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>日志管理</title>
</head>
<body>
<form method="post" action="AuditLogQueryServlet">
    用户名：<input type="text" name="username">
    操作类型：<select name="type">
    <option value="">全部</option>
    <option value="登录">登录</option>
    <option value="查询">查询</option>
    <option value="注册">注册</option>
    <option value="添加">添加</option>
    <option value="修改">修改</option>
    <option value="删除">删除</option>
        </select>
    时间：<input type="date" name="start"> ~ <input type="date" name="end">
    <button type="submit">查询</button>
    <button type="button" onclick="window.location.href='index.jsp'">返回</button>

</form>

<table>
    <tr><th>时间</th><th>用户名</th><th>角色</th><th>操作类型</th><th>对象</th><th>结果</th><th>IP</th></tr>
    <c:forEach var="log" items="${requestScope.logs}" varStatus="status">
        <tr>
            <td>${log.timestamp}</td>
            <td>${log.username}</td>
            <td>${log.role}</td>
            <td>${log.operationType}</td>
            <td>${log.operationObject}</td>
            <td>${log.result}</td>
            <td>${log.ipAddress}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
