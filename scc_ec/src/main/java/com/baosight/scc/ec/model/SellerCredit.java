package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.CommentType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by sam on 2014/6/6.
 */
@Entity
@Table(name="T_ec_sellerCredit")
public class SellerCredit implements Serializable{

    @Id
    protected Integer id;

    @ManyToOne
    @JoinColumn(name="user_id")
    protected EcUser user;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    @Enumerated(EnumType.STRING)
    private CommentType type;

    private Integer weekCount;

    private Integer oneMonthCount;

    private Integer sixMonthCount;

    private Integer sixMonthBeforeCount;

    private Integer total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public CommentType getType() {
        return type;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    public Integer getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(Integer weekCount) {
        this.weekCount = weekCount;
    }

    public Integer getOneMonthCount() {
        return oneMonthCount;
    }

    public void setOneMonthCount(Integer oneMonthCount) {
        this.oneMonthCount = oneMonthCount;
    }

    public Integer getSixMonthCount() {
        return sixMonthCount;
    }

    public void setSixMonthCount(Integer sixMonthCount) {
        this.sixMonthCount = sixMonthCount;
    }

    public Integer getSixMonthBeforeCount() {
        return sixMonthBeforeCount;
    }

    public void setSixMonthBeforeCount(Integer sixMonthBeforeCount) {
        this.sixMonthBeforeCount = sixMonthBeforeCount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellerCredit that = (SellerCredit) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SellerCredit{" +
                "id='" + id + '\'' +
                '}';
    }

}
