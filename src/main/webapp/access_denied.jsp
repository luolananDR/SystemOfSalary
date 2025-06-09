<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>权限不足</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f5f5f5;
        }
        .message {
            color: red;
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            padding: 20px;
            border: 1px solid #ffcccc;
            background-color: #ffeeee;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .countdown {
            color: #666;
            font-size: 16px;
            margin-top: 10px;
        }
    </style>
    <script>
        // 倒计时5秒后返回上一页
        let seconds = 5;

        function updateCountdown() {
            document.getElementById('countdown').textContent = seconds;
            seconds--;

            if (seconds >= 0) {
                setTimeout(updateCountdown, 1000);
            } else {
                window.history.back();
            }
        }

        // 页面加载后开始倒计时
        window.onload = function() {
            updateCountdown();
        };
    </script>
</head>
<body>
<div class="message">
    权限不足，无法进入
    <div class="countdown">
        <span id="countdown">5</span>秒后自动返回上一页
    </div>
</div>
</body>
</html>