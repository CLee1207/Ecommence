package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.type.ItemState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Charles on 2014/5/19.
 */
public interface ItemRepository extends PagingAndSortingRepository<Item, String> {
    List<Item> findByCreatedBy(EcUser user);

    Page<Item> findByCreatedBy(EcUser user, Pageable pageable);

    Page<Item> findByCreatedByAndState(EcUser user, ItemState state, Pageable pageable);
}
