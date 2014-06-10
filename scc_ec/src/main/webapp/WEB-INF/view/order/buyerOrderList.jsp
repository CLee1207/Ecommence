<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
    table,th, td{
        border: 1px solid black
    }
</style>
<h1>我的平台——订单列表</h1>
<table>
<tr>
    <th>商品</th>
    <th>单价 (元)</th>
    <th>数量</th>
    <th>总额(元)</th>
    <th>交易状态</th>
    <th>操作</th>
</tr>
<c:forEach var="orderItem" items="${grid.ecList}">
    <tr>
        <td colspan="2">订单编号：${orderItem.orderNo}</td>
        <td colspan="2">${orderItem.seller.name}</td>
        <td colspan="2">交易时间：<fmt:formatDate value="${orderItem.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
    </tr>
        <tr>
            <c:forEach items="${orderItem.lines}" var="orderLine">
                <td> ${orderLine.item.name}</td>
                <td>${orderLine.price}</td>
                <td>${orderLine.quantity}</td>
                <td>${orderLine.sum}</td>

            <td>
                <c:choose>
                    <c:when test="${orderItem.status == 'WAIT_GOODS_SEND'}">等待卖家发货</c:when>
                    <c:when test="${orderItem.status == 'BUYER_CANCEL'}">已取消</c:when>
                    <c:when test="${orderItem.status == 'SELLER_CANCEL'}">卖家已取消</c:when>
                    <c:when test="${orderItem.status == 'GOODS_DELIVER'}">卖家已发货</c:when>
                    <c:when test="${orderItem.status == 'GOODS_RECEIVE'}">已确认收货</c:when>
                    <c:when test="${orderItem.status == 'BUYER_APPRAISE'}">买家已评价</c:when>
                    <c:when test="${orderItem.status == 'SELLER_APPRAISE'}">卖家已评价</c:when>
                    <c:when test="${orderItem.status == 'FINISH'}">双方已评价</c:when>
                </c:choose>
                <a href="/orderCenter/buyerViewOrder/${orderItem.id}">订单详情</a>
            </td>
            <td>
                <c:choose>
                    <c:when test="${orderItem.status == 'WAIT_GOODS_SEND'}"><input type="button" class="optStatus"  data-id="${orderItem.id}" data-status="1" value="取消"></c:when>
                    <c:when test="${orderItem.status == 'GOODS_DELIVER'}"><input type="button" class="optStatus"  data-id="${orderItem.id}" data-status="4" value="确认收货"></c:when>
                    <c:when test="${orderItem.status == 'GOODS_RECEIVE'}"><input type="button" class="aLabel"  data-id="${orderItem.id}" value="评价"></c:when>
                </c:choose>
            </td>
        </tr>
            </c:forEach>
    <tr><td colspan="6">**************************************************************</td></tr>
</c:forEach>
</table>
<a href="/sellerCenter/fabrics">去购物</a>