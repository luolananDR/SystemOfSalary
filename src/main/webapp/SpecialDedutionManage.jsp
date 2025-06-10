<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/9
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人专项附加扣除录入</title>
</head>
<body>
<form id="deduction-form" method="post" action="SpecialDedutionImportServlet">
    员工编号：<input name="staffCode"><br>
    所属月份：<input name="deductionMonth" type="month"><br>
    子女教育：<input name="childEducation"><br>
    继续教育：<input name="continueEducation"><br>
    住房贷款利息：<input name="housingLoanInterest"><br>
    租金支出：<input name="housingRent"><br>
    赡养老人：<input name="elderlySupport"><br>
    大病医疗：<input name="seriousIllness"><br>
    <button type="submit">保存</button>
</form>

</body>
</html>
