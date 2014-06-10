<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/28
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form modelAttribute="sampleOrder">
    <form:label path="sampleCardNumber">册:</form:label>
    <form:input path="sampleCardNumber"></form:input>
    <form:errors path="sampleCardNumber"></form:errors>

    <form:label path="sampleItemMile">米:</form:label>
    <form:input path="sampleItemMile"></form:input>
    <form:errors path="sampleItemMile"></form:errors>

    <input type="submit" value="提交" name="_event_submit">
    <input type="submit" value="草稿" name="_event_temporary">
</form:form>
<div>
    ${name}
    ${id}
    ${coverImage}
</div>
