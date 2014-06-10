package com.baosight.scc.ec.service.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.model.OrderLine;
import com.baosight.scc.ec.repository.OrderLineRepository;
import com.baosight.scc.ec.service.OrderLineService;
import com.baosight.scc.ec.utils.GuidUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Charles on 2014/5/16.
 */
@Service
@Transactional
public class OrderLineServiceImpl implements OrderLineService{
	@Autowired
    private OrderLineRepository or;
	@PersistenceContext
    private EntityManager em;
    

    @Override
    public OrderLine save(OrderLine orderLine) {
        orderLine.setId(GuidUtil.newGuid());
        return or.save(orderLine);
    }

    @Override
    public Page<OrderLine> findByItem(OrderItem item,Pageable pageable) {
        return or.findOrderByItem(item,pageable);
    }
    
    @Transactional(readOnly=true)
    public List<OrderLine> findTest(){
    	return em.createNamedQuery("OrderLine.findTest", OrderLine.class).getResultList();
    }
}
