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
		来自买家的评价
		<table>
			<tr>
				<td>好评</td>
				<td>评价内容</td>
				<td>评价人</td>
				<td>宝贝信息</td>
			</tr>
			<c:forEach var="o" items="${grid.ecList }">
				<tr>
					<td>${o.type }</td>
					<td>${o.content }</td>
					<td>${o.user.name }</td>
					<td>${o.item.name}</td>
				</tr>
			</c:forEach>
		</table>
		
	</body>
</html>
