package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/5/13.
 */
@Embeddable
public class PreferMaterialCategory implements Serializable {
    @OneToMany
    @JoinTable(name="T_ec_user_PreferMaterialCategory",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="category_id"))
    private List<MaterialCategory> categories=new ArrayList<MaterialCategory>();

    public List<MaterialCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<MaterialCategory> categories) {
        this.categories = categories;
    }
}
