package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.*;
import com.baosight.scc.ec.service.MaterialService;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.type.MaterialState;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;

@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialServiceImpl implements MaterialService {
	
	@Autowired
    private MaterialRepository fr;
	@Autowired
    private CommentRepository cr;
	@Autowired
    private EcUserRepository er;
	@Autowired
	private OrderLineRepository or;
	@Autowired
	private MaterialCategoryRepository mr;
    @PersistenceContext
    private EntityManager em;


	/**
	 * 辅料产品详细信息
	 * @param mid 辅料产品id
	 * 2014-5-16
	 * @author sam
	 */
	@Transactional(readOnly = true)
	public Material getMaterialInfo(String mid){
		Material mt = null;
		try {
			mt = fr.findById(mid);
			if(null == mt){
				mt = new Material();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mt;
	}
	
	/**
	 * 辅料交易评价记录
	 * @param item 辅料
	 * @param pageable
	 * @param type
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Comment> showMaterialComments(Material item,Pageable pageable,CommentType type){
		Page<Comment> page = null;
		if(type != null){
			page = cr.findByTypeAndItem(type,item,pageable);
		}else{
			page = cr.findByItem(item, pageable);
		}
		return page;
	}
	
	
	
	/**
	 * 根据辅料服务商id，分类id，搜产品
	 * @param mid 供应商id
	 * @param mCategoryId 分类id
	 * @author sam
	 */
	@Transactional(readOnly = true)
	public Page<Material> searchItemsByProAndCateId(String mid,String mCategoryId,Pageable pageable){
		//根据mid查询辅料服务商信息
		EcUser user = this.er.findById(mid);
		//根据mCategoryId查询分类信息
		MaterialCategory mcategory = this.mr.findOne(mCategoryId);
		Page<Material> page = null;
		//根据服务商和分类条件查询该服务商下辅料产品列表
		page = this.fr.findByCreatedByAndCategory(user, mcategory, pageable);
		return page;
	}

	/**
	 * 根据辅料服务商id，产品名称，搜产品
	 * @param mid 辅料供应商id
	 * @param mItemName 产品名称
	 * @param pageable
	 */
	@Transactional(readOnly = true)
	public Page<Material> searchItemsByProIdAndName(String mid,String mItemName,Pageable pageable){
		EcUser user = this.er.findById(mid);
		Page<Material> page = null;
		if(null != mItemName){
			page = this.fr.findByCreatedByAndName(user, mItemName, pageable);
		}else{
			page = this.fr.findByCreatedBy(user, pageable);
		}
		return page;
	}
	
	@Transactional(readOnly = true)
	public Page<Material> searhItems(String mid,String mItemName,String secondCategory,Pageable pageable){
		Page<Material> page = null;
		if(null != secondCategory){
			this.searchItemsByProAndCateId(mid, secondCategory, pageable);
		}else{
			this.searchItemsByProIdAndName(mid, mItemName, pageable);
		}
		return page;
	}
	
	/**
	 * 根据辅料供应商id，查询辅料分页列表
	 * @param proId 供应商id
	 * @param pageable 
	 * @return
	 */
	public Page<Material> getItemsByProviderId(String proId,Pageable pageable){
		EcUser user = this.er.findById(proId);
		return this.fr.findByCreatedBy(user, pageable);
	}

	@Override
    public Material save(Material material) {
        material.setId(GuidUtil.newGuid());
        EcUser sourUser=em.find(EcUser.class,material.getCreatedBy().getId());

        if (material.getImages().size() != 0)
            material.setCoverImage(material.getImages().get(0).getLocation());

        if (!sourUser.getPreferMaterialCategory().getCategories().contains(material.getCategory())) {
            sourUser.getPreferMaterialCategory().getCategories().add(material.getCategory());
        }
        // if ranges is not empty set price to the lowest ranges values
        if(material.getRanges().size()>0)
            material.setPrice(Collections.min(material.getRanges().values()));
        return fr.save(material);
    }

    @Override
    public Page<Material> findByCreatedBy(EcUser u, Pageable pageable) {
        return fr.findByCreatedBy(u,pageable);
    }

    /**
	 * 辅料交易记录
	 * @param item
	 * @param pageable
	 */
	@Transactional(readOnly = true)
	public Page<OrderLine> showMaterialOrders(Material item, Pageable pageable) {
		return or.findByItem(item, pageable);
	}


    @Override
    public Page<Material> findByCreatedByAndState(EcUser u, MaterialState state, Pageable pageable) {
        return null;
    }

    @Override
    public Material findOne(String id) {
        Material material=fr.findOne(id);
        if(material!=null) {
            material.getImages().size();
            material.getColors().size();
            material.getRanges().size();
        }
        return material;
    }

    @Override
    public Material update(Material material) {
        EcUser sourUser=em.find(EcUser.class,material.getCreatedBy().getId());

        if (material.getImages().size() != 0)
            material.setCoverImage(material.getImages().get(0).getLocation());

        if (!sourUser.getPreferMaterialCategory().getCategories().contains(material.getCategory())) {
            sourUser.getPreferMaterialCategory().getCategories().add(material.getCategory());
        }
        return fr.save(material);
    }
}
