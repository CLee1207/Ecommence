<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/13
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title></title>
</head>
<body>
<form:form modelAttribute="fabric">
    <label>种类:</label>
    <form:input path="category"></form:input>
    <input type="hidden" value="0" name="_page" />
    <input type="submit" value="下一步" name="_target1" />
    <input type="submit" value="取消" name="_cancel" />
</form:form>
</body>
</html>
