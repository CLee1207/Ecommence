<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
    table,th, td{
        border: 1px solid black
    }
</style>
<div>
    <h1>订单详情</h1><br/>
    拍下宝贝：<fmt:formatDate value="${orderItem.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/>
    卖家发货：<fmt:formatDate value="${orderItem.deliverTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/>
    确认收货：<fmt:formatDate value="${orderItem.receiveTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/>
    <h3>*******************************************订单详情**************************************************</h3>
    收获地址：${orderItem.orderAddress.state} ${orderItem.orderAddress.city} ${orderItem.orderAddress.street}
    <h3>*******************************************卖家信息**************************************************</h3>

    昵称：${orderItem.buyer.name}
    联系电话：
    地址：
    <h3>*******************************************订单信息**************************************************</h3>

    订单编号：${orderItem.orderNo}
    <h3>*******************************************商品详情**************************************************</h3>
    <table>
        <tr>
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>总价</td>
        </tr>
        <c:forEach items="${orderItem.lines}" var="orderLine">
            <tr>
                <td>${orderLine.item.name}</td>
                <td>${orderLine.quantity}</td>
                <td>${orderLine.price}</td>
                <td>${orderLine.sum}</td>
            </tr>
        </c:forEach>
    </table>
    总价${orderItem.summary}
</div>
