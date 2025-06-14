<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/11
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册 - 人员工资管理系统</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f7fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .form-container {
            background-color: #fff;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            width: 350px;
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #444;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #1e88e5;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #1565c0;
        }

        p {
            text-align: center;
            margin-top: 10px;
        }

        p[style*="color:red"] {
            color: red;
        }

        p[style*="color:green"] {
            color: green;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>注册新用户</h2>
    <form action="RegisterServlet" method="post">
        <label>用户名：</label><input type="text" name="username" required><br><br>
        <label>密码：</label><input type="password" name="password" placeholder="至少8位包含数字，大小写字母和特殊字符" required><br><br>
        <label>确认密码：</label><input type="password" name="confirmPassword" required><br><br>
        <label>真实姓名：</label><input type="text" name="realName"><br><br>
        <label>手机号：</label><input type="text" name="phone"><br><br>
        <label>身份证号：</label><input type="text" name="idNumber"><br><br>
        <label>住址：</label><input type="text" name="address"><br><br>
        <input type="submit" value="注册">
        <a href="login.jsp">已有账号？登录</a>
    </form>

    <c:if test="${not empty errorMessage}">
        <p style="color:red">${errorMessage}</p>
    </c:if>
    <c:if test="${not empty successMessage}">
        <p style="color:green">${successMessage}</p>
    </c:if>
</div>
<script>
    // 简单的表单验证
    document.querySelector('form').addEventListener('submit', function(event) {
        const password = document.querySelector('input[name="password"]').value;
        const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;
        if (password !== confirmPassword) {
            event.preventDefault();
            alert("密码和确认密码不一致！");
        }
        //密码校验，至少8位包含数字，大小写字母和特殊字符
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordRegex.test(password)) {
            event.preventDefault();
            alert("密码必须至少8位，包含数字，大小写字母和特殊字符！");
        }
    });

</script>
</body>
</html>
