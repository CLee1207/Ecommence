package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.Fabric;

import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by ThinkPad on 2014/5/5.
 */
//@Table(name = "ec_fabricHeightType")
public class FabricHeightType {
    @Id
    private String id;

    private String name;

    @OneToOne(mappedBy = "fabricHeightType")
    private Fabric fabric;

}
