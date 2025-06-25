<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/7
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>工资管理</title>
    <link rel="stylesheet" type="text/css" href="sidebar.css">
    <style>

        .salary-container {
            display: flex;
            gap: 30px;
            max-width: 1300px;
            margin: 0 auto;
        }

        .salary-left-panel {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
        }

        .salary-left-panel {
            flex: 2;
        }


        .salary-h1 {
            color: #333;
            margin-top: 0;
            margin-bottom: 20px;
        }

        .salary-button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin: 10px;
        }

        .salary-button:hover{
            background-color: #1976d2;
        }


        .salary-form {
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

        .salary-form input[type="text"],
        .salary-form input[type="date"] {
            padding: 3px 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .salary-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .salary-table th, .salary-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
            font-size: 14px;
        }

        .salary-table th {
            background-color: #f2f2f2;
            color: #333;
        }

        .salary-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .salary-table tr:nth-child(odd) {
            background-color: #ffffff;
        }

        .salary-fieldset {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px 15px;
            margin-bottom: 15px;
            background-color: #fcfcfc;
        }

        .salary-legend {
            font-weight: bold;
            color: #333;
            padding: 0 5px;
        }

        .salary-input {
            width: 40%;
            padding: 6px 10px;
            margin-top: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .salary-modal {
            display: none;
            position: fixed;
            z-index: 999;
            left: 0; top: 0;
            width: 100%; height: 100%;
            background-color: rgba(0,0,0,0.4);
        }

        .salary-modal-content {
            background-color: #fff;
            margin: 10px auto;
            padding: 30px;
            border-radius: 8px;
            width: 500px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            position: relative;
            max-height: 90vh;
            overflow-y: auto;
        }

        .salary-modal-content h3 {
            margin-top: 0;
            color: #333;
            text-align: center;
        }

        .salary-modal-content .salary-fieldset {
            margin-bottom: 15px;
        }

        .salary-modal-close {
            position: absolute;
            top: 10px; right: 15px;
            font-size: 24px;
            font-weight: bold;
            color: #aaa;
            cursor: pointer;
        }

        .salary-modal-close:hover {
            color: #000;
        }
        .sidebar-button:nth-child(3){
            background-color: #1ABC9C;
        }
    </style>
</head>
<body>
<jsp:include page="sidebar.jsp"  />
<div class="main">
    <div class="salary-container">
        <div class="salary-left-panel">
            <h1 class="salary-h1">工资查询</h1>
            <c:if test="${role == 'admin' or role == 'finance'}">
                <button class="salary-button" onclick="document.getElementById('salaryModal').style.display='block'">工资录入</button>
            </c:if>
            <form action="SalaryQueryServlet" method="post" class="salary-form">
                姓名：<input type="text" name="staffName" />
                部门：<input type="text" name="department" />
                时间：<input type="date" name="start"> ~ <input type="date" name="end">
                <input type="submit" value="查询" />
            </form>
            <table class="salary-table">
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
                    <c:if test="${role == 'admin' or role == 'finance'}">
                    <th>操作</th>
                    </c:if>
                </tr>
                <c:forEach var="s" items="${requestScope.salary}" varStatus="status">
                    <tr>
                        <td>${s.staffCode}</td>
                        <td>${s.staffName}</td>
                        <td>${s.department}</td>
                        <td>
                            <fmt:formatDate value="${s.salaryMonth}" pattern="yyyy-MM" />
                        </td>
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
                        <c:if test="${role == 'admin' or role == 'finance'}">
                        <td><a href="SalaryEditServlet?id=${s.id}">修改</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${role == 'admin' or role == 'finance'}">
            <!-- 工资录入弹窗 -->
            <div id="salaryModal" class="salary-modal">
                <div class="salary-modal-content">
                    <span class="salary-modal-close" onclick="document.getElementById('salaryModal').style.display='none'">&times;</span>
                    <h3>工资录入</h3>
                    <form action="AddSalaryServlet" method="post">
                        <fieldset class="salary-fieldset">
                            <legend class="salary-legend" ><strong>员工基本信息</strong></legend>
                            工号：<br><input class="salary-input" type="text" name="staffCode" required /><br>
                            姓名：<br><input class="salary-input" type="text" name="staffName" required /><br>
                            部门：<br><input class="salary-input" type="text" name="departmentName" required /><br>
                            月份：<br><input class="salary-input" type="date" name="salaryMonth"  required />
                        </fieldset>
                        <fieldset>
                            <legend class="salary-legend"><strong>工资明细</strong></legend>
                            基本工资：<br><inpu class="salary-input" type="number" name="baseSalary" step="0.01" required /><br>
                            岗位津贴：<br><input class="salary-input" type="number" name="positionAllowance" step="0.01" /><br>
                            午餐补贴：<br><input class="salary-input" type="number" name="lunchAllowance" step="0.01" /><br>
                            加班工资：<br><input class="salary-input" type="number" name="overtimePay" step="0.01" /><br>
                            全勤奖金：<br><input class="salary-input" type="number" name="fullAttendanceBonus" step="0.01" />
                        </fieldset>
                        <fieldset>
                            <legend class="salary-legend"><strong>扣除项目</strong></legend>
                            社保：<br><input class="salary-input" type="number" name="socialInsurance" step="0.01" /><br>
                            公积金：<br><input class="salary-input" type="number" name="housingFund" step="0.01" /><br>
                            请假扣款：<br><input class="salary-input" type="number" name="leaveDeduction" step="0.01" />
                        </fieldset>
                        <input class="salary-input" type="submit" value="新增" />
                    </form>
                </div>
            </div>
        </c:if>
    </div>
</div>
<script>
    window.onload = function(event) {
        let modal = document.getElementById('salaryModal');
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
</script>
</body>
</html>
