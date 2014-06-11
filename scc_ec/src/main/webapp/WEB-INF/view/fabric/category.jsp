<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- /.box -->
<dl class="commodity-list box">
    <dt>商品分类</dt>
    <dd>
							<span>
								<i class="icon icon-minus">
                                </i>
                                面料
							</span>
        <ul>
            <c:forEach var="category" items="${categories}">
                <li>
                    <a data-id="${category.id}">${category.name}</a>
                </li>
            </c:forEach>
            <li>
                <a href="#">染色类</a>
                <div>
                    <a href="#">常规染色</a>
                </div>
            </li>
            <li><a href="#">色织类</a></li>
            <li><a href="#">印花类</a></li>
            <li><a href="#">提花类</a></li>
            <li><a href="#">纱线工艺</a></li>
            <li><a href="#">常见面料</a></li>
        </ul>

    </dd>
</dl>
<!-- /.box -->

