<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/29
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:forEach items="${grid.ecList}" var="sampleOrder">
    ${sampleOrder.id}
</c:forEach>
