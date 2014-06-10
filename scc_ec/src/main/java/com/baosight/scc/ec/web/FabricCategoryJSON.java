package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.FabricCategory;

/**
 * Created by zodiake on 2014/6/6.
 */
public class FabricCategoryJSON {
    private String id;
    private String name;

    public FabricCategoryJSON(FabricCategory fabricCategory) {
        this.id = fabricCategory.getId();
        this.name = fabricCategory.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
