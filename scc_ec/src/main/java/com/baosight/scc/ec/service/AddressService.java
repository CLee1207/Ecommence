package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;

import java.util.List;

/**
 * 收货地址管理
 * Created by Charles on 2014/5/20.
 */
public interface AddressService {
    /**
     * 收货地址save方法
     * @param address
     * @return
     */
    Address save(Address address);

    /**
     * 根据id查找收货地址
     * @param id
     * @return
     */
    Address findById(String id);

    /**
     * 查询当前用户的收货地址列表
     * @param ecUser
     * @return
     */
    List<Address> findByUser(EcUser ecUser);
}
