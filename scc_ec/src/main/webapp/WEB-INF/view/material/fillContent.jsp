<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/27
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form modelAttribute="material" action="${flowExecutionUrl}">
    <form:label path="materialType">类型：</form:label>
    <form:input path="materialType"></form:input>
    <form:errors path="materialType"></form:errors>

    <form:label path="customId">货号：</form:label>
    <form:input path="customId"></form:input>
    <form:errors path="customId"></form:errors>

    <form:label path="name">名称：</form:label>
    <form:input path="name"></form:input>
    <form:errors path="name"></form:errors>

    <form:label path="keys">起定量</form:label>
    <c:if test="${empty material.ranges}">
        <form:input path="keys"/>
        <form:errors path="keys"></form:errors>
        <form:input path="values"/>
        <form:errors path="values"></form:errors>
    </c:if>
    <c:if test="${ not empty material.ranges }">
        <c:forEach items="${fabric.ranges}" var="ranges">
            <div>
                <form:input path="keys" value="${ranges.key}"></form:input>
                <form:errors path="keys"></form:errors>
                <form:input path="values" value="${ranges.value}"></form:input>
                <form:errors path="values"></form:errors>
            </div>
        </c:forEach>
    </c:if>

    <input type="submit" name="_eventId_previous" value="上一步">
    <input type="submit" name="_eventId_temporary" value="暂存">
    <input type="submit" name="_eventId_finish" value="完成">
</form:form>
