package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.*;
import com.baosight.scc.ec.repository.FabricRepository;
import com.baosight.scc.ec.repository.ItemRepository;
import com.baosight.scc.ec.repository.MaterialRepository;
import com.baosight.scc.ec.service.ItemService;

import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.web.ItemJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charles on 2014/5/19.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemRepository ir;
    @Autowired
    private MaterialRepository mr;
    @Autowired
    private FabricRepository fr;
    @Autowired
    private EcUserRepository er;
    @Autowired
    private FabricCategoryRepository fabricCategoryRepository;
    @Autowired
    private  MaterialCategoryRepository materialCategoryRepository;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Item findById(String id) {
        return ir.findOne(id);
    }

    /**
     * 根据产品id，判断产品类型，0：表示面料，1：表示辅料,2:表示不存在
     * @param id
     * @return
     * @author sam
     */
    public int itemTypeById(String id){
    	int flag = 2;
    	Item item = null;
    	Material material = null;
    	Fabric fabric = null;
    	material = this.mr.findOne(id);
    	if(null != material){
    		flag = 1; 
    	}else{
    		fabric = this.fr.findOne(id);
    		if(null != fabric){
    			flag = 0;
    		}
    	}
    	return flag;
    }

    @Override
    public List<ItemJSON> findTop4ByCreatedByOrderByBidCount(EcUser user) {
        /*
        Sort sort = new Sort(Sort.Direction.DESC, "bidCount");
        PageRequest pageRequest = new PageRequest(0, 4, sort);
        Page<Item> items=ir.findByCreatedBy(user,pageRequest);
        return Lists.newArrayList(items);
        */
        Query query=em.createQuery("select i from Item i where i.createdBy=:user order by i.bidCount desc");
        query.setParameter("user",user);
        List<Item> list=query.setMaxResults(4).getResultList();
        List<ItemJSON> result=new ArrayList<ItemJSON>();
        for(Item i:list){
            if(i instanceof Fabric)
                i.setUrl(Fabric.class.getName());
            else if(i instanceof Material)
                i.setUrl(Material.class.getName());
            result.add(new ItemJSON(i));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findByCreatedBy(EcUser user, Pageable pageable) {
        return ir.findByCreatedBy(user,pageable);
    }

    @Override
    public int checkItemType(String proId) {
        Material material = null;
        material = mr.findOne(proId);
        if (null != material){
            return 1;
        }
        return 0;
    }

    @Override
    public Page<Item> findByUserIdAndCategoryIdAndType(String uid, String secondCategoryId, Integer type, Pageable pageable) {
        Page<Item> page = null;
        EcUser user = er.findOne(uid);
        if (secondCategoryId == null)
            page = this.ir.findByCreatedBy(user,pageable);
        else {
            if (type == 0){
                FabricCategory fabricCategory = this.fabricCategoryRepository.findOne(secondCategoryId);
                page = (Page)this.fr.findByCreatedByAndCategory(user,fabricCategory,pageable);
            }else{
                MaterialCategory materialCategory = this.materialCategoryRepository.findOne(secondCategoryId);
                page = (Page)this.mr.findByCreatedByAndCategory(user,materialCategory,pageable);
            }
        }

        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findByCreatedByAndState(EcUser user, ItemState state, Pageable pageable) {
        return ir.findByCreatedByAndState(user,state,pageable);
    }

    @Override
    public Item updateState(Item item) {
        Item source=em.find(Item.class,item.getId());
        source.setState(item.getState());
        return source;
    }

    @Override
    public Integer findOrderStatusCountByMid(String pid, String status) {
        String sql = "select count(a.item_id) from T_ec_Item i inner join T_ec_OrderLine a on i.id=a.item_id " +
                "inner join T_ec_OrderItem o on o.id=a.orderItem_id " +
                "where o.status = '"+status+"' and i.id = '" + pid + "' and o.createdTime <= current_timestamp - 30 ";
        Query query = em.createNativeQuery(sql);
        Integer count = (Integer)query.getSingleResult();
        return count;
    }
}
