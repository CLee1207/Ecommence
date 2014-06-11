<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/6
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
    table,th, td{
        border: 1px solid black
    }
</style>
<div>
    <a href="/systemConfig/firstSource?form">添加产品类别</a>
    <table>
        <tr>
            <td>顺序号</td>
            <td>产品类别</td>
            <td>创建人</td>
            <td>创建时间</td>
            <td>更新人</td>
            <td>更新时间</td>
            <td>是否有效</td>
            <td>操作</td>
        </tr>
        <c:forEach var="firstCategory" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${firstCategory.id}">
                <td>${list.index+1}</td>
                <td><a href="/systemConfig/secondSource/add/${firstCategory.id}">${firstCategory.name}</a></td>
                <td>${firstCategory.createdBy.name}</td>
                <td><fmt:formatDate value="${firstCategory.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td>${firstCategory.updatedBy.name}</td>
                <td><fmt:formatDate value="${firstCategory.updatedTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td>${firstCategory.isValid}</td>
                <td><a href="#" class="editFirstSource" data-id="${firstCategory.id}">edit</a></td>
            </tr>
        </c:forEach>
    </table>
</div>