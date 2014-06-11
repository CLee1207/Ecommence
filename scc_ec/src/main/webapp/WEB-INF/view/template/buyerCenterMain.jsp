<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/15
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <title>我的平台</title>
        <script type="text/javascript" src="${context}/resources/js/jquery-1.11.1.min.js"></script>
        <tiles:importAttribute name="script"/>
        <c:forEach var="item" items="${script}">
            <script src="${context}${item}"></script>
        </c:forEach>
    </head>
<body>
    <a href="/orderCenter/shopCart">我的购物车</a>
    <a href="/orderCenter/buyerOrderList">我的订单</a>
    <tiles:insertAttribute name="main"></tiles:insertAttribute>
</body>
</html>
