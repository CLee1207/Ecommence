<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<h2>买家评价</h2>

        <form:form modelAttribute="comment">
            好评：<form:input path="type"></form:input>
            产品id：<form:input path="item"></form:input>
            评价内容：<form:input path="content"></form:input>
            订单编号：<form:input path="orderItem"></form:input>
           <input type="submit" value="submit" />
       </form:form>
	</body>
</html>
