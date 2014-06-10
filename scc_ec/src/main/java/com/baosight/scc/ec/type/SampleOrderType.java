package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.SampleOrder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by ThinkPad on 2014/5/8.
 */
@Deprecated
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//调样单类型
public class SampleOrderType {
    private String id;

    private String name;//名称
    private String unit;//单位
    private String number;//数量

    @ManyToOne
    @JoinColumn(name="order_id")
    private SampleOrder order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleOrderType sampleOrderType = (SampleOrderType) o;

        if (id != null ? !id.equals(sampleOrderType.id) : sampleOrderType.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
