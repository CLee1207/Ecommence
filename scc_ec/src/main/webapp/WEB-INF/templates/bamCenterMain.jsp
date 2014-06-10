<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/3
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <title>我的平台</title>
        <script type="text/javascript" src="${context}/resources/js/jquery.min.js"></script>
        <tiles:importAttribute name="script"/>
        <c:forEach var="item" items="${script}">
            <script src="${context}${item}"></script>
        </c:forEach>
    </head>
<body>
    <ul>
        <li><a href="/informationCenter/advertisementPosition">广告栏位设置</a></li>
        <li><a href="/informationCenter/advertisement">广告内容设置</a></li>
        <li><a href="/informationCenter/informationCategory">资讯分类设置</a></li>
        <li><a href="/informationCenter/information">资讯内容设置</a></li>
        <li><a href="/systemConfig/firstCategory">面料一级分类维护</a></li>
        <li><a href="/systemConfig/secondCategory">面料二级分类维护</a></li>
        <li><a href="/systemConfig/firstSource">面料原料一级分类维护</a></li>
        <li><a href="/systemConfig/secondSource">面料原料二级分类维护</a></li>
        <li><a href="/systemConfig/materialFirstCategory">辅料一级分类维护</a></li>
        <li><a href="/systemConfig/materialSecondCategory">辅料二级分类维护</a></li>
    </ul>
    <tiles:insertAttribute name="main"></tiles:insertAttribute>
</body>
</html>
