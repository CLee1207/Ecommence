<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/9
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>供应链</title>
    <link type="text/css" rel="stylesheet" media="screen" href="${context}/resources/css/action.css"/>
    <!--[if lt IE 9]>
    <script src="${context}/resources/js/html5shiv.js"></script>
    <script src="${context}/resources/js/respond.min.js"></script>
    <![endif]-->
    <script src="${context}/resources/js/jquery.min.js"></script>
    <tiles:importAttribute name="script"/>
    <c:forEach items="${script}" var="script">
        <script src="${context}${script}"></script>
    </c:forEach>
</head>

<body>
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<div class="container">
    <div class="row-12">
        <div class="col-3">
            <tiles:insertAttribute name="menu"/>
        </div>
        <div class="col-9">
            <tiles:insertAttribute name="main"/>
        </div>
    </div>
    <tiles:insertAttribute name="newItem"/>
</div>
<tiles:insertAttribute name="footer"/>
</body>
</html>

