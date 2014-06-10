package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.repository.AddressRepository;
import com.baosight.scc.ec.service.AddressService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Charles on 2014/5/20.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository ar;

    @Override
    public Address save(Address address) {
        return ar.save(address);
    }

    @Override
    public Address findById(String id) {
        return ar.findOne(id);
    }

    @Override
    public List<Address> findByUser(EcUser ecUser) {
        return ar.findByUser(ecUser);
    }

}
