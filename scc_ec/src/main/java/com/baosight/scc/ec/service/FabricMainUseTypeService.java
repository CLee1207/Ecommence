package com.baosight.scc.ec.service;

import com.baosight.scc.ec.type.FabricMainUseType;

import java.util.List;

/**
 * Created by zodiake on 2014/5/14.
 */
public interface FabricMainUseTypeService {
    FabricMainUseType findOne(String id);
    List<FabricMainUseType> findAll();
}
