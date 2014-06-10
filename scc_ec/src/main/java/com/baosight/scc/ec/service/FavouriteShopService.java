package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteShops;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Created by zodiake on 2014/6/3.
 */
public interface FavouriteShopService {
    public Page<FavouriteShops> findByUser(EcUser user,Pageable pageable);
}
