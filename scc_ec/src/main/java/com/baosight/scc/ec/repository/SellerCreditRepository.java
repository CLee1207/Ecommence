package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SellerCredit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by sam on 2014/6/6.
 */
public interface SellerCreditRepository extends PagingAndSortingRepository<SellerCredit,Integer> {

    List<SellerCredit> findByUser(EcUser user);

}
