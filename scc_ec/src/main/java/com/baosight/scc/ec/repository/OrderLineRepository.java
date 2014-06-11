package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.model.OrderLine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Charles on 2014/5/16.
 */
public interface OrderLineRepository extends PagingAndSortingRepository<OrderLine,String> {
    Page<OrderLine> findOrderByItem(OrderItem item ,Pageable pageable);
    
  //根据产品id，查看交易记录
  	Page<OrderLine> findByItem(Item item,Pageable pageable);

    Long countByItem(Item item);
}
