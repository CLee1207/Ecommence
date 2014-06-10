package com.baosight.scc.ec.model;

import javax.persistence.*;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Entity
@Table(name="ec_PriceRange")
@Deprecated
public class PriceRange {
    @Id
    private int id;

    private double unitPrice;

    private double salableNumber;

    @ManyToOne
    @JoinColumn(name="fabric_id")
    private Fabric fabric;
}
