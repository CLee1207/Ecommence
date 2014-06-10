package com.baosight.scc.ec.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Charles on 2014/5/30.
 * 广告栏位管理
 */
@Entity
@Table(name = "T_ec_ad_position")
public class AdvertisementPosition implements Serializable {
    @Id
    private String id;
    private String positionNo;  //栏位编号
    private String name;    //栏位名称
    private String description; //说明
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;    //创建时间
    private int isValid;    //是否有效
    //暂时不考虑ManyToMany
    @OneToMany(mappedBy = "advertisementPosition", cascade = {CascadeType.ALL})
    private List<Advertisement> advertisements = new ArrayList<Advertisement>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    @PrePersist
    public void setCreatedTime() {
        DateTime dateTime = new DateTime();
        this.createdTime = dateTime.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertisementPosition)) return false;

        AdvertisementPosition that = (AdvertisementPosition) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
