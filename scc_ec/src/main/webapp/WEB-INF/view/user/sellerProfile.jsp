<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/11
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <form:form modelAttribute="user" action="/userCenter/profile?form" method="post">
        <label>卖家信息维护</label><br/>
        联系人：<form:input path="name"></form:input><br/>
        固定电话：<br/>
        手机：<br/>
        传真：<br/>
        邮箱：<br/>
        主营业务：<br/>
        主营产品或服务：<br/>
        公司图片：<br/>
        公司介绍：<br/>
        <input type="submit" value="保存" name="_target1" />
        <input type="button" value="取消" name="_cancel"  id="cancelBtn"/>
    </form:form>
</div>