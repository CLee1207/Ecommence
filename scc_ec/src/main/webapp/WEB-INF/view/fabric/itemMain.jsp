<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- /.Detail Main -->
<dl class="detail-main">
    <dt>
    <ul class="list-inline">
        <li>详细信息</li>
        <li id="bidList" data-id="${fabric.id}">成交记录（<bdo>90</bdo>）</li>
        <li>交易评价（<bdo>90</bdo>）</li>
    </ul>
    </dt>
    <dd class="particularity">
        ${fabric.content}
    </dd>
    <dd>
    </dd>
    <dd>
        <table class="table table-border table-stripe">
            <caption>30天内：交易中<bdo class="deep">51</bdo>交易成功<bdo class="deep">41</bdo></caption>
            <thead>
            <tr>
                <th><input type="radio" class="radio">全部</th>
                <th><input type="radio" class="radio">好评(<bdo>22</bdo>)</th>
                <th><input type="radio" class="radio">中评(<bdo>341</bdo>)</th>
                <th><input type="radio" class="radio">差评(<bdo>61</bdo>)</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>好评</td>
                <td>德川家康</td>
                <td>Label面料手感好，色牢度好，与商品描述一致，购物很开心，好评！</td>
                <td>2014-09-03 12:12:22</td>
            </tr>
            <tr>
                <td>好评</td>
                <td>德川家康</td>
                <td>Label面料手感好，色牢度好，与商品描述一致，购物很开心，好评！</td>
                <td>
            <tr>
                <td>好评</td>
                <td>德川家康</td>
                <td>Label面料手感好，色牢度好，与商品描述一致，购物很开心，好评！</td>
                <td>
            <tr>
                <td>好评</td>
                <td>德川家康</td>
                <td>Label面料手感好，色牢度好，与商品描述一致，购物很开心，好评！</td>
                <td>
            <tr>
                <td>好评</td>
                <td>德川家康</td>
                <td>Label面料手感好，色牢度好，与商品描述一致，购物很开心，好评！</td>
                <td>
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
    </dd>
</dl>
<!-- /.Detail Main -->

