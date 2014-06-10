package com.baosight.scc.ec.service;

import java.util.List;

import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.model.OrderLine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Charles on 2014/5/16.
 */
public interface OrderLineService {
    /**
     * 订单内商品保存
     * @param orderLine
     * @return
     */
    OrderLine save(OrderLine orderLine);

    /**
     * 订单详情商品分页列表
     * @param item
     * @param pageable
     * @return
     */
    Page<OrderLine> findByItem(OrderItem item,Pageable pageable);
    
    List<OrderLine> findTest();
}
