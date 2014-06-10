package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.FabricCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zodiake on 2014/5/12.
 */
public interface FabricRepository extends PagingAndSortingRepository<Fabric,String>{
    Page<Fabric> findByCreatedBy(EcUser user ,Pageable pageable);
    Fabric findByIdAndCreatedBy(String id,EcUser u);
    
  //根据面料供应商，面料名称，查询面料分页列表
    Page<Fabric> findByCreatedByAndName(EcUser user,String mName,Pageable pageable);
    
  //根据辅料供应商，查询辅料分页列表
    Page<Fabric> findByCreatedByAndCategory(EcUser user,FabricCategory mCategory,Pageable pageable);
}
