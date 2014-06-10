package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by sam on 2014/5/29.
 */
@AutoRegister
@Component
public class StringToItem implements Converter<String,Item> {
    @Autowired
    private ItemService itemService;
    @Override
    public Item convert(String source) {
        return this.itemService.findById(source);
    }
}
