package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.FavouriteShopsRepository;
import com.baosight.scc.ec.service.FavouriteShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created by zodiake on 2014/6/3.
 */
@Service
@Transactional
public class FavouriteShopServiceImpl implements FavouriteShopService {
    @Autowired
    private FavouriteShopsRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<FavouriteShops> findByUser(EcUser user, Pageable pageable) {
        Page<FavouriteShops> page = repository.findByUser(user, pageable);
        for (Iterator<FavouriteShops> i = page.iterator(); i.hasNext(); ) {
            FavouriteShops favouriteShops = i.next();
            Shop shop = favouriteShops.getShop();
            EcUser u = shop.getUser();
            TypedQuery<Item> query = em.createNamedQuery("item.findByUser", Item.class);
            query.setParameter("user", u);
            List<Item> items = query.setMaxResults(4).getResultList();
            for (Item im : items) {
                if (im instanceof Fabric) {
                    im.setUrl(Fabric.class.getName());
                } else if (im instanceof Material) {
                    im.setUrl(Material.class.getName());
                }
            }
            shop.setNewestItem(items);
        }
        return page;
    }

    @Override
    public FavouriteShops save(EcUser user, Shop shop) {
        FavouriteShops favouriteShops=new FavouriteShops();
        favouriteShops.setShop(shop);
        favouriteShops.setUser(user);
        FavouriteShops source=repository.save(favouriteShops);
        return source;
    }

    @Override
    public boolean countByUserAndShop(EcUser user, Shop shop) {
        return repository.countByUserAndShop(user,shop)>0;
    }

    @Override
    public void delete(FavouriteShops favouriteShops) {
        repository.delete(favouriteShops);
    }
}
