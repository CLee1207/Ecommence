<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 14:20
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
    <h1>购物车查看页面</h1>
    <table>
        <tr>
            <th>全选</th>
            <th>产品</th>
            <th>单价 (元)</th>
            <th>数量</th>
            <th>总额(元)</th>
            <th>操作</th>
        </tr>

        <c:forEach var="cartLine" items="${cartLineList}">
            <tr class="data_${cartItem.itemId}">
                <td colspan="6">供应商:${cartLine.value[0].supplierName}</td>
            </tr>
            <c:forEach items="${cartLine.value}" var="cartItem">
                <tr class="dataTable_${cartItem.itemId}">
                    <td colspan="2">${cartItem.imgPath}${cartItem.title}</td>
                    <td><span id="${cartItem.itemId}_price">${cartItem.price}</span></td>
                    <td>
                        <span>
                            <a href="#" class="reduce" data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}">减</a>
                                <input type="text" class="quantity" id="${cartItem.itemId}_quantity"  data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}" value="${cartItem.quantity}">
                            <a href="#" class="increase" data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}">加</a>
                        </span>
                    </td>
                    <td><span id="${cartItem.itemId}_summary">${cartItem.summary}</span></td>
                    <td>
                        <a href="#" class="favor" data-id="${cartItem.itemId}">加入收藏夹</a>
                        <a href="#" class="delete" data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>

</div>
<a href="/sellerCenter/fabrics">继续购物</a>
<a href="/orderCenter/checkOut">提交订单</a>