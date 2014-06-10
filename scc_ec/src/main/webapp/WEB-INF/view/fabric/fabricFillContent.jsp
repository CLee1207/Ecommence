<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/14
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title></title>
</head>
<body>
${success}
<form:form modelAttribute="fabric" action="${flowExecutionUrl}">
    <form:label path="density">密度</form:label>
    <form:input path="density"></form:input>
    <form:errors path="density"></form:errors>
    <form:label path="name">名称：</form:label>
    <form:input path="name"></form:input>
    <form:errors path="name"></form:errors>
    <input type="submit" value="previous" name="_eventId_previous"/>
    <input type="submit" value="完成" name="_eventId_finish"/>
    <input type="submit" value="暂存" name="_eventId_temporary"/>
    <form:label path="ranges">价格</form:label>
    <c:if test="${empty fabric.ranges}">
        <form:input path="keys"/>
        <form:errors path="keys"></form:errors>
        <form:input path="values"/>
        <form:errors path="values"></form:errors>
    </c:if>
    <c:if test="${ not empty fabric.ranges }">
        <c:forEach items="${fabric.ranges}" var="ranges">
            <div>
                <form:input path="keys" value="${ranges.key}"></form:input>
                <form:errors path="keys"></form:errors>
                <form:input path="values" value="${ranges.value}"></form:input>
                <form:errors path="values"></form:errors>
            </div>
        </c:forEach>
    </c:if>
</form:form>

</body>
</html>
