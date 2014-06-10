package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.MaterialCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/27.
 */
@AutoRegister
@Component
public class MaterialCategoryToString implements Converter<MaterialCategory,String> {
    @Override
    public String convert(MaterialCategory source) {
        return source.getId();
    }
}
