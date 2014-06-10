package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteItems;
import com.baosight.scc.ec.model.FavouriteItems.Id;
import com.baosight.scc.ec.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zodiake on 2014/5/30.
 */
public interface FavouriteItemsRepository extends PagingAndSortingRepository<FavouriteItems,Id>{
    public Page<FavouriteItems> findByUser(EcUser user,Pageable pageable);
    public Long countByItemAndUser(Item item ,EcUser user);
    public FavouriteItems findByItemAndUser(Item item,EcUser user);
}
