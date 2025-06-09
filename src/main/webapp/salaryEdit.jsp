
<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/8
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>修改工资信息</title></head>
<body>
<h2>修改工资信息</h2>
<form action="SalaryEditServlet" method="post">
  <input type="hidden" name="id" value="${salary.id}" />

  <p>姓名：${staff.name}</p>
  <input type="hidden" name="staffName" value="${staff.name}" />
  <p>部门：${department.name}</p>
  <input type="hidden" name="departmentName" value="${department.name}" />
  <p>月份：${salary.salaryMonth}</p>
  <input type="hidden" name="salaryMonth" value="${salary.salaryMonth}" />

  <label>基本工资：</label><input type="text" name="baseSalary" value="${salary.baseSalary}" /><br/>
  <label>岗位津贴：</label><input type="text" name="positionAllowance" value="${salary.positionAllowance}" /><br/>
  <label>午餐补贴：</label><input type="text" name="lunchAllowance" value="${salary.lunchAllowance}" /><br/>
  <label>加班工资：</label><input type="text" name="overtimePay" value="${salary.overtimePay}" /><br/>
  <label>全勤奖金：</label><input type="text" name="fullAttendanceBonus" value="${salary.fullAttendanceBonus}" /><br/>
  <label>社保：</label><input type="text" name="socialInsurance" value="${salary.socialInsurance}" /><br/>
  <label>公积金：</label><input type="text" name="housingFund" value="${salary.housingFund}" /><br/>
  <label>个税：</label><input type="text" name="personalIncomeTax" value="${salary.personalIncomeTax}" /><br/>
  <label>请假扣款：</label><input type="text" name="leaveDeduction" value="${salary.leaveDeduction}" /><br/>

  <br/>
  <input type="submit" value="保存修改" />
</form>
</body>
</html>
