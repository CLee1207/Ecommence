<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/22
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form modelAttribute="demandOrder">
    <label>商品名称:</label>
    <form:input path="title"></form:input>
    <form:errors path="title"></form:errors>

    <label>期望地址:</label>
    <form:input path="exceptionAddress"></form:input>
    <form:errors path="exceptionAddress"></form:errors>

    <label>求购类型</label>
    <form:input path="demandType"></form:input>
    <form:errors path="demandType"></form:errors>

    <label>收获地址:</label>
    <form:input path="receiveAddress"></form:input>
    <form:errors path="receiveAddress"></form:errors>

    <input type="submit" value="submit" name="submit" />
</form:form>
