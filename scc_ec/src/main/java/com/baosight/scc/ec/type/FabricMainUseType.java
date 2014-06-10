package com.baosight.scc.ec.type;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by ThinkPad on 2014/5/8.
 */
@Embeddable
public class FabricMainUseType implements Serializable {
    private String id;
    private String name;
    private int orderNum;

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

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FabricMainUseType type = (FabricMainUseType) o;

        if (id != null ? !id.equals(type.id) : type.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
