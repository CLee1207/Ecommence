<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/5
  Time: 14:55
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
            <td>是否有效</td>
            <td>创建日期</td>
            <td>操作</td>
        </tr>
        <c:forEach var="informationCategory" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${informationCategory.id}">
                <td>${list.index+1}</td>
                <td>${informationCategory.categoryName}</td>
                <td><fmt:formatDate value="${informationCategory.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td>${informationCategory.isValid}</td>
                <td><a href="#" class="editInfoCate" data-id="${informationCategory.id}">edit</a><a href="#" class="deleteInfoCate" data-id="${informationCategory.id}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div>
    分类名称:<input type="text" id="categoryName" value="">
    <input type="hidden" id="id" value="">
    <button class="saveInfoCate">保存</button><button class="cancelInfoCate">取消</button>
</div>



