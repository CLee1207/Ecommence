package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.web.ItemJSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Charles on 2014/5/19.
 */
public interface ItemService {

    /**
     * 查询单个商品
     * @param id
     * @return
     */
    Item findById(String id);

    /**
     * 判断产品类型，0：面料，1：辅料
     * @param proId 产品id
     * @return
     */
    int checkItemType(String proId);

    /**
     * 根据用户id、二级分类id，查询该用户下下的所有产品列表
     * @param uid 用户id
     * @param type 分类类型，0：面料分类，1辅料分类
     * @param secondCategoryId 二级分类id
     * @param pageable
     * @return
     */
    Page<Item> findByUserIdAndCategoryIdAndType(String uid,String secondCategoryId,Integer type,Pageable pageable);

    int itemTypeById(String id);
    
    public List<ItemJSON> findTop4ByCreatedByOrderByBidCount(EcUser user);

    public Page<Item> findByCreatedBy(EcUser user ,Pageable pageable);

    public Page<Item> findByCreatedByAndState(EcUser user ,ItemState state,Pageable pageable);
}
