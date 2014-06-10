<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/3
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
        <label>广告维护</label><br/>
        广告名称：${advertisement.title}<br/>
        广告栏位：${advertisement.advertisementPosition.name}<br/>
        广告产品关联：${advertisement.link}<br/>
        封面图片：${advertisement.coverPath}<br/>
</div>
