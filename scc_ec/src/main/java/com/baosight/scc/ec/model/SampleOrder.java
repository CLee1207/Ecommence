package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.SampleOrderState;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ThinkPad on 2014/5/8.
 */
//吊样单
@Entity
@Table(name = "T_ec_SampleOrder")
@NamedQueries({
        @NamedQuery(name = "sampleOrder.findByItemCreatedBy", query = "select s from SampleOrder s where s.item.createdBy=?1 order by s.createdTime"),
        @NamedQuery(name = "sampleOrder.countByItemCreatedBy", query = "select count(s) from SampleOrder s where s.item.createdBy=?1 order by s.createdTime"),
        @NamedQuery(name = "sampleOrder.findByStateAndItemCreatedBy", query = "select s from SampleOrder s where s.state=?1 and s.item.createdBy=?2 order by s.createdTime"),
        @NamedQuery(name = "sampleOrder.countByStateAndItemCreatedBy", query = "select count(s) from SampleOrder s where s.state=?1 and s.item.createdBy=?2 order by s.createdTime"),
})
public class SampleOrder implements Serializable {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private EcUser creator;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;  //调样单对应的商品

    @Enumerated(EnumType.STRING)
    private SampleOrderState state;

    @OneToOne
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;   //创建日期

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedTime;   //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar deliveryTime;  //交货日期

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private EcUser provider;

    @NotNull
    private Integer sampleCardNumber;   //吊样类型(册)

    @NotNull
    private Integer sampleItemMile;     //吊样类型 样品实物(米)

    @PrePersist
    private void prePersist() {
        DateTime time = new DateTime();
        this.createdTime = time.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    @PreUpdate
    private void preUpdate() {
        DateTime time = new DateTime();
        this.updatedTime = time.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EcUser getCreator() {
        return creator;
    }

    public void setCreator(EcUser creator) {
        this.creator = creator;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public SampleOrderState getState() {
        return state;
    }

    public void setState(SampleOrderState state) {
        this.state = state;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public Calendar getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Calendar deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public EcUser getProvider() {
        return provider;
    }

    public void setProvider(EcUser provider) {
        this.provider = provider;
    }

    public Integer getSampleCardNumber() {
        return sampleCardNumber;
    }

    public void setSampleCardNumber(Integer sampleCardNumber) {
        this.sampleCardNumber = sampleCardNumber;
    }

    public Integer getSampleItemMile() {
        return sampleItemMile;
    }

    public void setSampleItemMile(Integer sampleItemMile) {
        this.sampleItemMile = sampleItemMile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleOrder that = (SampleOrder) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
