<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/3
  Time: 14:00
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
    <table>
        <tr>
            <td>顺序号</td>
            <td>广告名称</td>
            <td>栏位名称</td>
            <td>关联的产品</td>
            <td>创建日期</td>
            <td>操作</td>
        </tr>
        <c:forEach var="advertisement" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${advertisement.id}">
                <td>${list.index+1}</td>
                <td><a href="#" class="viewAd" data-id="${advertisement.id}">${advertisement.title}</a></td>
                <td>${advertisement.advertisementPosition.positionNo}</td>
                <td>${advertisement.link}</td>
                <td><fmt:formatDate value="${advertisement.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td><a href="#" class="editAd" data-id="${advertisement.id}">edit</a> <a href="#" class="deleteAd" data-id="${advertisement.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="/informationCenter/advertisement?form">添加广告</a>
</div>
