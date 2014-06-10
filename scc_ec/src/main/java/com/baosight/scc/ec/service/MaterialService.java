package com.baosight.scc.ec.service;

import com.baosight.scc.ec.type.MaterialState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baosight.scc.ec.model.Comment;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.model.OrderLine;
import com.baosight.scc.ec.type.CommentType;

/**辅料服务接口
 * 
 * @author sam
 *
 */
public interface MaterialService {
	
	/**
	 * 辅料产品详细信息
	 * @param mid 辅料产品id
	 * 2014-5-16
	 * @author sam
	 */
	public Material getMaterialInfo(String mid);
	
	/**
	 * 辅料交易评价记录
	 * @param item 辅料
	 * @param pageable
	 * @return
	 */
	public Page<Comment> showMaterialComments(Material item,Pageable pageable,CommentType type);
	
	/**
	 * 辅料交易记录
	 * @param item
	 * @param pageable
	 * @return
	 */
	public Page<OrderLine> showMaterialOrders(Material item,Pageable pageable);
	
	/**
	 * 辅料供应商店内产品列表
	 * @param mid 辅料供应商id
	 * @param mCategoryId 辅料分类id
	 * @param pageable
	 * 2014-5-16
	 * @author sam
	 */
	public Page<Material> searchItemsByProAndCateId(String mid,String mCategoryId,Pageable pageable);
	
	/**
	 * 辅料供应商店内产品搜索
	 * @param mid 辅料供应商id
	 * @param mItemName 辅料产品名称
	 * @param pageable
	 * 2014-5-16
	 * @author sam
	 */
	public Page<Material> searchItemsByProIdAndName(String mid,String mItemName,Pageable pageable);
	
	/**
	 * 辅料供应商店内产品搜索
	 * @param mid 辅料供应商id
	 * @param mItemName 辅料产品名称
	 * @param secondCategory 二级分类id
	 * @param pageable
	 * @return
	 */
	public Page<Material> searhItems(String mid,String mItemName,String secondCategory,Pageable pageable);
	
	/**
	 * 根据辅料供应商id，查询辅料分页列表
	 * @param proId 供应商id
	 * @param pageable 
	 * @return
	 */
	public Page<Material> getItemsByProviderId(String proId,Pageable pageable);

    public Material save(Material material);
    
    public Page<Material> findByCreatedBy(EcUser u,Pageable pageable);

    public Page<Material> findByCreatedByAndState(EcUser u,MaterialState state,Pageable pageable);

    public Material findOne(String id);

    public Material update(Material material);


 }
