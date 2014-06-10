<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/26
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form:form modelAttribute="material" action="${flowExecutionUrl}">

    <form:label path="category">分类：</form:label>
    <form:checkboxes path="category" items="${categories}" itemLabel="name" itemValue="id"></form:checkboxes>
    <form:errors path="category"></form:errors>

    <input type="submit" name="_eventId_next" value="下一步">
</form:form>
