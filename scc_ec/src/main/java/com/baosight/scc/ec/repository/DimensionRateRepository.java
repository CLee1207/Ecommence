package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.DimensionRate;
import com.baosight.scc.ec.model.EcUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by sam on 2014/6/5.
 */
public interface DimensionRateRepository extends PagingAndSortingRepository<DimensionRate,Integer> {

    DimensionRate findBySeller(EcUser user);
}
