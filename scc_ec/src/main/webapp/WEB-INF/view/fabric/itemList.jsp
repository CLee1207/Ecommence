<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/14
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>

<!-- /.Sider Bar -->


<!-- /.Detail Bread -->
<div class="bread detail-bread">
    <span>您的位置：</span>
    <ul>
        <li><a href="#">首页</a></li>
        <li><a href="#">我的平台</a></li>
        <li><a href="#">我的求购单列表</a></li>
    </ul>
</div>
<!-- /.Detail Bread -->

<!-- /.Detail Table -->
<div class="detail-table">
    <select>
        <option>草稿</option>
        <option>出售中</option>
        <option>审核中</option>
        <option>下架</option>
    </select>
    <table class="table table-border">
        <thead>
        <tr>
            <th>图片</th>
            <th>标题</th>
            <th width="20%">发布时间</th>
            <th>状态</th>
            <th width="20%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${grid.ecList}" var="item">
            <tr>
                <td>
                    <div class="central"><img src="/resource/pic/model.jpg"></div>
                </td>
                <td><a href="/${item.url}/${item.id}">${item.name}</a></td>
                <td><fmt:formatDate value="${item.createdTime.time}"
                                    pattern="yyyy-mm-dd hh:mm:ss"></fmt:formatDate></td>
                <td>${item.state}</td>
                <td align="center">
                    <c:if test="${item.state=='草稿'}">
                        <a type="button" class="button button-deep">编辑</a>
                        <button type="button" class="button button-deep">删除</button>
                    </c:if>
                    <c:if test="${item.state=='出售中'}">
                        <button type="button" class="button button-deep">下架</button>
                    </c:if>
                    <c:if test="${item.state=='下架'}">
                        <a type="button" class="button button-deep"
                           href="${context}/sellerCenter/${item.url}Edit?id=${item.id}">编辑</a>
                    </c:if>
                    <c:if test="${item.state==''}"></c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="text-right">
        <c:if test="${grid.totalPages>0}">
            <ul class="pagination">
                <li><a href="#">首页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                    <li <c:if test='${grid.currentPage == status.current}'>class="active"</c:if>>
                        <a href="${context}/sellerCenter/items?type=${type}&page=${grid.currentPage}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a href="#">下一页</a></li>
            </ul>
        </c:if>
    </div>
</div>
<!-- /.Detail Info -->


<!-- /.Container -->

