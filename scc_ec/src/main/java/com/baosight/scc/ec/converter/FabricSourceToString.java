package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.FabricSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/22.
 */
@AutoRegister
@Component
public class FabricSourceToString implements Converter<FabricSource,String>{
    @Override
    public String convert(FabricSource source) {
        return source.toString();
    }
}
