package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.service.FabricCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/13.
 */

@AutoRegister
@Component
public class StringToFabricCategory implements Converter<String,FabricCategory> {
    @Autowired
    private FabricCategoryService service;


    public FabricCategory convert(String source) {
        return service.findById(source);
    }
}
