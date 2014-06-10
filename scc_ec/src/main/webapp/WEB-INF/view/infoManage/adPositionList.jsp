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
<div>
    <table class="adpList">
        <tr>
            <td>顺序号</td>
            <td>栏位编号</td>
            <td>栏位名称</td>
            <td>说明</td>
            <td>创建日期</td>
            <td>操作</td>
        </tr>
        <c:forEach var="advertisementPosition" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${advertisementPosition.id}">
                <td>${list.index+1}</td>
                <td><a href="#" class="viewAdp" data-id="${advertisementPosition.id}">${advertisementPosition.positionNo}</a></td>
                <td>${advertisementPosition.name}</td>
                <td>${advertisementPosition.description}</td>
                <td><fmt:formatDate value="${advertisementPosition.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td><a href="#" class="deleteAdp" data-id="${advertisementPosition.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    栏位编号:<input type="text" id="positionNo" value="">
    栏位编号:<input type="text" id="name" value="">
    说明:<input type="text" id="description" value="">
    <input type="hidden" id="id" value="">
    <button class="saveAdp">保存</button><button class="cancelAdp">取消</button><br/>
    启动加载的广告：
    <c:forEach var="adMap" items="${adMap}">
        <c:if test="${adMap.key == 'ewewewewe'}">
            <c:forEach var="ad" items="${adMap.value}">
                ${ad.title}/${ad.link}/${ad.coverPath}
            </c:forEach>
        </c:if>
    </c:forEach>
</div>



