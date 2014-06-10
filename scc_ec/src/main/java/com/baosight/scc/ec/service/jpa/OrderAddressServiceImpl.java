package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.OrderAddress;
import com.baosight.scc.ec.repository.OrderAddressRepository;
import com.baosight.scc.ec.service.OrderAddressService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Charles on 2014/5/28.
 */
@Service
@Transactional
public class OrderAddressServiceImpl implements OrderAddressService {
    @Autowired
    private OrderAddressRepository oar;
    @Override
    public OrderAddress save(OrderAddress address) {
        address.setId(GuidUtil.newGuid());
        return oar.save(address);
    }

    @Override
    public OrderAddress findById(String id) {
        return oar.findOne(id);
    }
}
