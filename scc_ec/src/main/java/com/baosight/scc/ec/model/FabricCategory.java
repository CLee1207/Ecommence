package com.baosight.scc.ec.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Entity
@Table(name="T_ec_FabricCategory")
@NamedQueries({@NamedQuery(name="FabricCategory.findAllSecondCategory",query="select fc from FabricCategory fc where fc.secondCategory is empty"),
				@NamedQuery(name="FabricCategory.findAllFirstCategory",query="select fc from FabricCategory fc where fc.parentCategory is empty")})
//面料分类
public class FabricCategory implements Serializable{
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "parentCategory",cascade = {CascadeType.ALL})
    private List<FabricCategory> secondCategory=new ArrayList<FabricCategory>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private FabricCategory parentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy")
    private EcUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updatedBy")
    private EcUser updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedTime;

    private int isValid;

    @OneToMany(mappedBy = "category")
    private List<Fabric> fabrics=new ArrayList<Fabric>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FabricCategory> getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(List<FabricCategory> secondCategory) {
        this.secondCategory = secondCategory;
    }

    public FabricCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(FabricCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Fabric> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<Fabric> fabrics) {
        this.fabrics = fabrics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EcUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EcUser createdBy) {
        this.createdBy = createdBy;
    }

    public EcUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(EcUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Calendar getCreatedTime() {

        return createdTime;
    }

    @PrePersist
    public void setCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public Calendar getUpdatedTime() {

        return updatedTime;
    }

    public void setUpdatedTime(Calendar updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FabricCategory that = (FabricCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FabricCategory{" +
                "name='" + name + '\'' +
                '}';
    }
}
