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
<dl class="business-board box">
    <dt>商家信息</dt>
    <dd>
        <a class="central" href="#"><i></i><img src="/resources/pic/logo.jpg"></a>
        <ul>
            <li>
                <label>公司名称：</label>
                <span>${fabric.createdBy.companyName}</span>
            </li>
            <li>
                <label>公司地址：</label>
                <span>${fabric.createdBy.companyAddress}</span>
            </li>
            <li>
                <label>官方网站：</label>
                <span><a href="${fabric.createdBy.url}">${fabric.createdBy.url}</a></span>
            </li>
            <li>
                <label>综合评分：</label>
                <span><i class="icon-stars icon-stars-3"></i></span>
            </li>
        </ul>
    </dd>
</dl>
<!-- /.box -->
