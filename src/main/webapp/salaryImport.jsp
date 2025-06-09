<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/7
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量导入工资</title>
</head>
<body>
<h2>批量导入工资（Excel）</h2>
<form action="SalaryImportServlet" method="post" enctype="multipart/form-data">
    <label>选择 Excel 文件：</label><br>
    <input type="file" name="salaryFile" accept=".xls,.xlsx" required><br><br>
    <button type="submit">导入工资</button>
</form>
<br><a href="index.jsp">返回</a>
</body>
</html>
