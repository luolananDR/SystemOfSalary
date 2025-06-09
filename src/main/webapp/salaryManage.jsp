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
        }
        .container {
            display: flex;
            gap: 20px;
        }
        .left-panel {
            flex: 2;  /* 左边宽度占比 */
        }
        .right-panel {
            flex: 1;  /* 右边宽度占比 */
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 5px;
            height: fit-content;
            background: #f9f9f9;
        }
        /* 查询表单和表格之间间距 */
        .search-form {
            margin-bottom: 20px;
            padding: 10px;
            background: #e8f0fe;
            border-radius: 5px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 6px 10px;
            border: 1px solid #999;
            text-align: center;
        }
        tr:nth-child(even) {
            background: #eeeeff;
        }
        tr:nth-child(odd) {
            background: #dedeff;
        }
        fieldset {
            margin-bottom: 15px;
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
            月份：<input type="text" name="salaryMonth" placeholder="YYYY-MM" />
            <input type="submit" value="查询" />
        </form>

        <table>
            <tr>
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

    <div class="right-panel">
        <h3>新增工资记录</h3>
        <form action="AddSalaryServlet" method="post">
            <fieldset>
                <legend><strong>员工基本信息</strong></legend>
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
                个税：<br><input type="number" name="personalIncomeTax" step="0.01" /><br>
                请假扣款：<br><input type="number" name="leaveDeduction" step="0.01" />
            </fieldset>
            <input type="submit" value="新增" />
        </form>
    </div>

</div>


</body>
</html>
