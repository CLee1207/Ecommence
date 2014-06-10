<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/27
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="material" items="${materialGrid.ecList}">
    ${material.id}
    ${material.name}
</c:forEach>
