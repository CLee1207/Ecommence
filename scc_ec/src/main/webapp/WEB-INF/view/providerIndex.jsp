<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<h2>供应商主页</h2>
        <table border="1" align="center">
            <tr>
                <td>产品id</td>
                <td>产品名称</td>
                <td>价格</td>
            </tr>
        <c:forEach var="c" items="${grid.ecList}">
            <tr>
                <td>${c.id}</td>
                <td>${c.name}</td>
                <td>${c.price}</td>
            </tr>
        </c:forEach>
    </table>
    面料一级分类：
    <c:forEach var="m" items="${materialCategories}">
        ${m.id}<br/>
        ${m.name}<br/>
    </c:forEach>
    辅料一级分类：
    <c:forEach var="f" items="${fabricCategories}">
        ${f.id}<br/>
        ${f.name}<br/>

    </c:forEach>
	</body>
</html>
