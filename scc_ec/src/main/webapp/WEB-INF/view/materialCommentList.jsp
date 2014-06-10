<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<h2>welcome !!!</h2>
		<c:forEach var="comment" items="${materialComments.ecList}">
			${comment.content }<br/>
			${comment.item.name}<br/>
			<a href="/sellerCenter/material/1111?showComments&page=1">1</a>
			<a href="/sellerCenter/material/1111?showComments&page=2">2</a>
			<a href="/sellerCenter/material/1111?showComments&page=3">3</a>
		</c:forEach>
		
	</body>
</html>
