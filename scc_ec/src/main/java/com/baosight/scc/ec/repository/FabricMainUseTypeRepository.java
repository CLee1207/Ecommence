package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.type.FabricMainUseType;

import java.util.List;

/**
 * Created by zodiake on 2014/5/19.
 */
public interface FabricMainUseTypeRepository {
    public List<FabricMainUseType> findAll();
    public FabricMainUseType findOne(String id);
}
