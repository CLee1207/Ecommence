package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteItems;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.repository.FavouriteItemsRepository;
import com.baosight.scc.ec.service.FavouriteItemsService;
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
 * Created by zodiake on 2014/5/30.
 */
@Service
@Transactional
public class FavouriteItemsServiceImpl implements FavouriteItemsService{
    @Autowired
    private FavouriteItemsRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addFavourite(EcUser u,Item item) {
        FavouriteItems favouriteItems=new FavouriteItems(u,item);
        repository.save(favouriteItems);
    }

    @Override
    public void removeFavourite(EcUser u,Item item){
        FavouriteItems favouriteItems=new FavouriteItems(u,item);
        repository.delete(favouriteItems);
    }

    @Override
    public Page<FavouriteItems> findByUser(EcUser user,Pageable pageable){
        TypedQuery<FavouriteItems> query=em.createNamedQuery("FavouriteItems_findByUser", FavouriteItems.class);
        query.setParameter("user",user);
        List<FavouriteItems> list=query.getResultList();
        TypedQuery<Long> countQuery=em.createNamedQuery("FavouriteItems_countByUser", Long.class);
        countQuery.setParameter("user",user);
        Long i=countQuery.getSingleResult();
        Page<FavouriteItems> page=new PageImpl<FavouriteItems>(list,pageable,i);
        return page;
    }

    @Override
    public boolean countByItemAndUser(Item item, EcUser user) {
        return repository.countByItemAndUser(item,user)>0;
    }

    @Override
    public void deleteFavourite(Item item, EcUser user) {
        FavouriteItems favouriteItems=repository.findByItemAndUser(item,user);
        em.remove(favouriteItems);
    }
}
