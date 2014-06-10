package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Charles on 2014/5/20.
 */
public interface AddressRepository extends PagingAndSortingRepository<Address,String> {
    public List<Address> findByUser(EcUser ecUser);
}
