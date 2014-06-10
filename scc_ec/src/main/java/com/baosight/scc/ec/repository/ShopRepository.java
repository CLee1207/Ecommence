package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Shop;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zodiake on 2014/6/3.
 */
public interface ShopRepository extends PagingAndSortingRepository<Shop,String>{
}
