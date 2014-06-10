package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.DemandOrderState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ThinkPad on 2014/5/8.
 */
@Entity
@Table(name = "T_ec_DemandOrder")
//求购单
public class DemandOrder implements Serializable {
    @Id
    private String id;

    @NotNull
    private String exceptionAddress;    //期望所在地

    @NotNull
    private String title;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @NotNull
    private Address receiveAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EcUser createdBy;   //创建人

    @NotNull
    private String demandType;  //求购类型

    private String content; //内容

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar validDateFrom; //有效期开始

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar validDateTo;   //有效期结束

    private String provideType;//供货方式

    private String unit;    //计量单位

    private double priceFrom;   //价格区间开始

    private double priceTo; //价格区间结束

    private double demandSum;   //求购总量

    private int deliveryDuration;   //发货时间

    @ElementCollection
    @CollectionTable(name = "T_ec_DemandOrderImage", joinColumns = @JoinColumn(name = "demandOrder_id"))
    private List<CultureImage> images;  //图片列表

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    @Enumerated(EnumType.STRING)
    private DemandOrderState state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExceptionAddress() {
        return exceptionAddress;
    }

    public void setExceptionAddress(String exceptionAddress) {
        this.exceptionAddress = exceptionAddress;
    }

    public Address getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(Address receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public EcUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EcUser createdBy) {
        this.createdBy = createdBy;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Calendar validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public Calendar getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Calendar validDateTo) {
        this.validDateTo = validDateTo;
    }

    public String getProvideType() {
        return provideType;
    }

    public void setProvideType(String provideType) {
        this.provideType = provideType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }

    public double getDemandSum() {
        return demandSum;
    }

    public void setDemandSum(double demandSum) {
        this.demandSum = demandSum;
    }

    public int getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(int deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public List<CultureImage> getImages() {
        return images;
    }

    public void setImages(List<CultureImage> images) {
        this.images = images;
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

    public DemandOrderState getState() {
        return state;
    }

    public void setState(DemandOrderState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
