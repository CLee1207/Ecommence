package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.Fabric;

import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by ThinkPad on 2014/5/6.
 */
//@Entity
//@Table(name="ec_fabricTechnology")
public class FabricTechnology {
    @Id
    private String id;

    private String name;

    @OneToOne(mappedBy = "fabricTechnology")
    private Fabric fabric;
}
