package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.Fabric;

import javax.persistence.Id;
import javax.persistence.OneToOne;

//@Entity
//@Table(name="ec_fabricSeasonType")
public class FabricSeasonType {
    @Id
    private String id;

    private String name;

    @OneToOne(mappedBy = "fabricSeasonType")
    private Fabric fabric;
}
