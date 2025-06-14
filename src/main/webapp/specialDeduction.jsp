<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/9
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人专项附加扣除</title>
</head>
<body>
<h2>专项附加扣除信息</h2>
<p><strong>员工姓名：</strong>${staff.getName()}</p>
<p><strong>员工编号：</strong>${ staff.getStaffCode() }</p>

<form action="SpecialDedutionImportServlet" method="post">
    <input type="hidden" name="staffCode" value="${deduction.staffCode}">

    子女教育：<input type="text" name="childEducation" value="${deduction.childEducation}"> 元<br><br>

    继续教育：
    <select name="continueEducation">
        <option value="无" >无</option>
        <option value="有" >有（400元）</option>
    </select><br><br>

    住房贷款：
    <select name="housingLoanInterest">
        <option value="无">无</option>
        <option value="有" >有（1000元）</option>
    </select><br><br>
    住房租金：
    <select name="housingRent">
        <option value="无">无</option>
        <option value="有" >有（1500元）</option>
    </select><br><br>
    赡养老人：<input type="text" name="elderlySupport" value="${deduction.elderlySupport}"> 元<br><br>
    大病医疗：<input type="text" name="seriousIllness" value="${deduction.seriousIllness}"> 元<br><br>

    <button type="submit">保存修改</button>
    <a href="javascript:history.back();">返回</a>
</form>
</body>
</html>