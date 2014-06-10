package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.CartLine;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Charles on 2014/5/16.
 */
public interface OrderItemService {
    /**
     * 订单创建方法
     * @param item
     * @return
     */
    OrderItem save(OrderItem item);

    /**
     * 查找当前用户作为买家的订单
     * @param buyer
     * @param pageable
     * @return
     */
    Page<OrderItem> findByBuyer(EcUser buyer,Pageable pageable);
    /**
     * 查找当前用户作为卖家的订单
     * @param seller
     * @param pageable
     * @return
     */
    Page<OrderItem> findBySeller(EcUser seller,Pageable pageable);

    /**
     * 查看订单详情
     * @param id
     * @return
     */
    OrderItem findById(String id);

    /**创建订单
     *
     * @param cartLineMap
     * @param addressId
     * @param orderTitle
     * @return
     */
    Map createOrder(Map<String,List<CartLine>> cartLineMap,String addressId,String orderTitle);

    /**
     * 更新订单状态
     * @param orderId
     * @return
     */
    String updateStatus(String orderId,String status);

    Page<OrderItem> findByBuyerAndStatusNotLike(EcUser buyer,String status,Pageable pageable);
}
