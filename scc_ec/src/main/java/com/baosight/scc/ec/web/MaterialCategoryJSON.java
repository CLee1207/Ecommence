package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.MaterialCategory;

/**
 * Created by zodiake on 2014/6/6.
 */
public class MaterialCategoryJSON {
    private String id;
    private String name;

    public MaterialCategoryJSON(MaterialCategory materialCategory) {
        this.id=materialCategory.getId();
        this.name=materialCategory.getName();
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
