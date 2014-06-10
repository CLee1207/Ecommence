package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.FabricSource;
import com.baosight.scc.ec.repository.FabricSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/19.
 */
@AutoRegister
@Component
public class StringToFabricSource implements Converter<String,FabricSource>{
    @Autowired
    private FabricSourceRepository repository;
    final Logger logger = LoggerFactory.getLogger(StringToFabricSource.class);

    public FabricSource convert(String source) {
        return repository.findOne(source);
    }
}
