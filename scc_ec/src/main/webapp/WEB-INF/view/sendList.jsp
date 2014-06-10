<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<h2>发出的评价内容</h2>
        <table border="1" align="center">
            <tr>
                <td>好评</td>
                <td>评价内容</td>
                <td>产品名称</td>
                <td>评论人</td>
            </tr>
        <c:forEach var="c" items="${grid.ecList}">
            <tr>
                <td>${c.type}</td>
                <td>${c.content}</td>
                <td>${c.item.name}</td>
                <td>${c.user.name}</td>
            </tr>
        </c:forEach>
    </table>
	</body>
</html>
