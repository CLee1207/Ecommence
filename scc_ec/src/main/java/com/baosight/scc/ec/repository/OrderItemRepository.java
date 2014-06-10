package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.criteria.Order;

/**
 * Created by Charles on 2014/5/16.
 */
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem,String> {
    Page<OrderItem> findByBuyer(EcUser buyer,Pageable pageable);
    Page<OrderItem> findBySeller(EcUser seller,Pageable pageable);
    Page<OrderItem> findByBuyerAndStatusNotLike(EcUser buyer,String status,Pageable pageable);
}
