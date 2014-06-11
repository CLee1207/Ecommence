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
        </ul>

    </dd>
</dl>
<!-- /.box -->

