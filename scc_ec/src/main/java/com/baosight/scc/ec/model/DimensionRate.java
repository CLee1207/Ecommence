package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by sam on 2014/6/5.
 */
@Entity
@Table(name="T_ec_dimension_rate")
public class DimensionRate implements Serializable {

    @Id
    private Integer id;
    @OneToOne
    @JoinColumn(name="user_id")
    private EcUser seller; //卖家
    private Double attitude; //服务态度率
    private Double deliverySpeed;//发货速度率
    private Double satisfied;//宝贝相符率
    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;//创建日期

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EcUser getSeller() {
        return seller;
    }

    public void setSeller(EcUser seller) {
        this.seller = seller;
    }

    public Double getAttitude() {
        return attitude;
    }

    public void setAttitude(Double attitude) {
        this.attitude = attitude;
    }

    public Double getDeliverySpeed() {
        return deliverySpeed;
    }

    public void setDeliverySpeed(Double deliverySpeed) {
        this.deliverySpeed = deliverySpeed;
    }

    public Double getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(Double satisfied) {
        this.satisfied = satisfied;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DimensionRate that = (DimensionRate) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DimensionRate{" +
                "id='" + id + '\'' +
                '}';
    }
}
