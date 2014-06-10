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
    <a href="/systemConfig/secondSource?form">添加产品二级类别</a>
    <table>
        <tr>
            <td>顺序号</td>
            <td>产品类别</td>
            <td>一级分类</td>
            <td>创建人</td>
            <td>创建时间</td>
            <td>更新人</td>
            <td>更新时间</td>
            <td>是否有效</td>
            <td>操作</td>
        </tr>
        <c:forEach var="secondCategory" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${secondCategory.id}">
                <td>${list.index+1}</td>
                <td>${secondCategory.name}</td>
                <td>${secondCategory.parent.name}</td>
                <td>${secondCategory.createdBy.name}</td>
                <td><fmt:formatDate value="${secondCategory.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td>${secondCategory.updatedBy.name}</td>
                <td><fmt:formatDate value="${secondCategory.updatedTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td>${secondCategory.isValid}</td>
                <td><a href="#" class="editSecondSource" data-id="${secondCategory.id}">edit</a></td>
            </tr>
        </c:forEach>
    </table>
</div>