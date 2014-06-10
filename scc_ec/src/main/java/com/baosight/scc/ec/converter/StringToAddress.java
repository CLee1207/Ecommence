package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/26.
 */
@AutoRegister
@Component
public class StringToAddress implements Converter<String,Address> {
    @Autowired
    private AddressService service;

    @Override
    public Address convert(String source) {
        Address address=service.findById(source);
        if(address!=null)
            return address;
        else
            return null;
    }
}
