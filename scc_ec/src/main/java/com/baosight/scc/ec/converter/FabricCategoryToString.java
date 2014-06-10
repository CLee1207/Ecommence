package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.FabricCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/15.
 */
@AutoRegister
@Component
public class FabricCategoryToString implements Converter<FabricCategory, String> {

    public String convert(FabricCategory source) {
        return source.getName();
    }
}
