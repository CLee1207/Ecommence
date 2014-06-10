package com.baosight.scc.ec.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Charles on 2014/6/5.
 * 广告栏位管理
 */
@Entity
@Table(name = "T_ec_information_category")
public class InformationCategory implements Serializable {
    @Id
    private String id;
    private String categoryName;    //分类名称
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;    //创建时间
    private int isValid;    //是否有效
    //暂时不考虑ManyToMany
    @OneToMany(mappedBy = "informationCategory")
    private List<Information> informationList = new ArrayList<Information>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Information> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<Information> informationList) {
        this.informationList = informationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformationCategory)) return false;

        InformationCategory that = (InformationCategory) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
