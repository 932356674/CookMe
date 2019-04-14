<%--
  Created by IntelliJ IDEA.
  User: A
  Date: 2019/2/13
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
金额:${order.amount/100.0}
订单号:${order.orderNum}
<form action="pay.html" method="post">
    <input type="hidden" value="${order.orderNum}" name="orderNum">
    <input type="hidden" value="${order.amount/100.0}" name="amount">
    <input type="submit" value="确认订单">
</form>
</body>
</html>
