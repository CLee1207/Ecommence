<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
		<script>
			
		</script>
	</head> 
	<body>
		<h2>辅料供应商页面 !!!</h2>
		<c:forEach var="m" items="${grid.ecList }">
		${m.name }<br/>
		${m.createdBy.name }<br/>
		${m.createdBy.id }<br/>
		</c:forEach>
		
		<c:forEach var="l" items="${firstCategoryList }">
			${l.id }<br/>
		<a href="/materialCategory/${l.id }">	${l.name }</a>
		</c:forEach>
	</body>
</html>
