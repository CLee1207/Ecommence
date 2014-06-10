package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.repository.FabricMainUseTypeRepository;
import com.baosight.scc.ec.service.FabricMainUseTypeService;
import com.baosight.scc.ec.type.FabricMainUseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zodiake on 2014/5/14.
 */
@Service
public class FabricMainUseTypeServiceImpl implements FabricMainUseTypeService{
    @Autowired
    private FabricMainUseTypeRepository repository;

    public FabricMainUseType findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    public List<FabricMainUseType> findAll() {
        return repository.findAll();
    }
}
