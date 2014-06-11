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
    private String location300;

    private String location600;

    private String location1000;

    private int orderNum;

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    public String getLocation300() {
        return location300;
    }

    public void setLocation300(String location300) {
        this.location300 = location300;
    }

    public String getLocation600() {
        return location600;
    }

    public void setLocation600(String location600) {
        this.location600 = location600;
    }

    public String getLocation1000() {
        return location1000;
    }

    public void setLocation1000(String location1000) {
        this.location1000 = location1000;
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
