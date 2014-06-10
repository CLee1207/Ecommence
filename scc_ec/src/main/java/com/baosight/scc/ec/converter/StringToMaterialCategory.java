package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.service.MaterialCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/26.
 */
@AutoRegister
@Component
public class StringToMaterialCategory implements Converter<String,MaterialCategory>{
    @Autowired
    private MaterialCategoryService service;

    @Override
    public MaterialCategory convert(String source) {
        MaterialCategory category=service.findOne(source);
        if(category!=null)
            return category;
        else
            return null;
    }
}
