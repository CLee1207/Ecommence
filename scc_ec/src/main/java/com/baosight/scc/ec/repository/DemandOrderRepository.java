package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.DemandOrder;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.type.DemandOrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by zodiake on 2014/5/22.
 */
public interface DemandOrderRepository extends PagingAndSortingRepository<DemandOrder, String> {
    /*
        @Param id demandOrder pk
        @Param user currentUser
        @return demandOrder
     */
    public DemandOrder findByIdAndCreatedBy(String id, EcUser user);

    /*
        @param user currentUser
        @param state demandOrderState
        @param pageable
        @return Page<DemandOrder>
     */
    public Page<DemandOrder> findByCreatedByAndState(EcUser u, DemandOrderState state, Pageable pageable);

    /*
        @param user currentUser
        @param pageable
        @return Page<DemandOrder>
     */
    public Page<DemandOrder> findByCreatedBy(EcUser u, Pageable pageable);

    /*
        @param user currentUser
        @return List<DemandOrder>
     */
    public List<DemandOrder> findByCreatedBy(EcUser u);
}
