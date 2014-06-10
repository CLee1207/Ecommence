<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/3
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <form:form modelAttribute="advertisement">
        <label>广告维护</label><br/>
        广告名称：<form:input path="title"></form:input><br/>
        广告栏位：
        <select id="position_id" name="position_id">
            <c:forEach var="position" items="${advertisementPositionList}">
                <option value="${position.id}">${position.name}</option>
            </c:forEach>
        </select><br/>
        广告产品关联：<form:input path="link"></form:input>请输入相关产品或店铺的URL地址<br/>
        封面图片：<form:input path="coverPath"></form:input><br/>
        <input type="submit" value="保存" name="_target1" />
        <input type="button" value="取消" name="_cancel"  id="cancelBtn"/>
    </form:form>
</div>
