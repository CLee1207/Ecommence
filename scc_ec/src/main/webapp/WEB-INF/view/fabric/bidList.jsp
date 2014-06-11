<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-stripe table-hover">
    <caption>30天内：交易中<bdo class="deep">51</bdo>交易成功<bdo class="deep">41</bdo></caption>
    <thead>
    <tr>
        <th>买家</th>
        <th>拍下价格</th>
        <th>数量</th>
        <th>成交时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${grid.ecList}" var="item">
        <tr>
            <td>${item.orderItem.buyer.name}</td>
            <td><bdo class="price">${item.price}</bdo></td>
            <td>${item.quantity}</td>
            <td>2014-09-03 12:12:22</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="text-right">
    <ul class="pagination">
        <li><a href="#">上一页</a></li>
        <li><a href="#">1</a></li>
        <li><a href="#">2</a></li>
        <li><a class="disable" href="#">3</a></li>
        <li><a href="#">4</a></li>
        <li><a href="#">下一页</a></li>
    </ul>
</div>
