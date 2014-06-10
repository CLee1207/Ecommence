<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/6
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <form:form modelAttribute="firstCategory">
        <label>一级分类</label><br/>
        产品类别：<form:input path="name"></form:input><br/>
        是否有效：
        <form:select path="isValid">
            <form:option value="0">是</form:option>
            <form:option value="1">否</form:option>
        </form:select><br/>
        已添加的产品分类：
        <div>
            <c:forEach var="materialCategory" items="${materialCategories}">
                ${materialCategory.name}、
            </c:forEach>
        </div>
        <input type="submit" value="保存" name="_target1" />
        <input type="button" value="取消" name="_cancel"  id="cancelBtn"/>
    </form:form>
</div>
