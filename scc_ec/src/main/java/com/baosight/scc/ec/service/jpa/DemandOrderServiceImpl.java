package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.DemandOrder;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.repository.DemandOrderRepository;
import com.baosight.scc.ec.service.DemandOrderService;
import com.baosight.scc.ec.type.DemandOrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zodiake on 2014/5/22.
 */
@Service
@Transactional
public class DemandOrderServiceImpl implements DemandOrderService {
    @Autowired
    private DemandOrderRepository repository;
    @PersistenceContext
    private EntityManager em;
    private Logger logger = LoggerFactory.getLogger(DemandOrderServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public DemandOrder findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    public DemandOrder findByIdAndUser(String id, EcUser user) {
        return repository.findByIdAndCreatedBy(id, user);
    }

    @Override
    public DemandOrder save(DemandOrder demandOrder) {
        demandOrder.setId(GuidUtil.newGuid());
        em.persist(demandOrder);
        return demandOrder;
    }

    @Override
    public DemandOrder update(DemandOrder demandOrder) {
        return em.merge(demandOrder);
    }

    @Override
    public Page<DemandOrder> findByUserAndState(EcUser u, DemandOrderState state, Pageable pageable) {
        return repository.findByCreatedByAndState(u, state, pageable);
    }

    @Override
    public Page<DemandOrder> findByUser(EcUser u, Pageable pageable) {
        logger.debug(repository.findByCreatedBy(u, pageable).getContent().size() + "");
        logger.debug(repository.findByCreatedBy(u).size() + "--no page");
        return repository.findByCreatedBy(u, pageable);
    }

    @Override
    public List<DemandOrder> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public List<DemandOrder> findTopN(int len) {
        String querySql = "select id,title,content,createdTime,validDateTo from T_ec_DemandOrder " +
                " where current_timestamp <= validDateTo  order by createdTime desc limit 0,"+len;
        Query query = em.createNativeQuery(querySql);
        List result = new ArrayList();
        result = query.getResultList();
        List<DemandOrder> list = new ArrayList<DemandOrder>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        for (Iterator iterator = result.iterator();iterator.hasNext();){
            Object[] values = (Object[])iterator.next();
            DemandOrder demandOrder = new DemandOrder();
            demandOrder.setId((String)values[0]);
            demandOrder.setTitle((String) values[1]);
            demandOrder.setContent((String) values[2]);
            String createdTime = sdf.format((Timestamp)values[3]);
            String validDateTo = sdf.format((Timestamp)values[4]);
            try {
                Date d = sdf.parse(createdTime);
                calendar.setTime(d);
                demandOrder.setCreatedTime(calendar);
                d=sdf.parse(validDateTo);
                calendar.setTime(d);
                demandOrder.setValidDateTo(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(demandOrder);
        }
        return list;
    }
}
