package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.service.FabricMainUseTypeService;
import com.baosight.scc.ec.type.FabricMainUseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/14.
 */
@AutoRegister
@Component
public class StringToFabricMainUseType implements Converter<String,FabricMainUseType> {
    @Autowired
    private FabricMainUseTypeService service;
    final Logger logger = LoggerFactory.getLogger(StringToFabricMainUseType.class);

    public FabricMainUseType convert(String source) {
        logger.info("mainUserType:----"+service.findOne(source));
        return service.findOne(source);
    }
}
