<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/30
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:forEach var="item" items="${grid.ecList}">
    <div>
            <c:forEach  var ="item" items="${item.shop.newestItem}">
                ${item.name}
                ${item.url}
            </c:forEach>
    </div>
</c:forEach>
