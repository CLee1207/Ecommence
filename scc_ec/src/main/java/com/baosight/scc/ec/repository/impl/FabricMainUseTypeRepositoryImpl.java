package com.baosight.scc.ec.repository.impl;

import com.baosight.scc.ec.repository.FabricMainUseTypeRepository;
import com.baosight.scc.ec.type.FabricMainUseType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/5/19.
 */
@Component
public class FabricMainUseTypeRepositoryImpl implements FabricMainUseTypeRepository {
    private List<FabricMainUseType> types = new ArrayList<FabricMainUseType>();

    public FabricMainUseTypeRepositoryImpl(){
        FabricMainUseType type1 = new FabricMainUseType();
        type1.setId("1");
        type1.setName("aa");
        FabricMainUseType type2 = new FabricMainUseType();
        type2.setId("2");
        type2.setName("bb");
        FabricMainUseType type3 = new FabricMainUseType();
        type3.setId("3");
        type3.setName("cc");
        types.add(type1);
        types.add(type2);
        types.add(type3);
    }

    @Override
    public List<FabricMainUseType> findAll() {
        return types;
    }

    @Override
    public FabricMainUseType findOne(String id) {
        return types.get(Integer.parseInt(id)-1);
    }
}
