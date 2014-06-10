<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/5
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
    table,th, td{
        border: 1px solid black
    }
</style>
<div>
    <table>
        <tr>
            <td>顺序号</td>
            <td>资讯标题</td>
            <td>资讯分类</td>
            <td>创建人</td>
            <td>创建日期</td>
            <td>操作</td>
        </tr>
        <c:forEach var="information" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${information.id}">
                <td>${list.index+1}</td>
                <td><a href="#" class="viewInfo" data-id="${information.id}">${information.title}</a></td>
                <td>${information.informationCategory.categoryName}</td>
                <td>${information.createdBy.name}</td>
                <td><fmt:formatDate value="${information.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td><a href="#" class="editInfo" data-id="${information.id}">edit</a> <a href="#" class="deleteInfo" data-id="${information.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="/informationCenter/information?form">添加广告</a>
</div>
