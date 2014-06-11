package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.model.OrderLine;
import com.baosight.scc.ec.repository.OrderLineRepository;
import com.baosight.scc.ec.service.OrderLineService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

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

    @Override
    public boolean countByStateFinishAndItem(Item item) {
        TypedQuery<Long> query=em.createNamedQuery("OrderLine.countByStateAndItem",Long.class);
        query.setParameter("item",item);
        return query.getSingleResult()>0;
    }

    @Transactional(readOnly = true)
    public Page<OrderLine> showFabricOrdersByFid(String id, Pageable pageable) {
        Item item=new Item();
        item.setId(id);
        TypedQuery<OrderLine> query=em.createNamedQuery("OrderLine.findByItem",OrderLine.class);
        query.setParameter("item", item);
        List<OrderLine> lines=query.getResultList();
        Long count=or.countByItem(item);
        return new PageImpl<OrderLine>(lines,pageable,count);
    }
}
