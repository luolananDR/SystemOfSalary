<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/7
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>操作结果</title>
    <script type="text/javascript">
        window.onload = function () {
            setTimeout(function () {
                let msg = "${empty msg ? '操作完成。' : msg}";
                alert(msg);
                window.location.href = "index.jsp";
            }, 1000);
        }
    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .message {
            font-size: 18px;
            color: #555;
        }
    </style>
</head>
<body>
<div class="message">正在处理，请稍候...</div>
</body>
</html>