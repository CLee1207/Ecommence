package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.type.MaterialState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.model.MaterialCategory;


public interface MaterialRepository extends PagingAndSortingRepository <Material,String>{

	//根据辅料id，查询辅料信息
	Material findById(String id);
	
	//根据辅料供应商，辅料名称，查询辅料分页列表
	Page<Material> findByCreatedByAndName(EcUser user,String mName,Pageable pageable);
	
	//根据辅料供应商，辅料分类，查询辅料分页列表
	Page<Material> findByCreatedByAndCategory(EcUser user,MaterialCategory mCategory,Pageable pageable);
	
	//根据辅料供应商，查询辅料分页列表
	Page<Material> findByCreatedBy(EcUser user,Pageable pageable);

    Page<Material> findByCreatedByAndState(EcUser user,MaterialState state,Pageable pageable);

}
