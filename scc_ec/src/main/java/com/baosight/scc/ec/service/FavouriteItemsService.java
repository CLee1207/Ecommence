package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteItems;
import com.baosight.scc.ec.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zodiake on 2014/5/30.
 */
public interface FavouriteItemsService {
    public void addFavourite(EcUser user, Item item);

    public void removeFavourite(EcUser u, Item item);

    public Page<FavouriteItems> findByUser(EcUser user, Pageable pageable);

    public boolean countByItemAndUser(Item item,EcUser user);

    public void deleteFavourite(Item item,EcUser user);
}
