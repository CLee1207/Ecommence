<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/15
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>12</title>
        <script type="text/javascript" src="${context}/resources/js/jquery-1.11.1.min.js"></script>
        <tiles:importAttribute name="script"/>
        <c:forEach var="item" items="${script}">
            <script src="${context}${item}"></script>
        </c:forEach>
    </head>
<body>
    <tiles:insertAttribute name="main"></tiles:insertAttribute>
</body>
</html>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>供应链</title>
<link type="text/css" rel="stylesheet" media="screen" href="/resource/css/action.css" />
<!--[if lt IE 9]>
<script src="/resource/js/html5shiv.js"></script>
<script src="/resource/js/respond.min.js"></script>
<![endif]-->
</head>

<body>