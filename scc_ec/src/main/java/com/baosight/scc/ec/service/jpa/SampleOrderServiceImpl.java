package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SampleOrder;
import com.baosight.scc.ec.repository.SampleOrderRepository;
import com.baosight.scc.ec.service.SampleOrderService;
import com.baosight.scc.ec.type.SampleOrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by zodiake on 2014/5/28.
 */
@Service
public class SampleOrderServiceImpl implements SampleOrderService {
    @Autowired
    private SampleOrderRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public SampleOrder findByIdAndCreator(String id, EcUser user) {
        SampleOrder order = repository.findByIdAndCreator(id, user);
        order.getItem();
        return order;
    }

    @Override
    public SampleOrder save(SampleOrder sampleOrder) {
        sampleOrder.setId(GuidUtil.newGuid());
        return repository.save(sampleOrder);
    }

    @Override
    public SampleOrder update(SampleOrder sampleOrder) {
        return repository.save(sampleOrder);
    }


    @Override
    public Page<SampleOrder> findByItemCreatedBy(EcUser user, Pageable pageable) {
        TypedQuery<SampleOrder> query=em.createNamedQuery("sampleOrder.findByItemCreatedBy", SampleOrder.class).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        query.setParameter(1,user);
        List<SampleOrder> lists=query.getResultList();
        TypedQuery<Long> countQuery=em.createNamedQuery("sampleOrder.countByItemCreatedBy", Long.class);
        countQuery.setParameter(1,user);
        Long i=countQuery.getSingleResult();
        Page<SampleOrder> pageImpl=new PageImpl<SampleOrder>(lists,pageable,i);
        return pageImpl;
    }

    @Override
    public Page<SampleOrder> findByStateAndItemCreatedBy(SampleOrderState state,EcUser user, Pageable pageable) {
        TypedQuery<SampleOrder> query=em.createNamedQuery("sampleOrder.findByStateAndItemCreatedBy", SampleOrder.class).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        query.setParameter(1,state);
        query.setParameter(2,user);
        List<SampleOrder> lists=query.getResultList();
        TypedQuery<Long> countQuery=em.createNamedQuery("sampleOrder.countByStateAndItemCreatedBy", Long.class);
        countQuery.setParameter(1,state);
        countQuery.setParameter(2,user);
        Long i=countQuery.getSingleResult();
        Page<SampleOrder> pageImpl=new PageImpl<SampleOrder>(lists,pageable,i);
        return pageImpl;
    }

    @Override
    public Page<SampleOrder> findByCreatedBy(EcUser user, Pageable pageable) {
        return repository.findByCreator(user, pageable);
    }

    @Override
    public Page<SampleOrder> findByStateAndCreatedBy(EcUser user, SampleOrderState state, Pageable pageable) {
        return repository.findByCreatorAndState(user,state,pageable);
    }

    @Override
    public List<SampleOrder> findAll() {
        return Lists.newArrayList(repository.findAll());
    }
}
