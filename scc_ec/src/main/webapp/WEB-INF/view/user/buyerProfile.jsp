<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/11
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <form:form modelAttribute="user" action="/userCenter/profile?form" method="post">
        <label>买家信息维护</label><br/>
        昵称：<form:input path="name"></form:input><br/>
        上传头像：<br/>
        手机：<br/>
        邮箱：<br/>
        身份证照片：<br/>
        <input type="submit" value="保存" name="_target1" />
        <input type="button" value="取消" name="_cancel"  id="cancelBtn"/>
    </form:form>
</div>