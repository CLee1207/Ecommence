package com.baosight.scc.ec.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by zodiake on 2014/5/13.
 */
//服务选择分类时，对于进行验证
public class FabricCategoryValidator implements Validator{
    public boolean supports(Class<?> aClass) {
        return Fabric.class.isAssignableFrom(aClass);
    }

    public void validate(Object o, Errors errors) {
    }

    public void validateCategory(Object o,Errors errors){
        ValidationUtils.rejectIfEmpty(errors,"category","fabric.category.required");
    }

    public void validateContent(Object o,Errors errors) {

    }
}
