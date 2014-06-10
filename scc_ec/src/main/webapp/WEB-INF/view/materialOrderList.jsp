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
		辅料交易记录
		<table>
			<tr>
				<td>买家</td>
				<td>拍下价格</td>
				<td>数量</td>
				<td>成交时间</td>
			</tr>
			<c:forEach var="o" items="${mgrid.ecList }">
				<tr>
					<td>${o.orderItem.buyer.name }</td>
					<td>${o.price }</td>
					<td>${o.quantity }</td>
					<td>{o.createdTime}</td>
				</tr>
			</c:forEach>
		</table>
		
	</body>
</html>
