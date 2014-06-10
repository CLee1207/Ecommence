<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/15
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<form:form modelAttribute="fabric" action="${flowExecutionUrl}">
    <label>种类:</label>
    <form:checkboxes path="category" items="${categoies}" itemLabel="name" itemValue="id"></form:checkboxes>
    <form:errors path="category"></form:errors>

    <label>原料成分：</label>
    <form:radiobuttons path="source" items="${sources}" itemLabel="name" itemValue="id"></form:radiobuttons>
    <form:errors path="source"></form:errors>

    <label>原料成分细分：</label>
    <form:radiobuttons path="sourceDetail" items="${detailSources}" itemLabel="name" itemValue="id"></form:radiobuttons>
    <form:errors path="sourceDetail"></form:errors>

    <label>主要使用：</label>
    <form:checkboxes path="mainUseTypes" items="${mainTypes}" itemLabel="name" itemValue="id"></form:checkboxes>
    <form:errors path="mainUseTypes"></form:errors>

    <input type="submit" value="下一步" name="_eventId_next" />
    <input type="submit" value="取消" name="_eventId_cancel" />
</form:form>
