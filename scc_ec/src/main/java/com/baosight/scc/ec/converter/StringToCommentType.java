package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.type.CommentType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/5/29.
 */
@AutoRegister
@Component
public class StringToCommentType implements Converter<String,CommentType>{
    @Override
    public CommentType convert(String source) {
        int i=Integer.parseInt(source);
        if(i>=-1&&i<=1)
            return CommentType.values()[i];
        return null;
    }
}
