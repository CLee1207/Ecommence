package com.baosight.scc.ec.config;

import com.baosight.scc.ec.annotation.AutoRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

import java.util.List;

/**
 * Created by zodiake on 2014/6/5.
 */
public class AutoregisterFormatterRegistrar implements FormatterRegistrar {

    @Autowired(required = false)
    @AutoRegister
    private List<Converter<?, ?>> autoRegisteredConverters;


    @Override
    public void registerFormatters(final FormatterRegistry registry) {
        if (this.autoRegisteredConverters != null) {
            for (Converter<?, ?> converter : this.autoRegisteredConverters) {
                registry.addConverter(converter);
            }
        }
    }
}
