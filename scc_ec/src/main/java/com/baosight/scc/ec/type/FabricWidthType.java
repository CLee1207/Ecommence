package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.Fabric;

import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by ThinkPad on 2014/5/5.
 */
//@Entity
//@Table(name="ec_fabricWidthType")
public class FabricWidthType {
    @Id
    private String id;

    private String name;

    @OneToOne(mappedBy = "fabricWidthType")
    private Fabric fabric;
}
