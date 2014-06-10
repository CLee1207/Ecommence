<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/14
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<style type="text/css">
    table,th, td{
        border: 1px solid black
    }
</style>
${success}
<table>
    <tr>
        <td>商品编号</td>
        <td>商品名称</td>
        <td>操作</td>
    </tr>
    <c:forEach var="fabric" items="${grid.ecList}">
        <tr>
            <td> ${fabric.id}</td>
            <td>${fabric.name}</td>
            <td><button class="addCart" data-id="${fabric.id}" data-spid="${fabric.createdBy.id}">add2Cart</button></td>
        </tr>
    </c:forEach>
    <input type="hidden" id="quantity" value="10">
</table>
<a href="/orderCenter/shopCart">查看购物车</a>