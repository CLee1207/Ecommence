package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 2014/5/9.
 */
@Embeddable
//偏好的面料分类
public class PreferFabricCategory implements Serializable {
    @OneToMany
    @JoinTable(name="T_ec_user_PreferFabricCategory",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="category_id"))
    private List<FabricCategory> categories=new ArrayList<FabricCategory>();

    public List<FabricCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<FabricCategory> categories) {
        this.categories = categories;
    }
}
