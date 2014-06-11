package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.CompositeScore;
import com.baosight.scc.ec.model.EcUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by sam on 2014/6/11.
 */
public interface CompositeRepository extends PagingAndSortingRepository<CompositeScore,Integer> {
    public CompositeScore findByUser(EcUser user);
}
