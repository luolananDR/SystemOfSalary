<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/7
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>工资管理</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 40px;
        }

        .container {
            display: flex;
            gap: 30px;
            max-width: 1300px;
            margin: 0 auto;
        }

        .left-panel, .right-panel {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
        }

        .left-panel {
            flex: 2;
        }

        .right-panel {
            flex: 1;
            position: sticky;
            top: 40px;
            height: fit-content;
        }

        h2, h3 {
            color: #333;
            margin-top: 0;
            margin-bottom: 20px;
        }

        button, input[type="submit"] {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 10px;
        }

        button:hover, input[type="submit"]:hover {
            background-color: #1976d2;
        }

        a {
            color: #2196F3;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .search-form {
            margin-bottom: 20px;
            padding: 15px;
            background: #e3f2fd;
            border-radius: 6px;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
            font-size: 14px;
        }

        .search-form input[type="text"],
        .search-form input[type="date"] {
            padding: 6px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
            font-size: 14px;
        }

        th {
            background-color: #f2f2f2;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:nth-child(odd) {
            background-color: #ffffff;
        }

        fieldset {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px 15px;
            margin-bottom: 15px;
            background-color: #fcfcfc;
        }

        legend {
            font-weight: bold;
            color: #333;
            padding: 0 5px;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 6px 10px;
            margin-top: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="left-panel">

        <h2>工资查询</h2><button onclick="location.href='index.jsp'">返回</button>
        <form action="SalaryQueryServlet" method="post" class="search-form">
            姓名：<input type="text" name="staffName" />
            部门：<input type="text" name="department" />
            时间：<input type="date" name="start"> ~ <input type="date" name="end">
            <input type="submit" value="查询" />
        </form>

        <table>
            <tr>
                <th>工号</th>
                <th>姓名</th>
                <th>部门</th>
                <th>月份</th>
                <th>基本工资</th>
                <th>岗位津贴</th>
                <th>午餐补贴</th>
                <th>加班工资</th>
                <th>全勤奖金</th>
                <th>社保</th>
                <th>公积金</th>
                <th>个税</th>
                <th>请假扣款</th>
                <th>实发工资</th>
                <th>操作</th>
            </tr>
            <c:forEach var="s" items="${requestScope.salary}" varStatus="status">
                <tr>
                    <td>${s.staffCode}</td>
                    <td>${s.staffName}</td>
                    <td>${s.department}</td>
                    <td>${s.salaryMonth}</td>
                    <td>${s.baseSalary}</td>
                    <td>${s.positionAllowance}</td>
                    <td>${s.lunchAllowance}</td>
                    <td>${s.overtimePay}</td>
                    <td>${s.fullAttendanceBonus}</td>
                    <td>${s.socialInsurance}</td>
                    <td>${s.housingFund}</td>
                    <td>${s.personalIncomeTax}</td>
                    <td>${s.leaveDeduction}</td>
                    <td>${s.actualSalary}</td>
                    <td><a href="SalaryEditServlet?id=${s.id}">修改</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <c:if test="${role == 'admin' or role == 'finance' }">
    <div class="right-panel">
        <h3>新增工资记录</h3>
        <form action="AddSalaryServlet" method="post">
            <fieldset>
                <legend><strong>员工基本信息</strong></legend>
                工号：<br><input type="text" name="staffCode" required /><br>
                姓名：<br><input type="text" name="staffName" required /><br>
                部门：<br><input type="text" name="department" required /><br>
                月份：<br><input type="text" name="salaryMonth" placeholder="YYYY-MM" required />
            </fieldset>
            <fieldset>
                <legend><strong>工资明细</strong></legend>
                基本工资：<br><input type="number" name="baseSalary" step="0.01" required /><br>
                岗位津贴：<br><input type="number" name="positionAllowance" step="0.01" /><br>
                午餐补贴：<br><input type="number" name="lunchAllowance" step="0.01" /><br>
                加班工资：<br><input type="number" name="overtimePay" step="0.01" /><br>
                全勤奖金：<br><input type="number" name="fullAttendanceBonus" step="0.01" />
            </fieldset>
            <fieldset>
                <legend><strong>扣除项目</strong></legend>
                社保：<br><input type="number" name="socialInsurance" step="0.01" /><br>
                公积金：<br><input type="number" name="housingFund" step="0.01" /><br>
                请假扣款：<br><input type="number" name="leaveDeduction" step="0.01" />
            </fieldset>
            <input type="submit" value="新增" />
        </form>
    </div>
    </c:if>
</div>


</body>
</html>
