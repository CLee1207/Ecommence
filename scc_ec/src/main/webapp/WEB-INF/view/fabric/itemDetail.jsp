<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- /.Detail Info -->
<div class="detail-info">
    <div class="row-2">
        <div class="col-1">
            <div class="magnifier">
                <div class="magnifier-view central"><img src="/resources/pic/designer.png"></div>
                <ul class="magnifier-menu">
                    <c:forEach items="${fabric.images}" var="image">
                        <li data-url="${image.location600}"><img src="${image.location300}"></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-1">
            <h1>全棉色织泡泡布</h1>
            <ul class="info-item">
                <li>
                    <label>产品编码：</label>
                    <span>1308121152182</span>
                </li>
                <li>
                    <label>起订量（码）：</label>
									<span>
                                        <c:forEach items="${fabric.showRanges}" var="range">
                                            ${range.key}
                                        </c:forEach>
									</span>
                </li>
                <li>
                    <label>价格：</label>
									<span>
                                        <c:forEach items="${fabric.ranges}" var="range">
                                            <bdo><b class="price orange font-20"> ${range.value}</b>/码</bdo>
                                        </c:forEach>
									</span>
                </li>
                <li>
                    <label>发货/物流：</label>
                    <span>浙江绍兴</span>
                </li>
                <li>
                    <label>成交/评价：</label>
                    <span>4米成交</span>
                </li>
            </ul>
            <div class="row-2">
                <span class="col-1"><a class="button button-default" href="#">立即订购</a></span>
                <span class="col-1"><a class="button button-default" href="#">加入购物车</a></span>
                <span class="col-1"><a class="button button-default" href="#">加入收藏夹</a></span>
                <span class="col-1"><a class="button button-default" href="#">调样</a></span>
            </div>
        </div>
    </div>
</div>
<!-- /.Detail Info -->

