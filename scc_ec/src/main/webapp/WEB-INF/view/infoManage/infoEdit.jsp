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
<div>
    <form:form modelAttribute="information" method="post" action="/informationCenter/information?form">
        <label>资讯内容设置-编辑</label><br/>
        标题：<form:input path="title"></form:input><br/>
        资讯分类：
        <select id="category_id" name="category_id">
            <c:forEach var="category" items="${informationCategoryList}">
                <option value="${category.id}"<c:if test="${category.id == information.informationCategory.id}">selected="selected" </c:if>>${category.categoryName}</option>
            </c:forEach>
        </select><br/>
        资讯正文：<form:textarea path="content"></form:textarea><br/>
        封面图片：<form:input path="coverPath"></form:input><br/>
        <input type="hidden" id="id" name="id"value="${information.id}" >
        <input type="submit" value="保存" name="_target1" />
        <input type="button" value="取消" name="_cancel"  id="cancelBtn"/>
    </form:form>
</div>
