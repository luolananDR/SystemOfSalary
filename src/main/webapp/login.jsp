<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录 - 人员工资管理系统</title>
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
    <h2>系统登录</h2>
    <form action="LoginServlet" method="post">
        <label>用户名：</label>
        <input type="text" name="username" required>
        <label>密码：</label>
        <input type="password" name="password" required>
        <a href="register.jsp">没有账户？点击注册</a><br><br>
        <input type="submit" value="登录">
    </form>
    <c:if test="${not empty errorMessage}">
        <p style="color:red">${errorMessage}</p>
    </c:if>
    <c:if test="${not empty successMessage}">
        <p style="color:green">${successMessage}</p>
    </c:if>

</div>
<script>
    const params = new URLSearchParams(window.location.search);
    if (params.get("timeout") === "true") {
        alert("您已超过30分钟未操作，请重新登录。");
        // 清除 URL 参数
        window.history.replaceState({}, document.title, window.location.pathname);
    }
</script>
</body>
</html>