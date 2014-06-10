package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Embeddable
//非实体对象，依存与面料辅料
public class CultureImage implements Serializable {
    private String location;

    private int orderNum;

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Calendar getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Calendar updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }
}
