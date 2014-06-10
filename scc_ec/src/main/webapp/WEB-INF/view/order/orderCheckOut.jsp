<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
    table,th, td{
        border: 1px solid black
    }
</style>
<div>
    <a id="address" href="#">默认地址</a>
    <h1>收货相关信息</h1><br/>
    收货地址：${address.state} ${address.city} ${address.street} ${address.zipCode}<br/>
    发票信息：${address.receiverName}<br/>
    <h1>订单</h1>
    <table>
        <tr>
            <th>商品名称</th>
            <th>所在企业</th>
            <th>单价</th>
            <th>数量</th>
            <th>小计</th>
            <th>操作</th>
        </tr>

        <c:forEach var="cartLine" items="${cartLineList}">
            <c:forEach items="${cartLine.value}" var="cartItem">
                <tr class="dataTable_${cartItem.itemId}">
                    <td>${cartItem.title}</td>
                    <td>${cartItem.supplierName}</td>
                    <td>￥${cartItem.price}</td>
                    <td>${cartItem.quantity}</td>
                    <td>￥${cartItem.summary}</td>
                    <td><button class="reAddCart" data-id="${cartItem.itemId}" data-spid="${cartItem.supplierId}">放回购物车</button></td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>
<form action="/orderCenter/submitOrder" method="post">
    <input type="hidden" name="title" value="${address.receiverName}">
    <input type="hidden" name="addressId" value="${address.id}">
    <a href="/sellerCenter/fabrics">继续购物</a>
    <input type="submit" value="提交订单" name="_target1" />
</form>