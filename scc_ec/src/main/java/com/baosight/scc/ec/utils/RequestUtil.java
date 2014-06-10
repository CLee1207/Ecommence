package com.baosight.scc.ec.utils;

import org.springframework.webflow.context.ExternalContext;

import java.io.Serializable;

/**
 * Created by zodiake on 2014/5/15.
 */
public class RequestUtil implements Serializable {

    public String getRequestParameter(String name, ExternalContext context) {
        try {
            String paramValue = context.getRequestParameterMap().get(name);
            return paramValue;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
