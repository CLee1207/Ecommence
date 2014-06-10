<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/22
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title></title>
</head>
<body>
    <form:form modelAttribute="address">
        <label>地区</label>
        <form:input path="state"></form:input>
        <form:errors path="state"></form:errors>

        <label>city</label>
        <form:input path="city"></form:input>
        <form:errors path="city"></form:errors>

        <label>邮编</label>
        <form:input path="zipCode"></form:input>
        <form:errors path="zipCode"></form:errors>

        <label>收货人姓名</label>
        <form:input path="receiverName"></form:input>
        <form:errors path="receiverName"></form:errors>

        <label>街道地址</label>
        <form:input path="street"></form:input>
        <form:errors path="street"></form:errors>
        <input type="submit" value="submit">
    </form:form>
</body>
</html>
