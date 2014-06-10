package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.OrderAddress;

import java.util.List;

/**
 * 订单收货地址管理
 * Created by Charles on 2014/5/28.
 */
public interface OrderAddressService {
    /**
     * 收货地址save方法
     * @param address
     * @return
     */
    OrderAddress save(OrderAddress address);

    /**
     * 根据id查找收货地址
     * @param id
     * @return
     */
    OrderAddress findById(String id);
}
