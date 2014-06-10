package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.DemandOrder;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.type.DemandOrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zodiake on 2014/5/22.
 */
public interface DemandOrderService {
    public DemandOrder findOne(String id);

    public DemandOrder findByIdAndUser(String id, EcUser user);

    public DemandOrder save(DemandOrder demandOrder);

    public DemandOrder update(DemandOrder demandOrder);

    public Page<DemandOrder> findByUserAndState(EcUser u, DemandOrderState state, Pageable pageable);

    public Page<DemandOrder> findByUser(EcUser u, Pageable pageable);

    public List<DemandOrder> findAll();

    public List<DemandOrder> findTopN(int len);
}
