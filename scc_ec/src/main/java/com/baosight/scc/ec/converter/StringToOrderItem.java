package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by sam on 2014/6/3.
 */
@AutoRegister
@Component
public class StringToOrderItem implements Converter<String,OrderItem> {
    @Autowired
    private OrderItemService orderItemService;
    @Override
    public OrderItem convert(String source) {
        return this.orderItemService.findById(source);
    }
}
