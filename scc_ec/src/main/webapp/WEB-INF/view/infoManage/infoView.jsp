<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/5
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div>
        <label>资讯详情</label><br/>
        名称：${information.title}<br/>
        分类：${information.informationCategory.categoryName}<br/>
        正文：${information.content}<br/>
        封面图片：${information.coverPath}<br/>
</div>
