package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.Material;

import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by ThinkPad on 2014/5/6.
 */
//@Entity
//@Table(name="ec_MaterialScope")
public class MaterialScope {
    @Id
    private String id;

    private String name;

    @OneToOne(mappedBy = "materialScope")
    private Material material;
}
