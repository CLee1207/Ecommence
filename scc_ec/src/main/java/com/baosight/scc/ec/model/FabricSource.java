package com.baosight.scc.ec.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/19.
 */
@Entity
@Table(name = "T_ec_FabricSource")
@NamedQueries({@NamedQuery(name="FabricSource.findAllFirstSource",query="select s from FabricSource s where s.detailSources is not empty"),
@NamedQuery(name="FabricSource.findAllSecondSource",query = "select s from FabricSource s where s.detailSources is empty")})
public class FabricSource implements Serializable {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "parent")
    private List<FabricSource> detailSources;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private FabricSource parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy")
    private EcUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updatedBy")
    private EcUser updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    private int isValid;

    @OneToMany(mappedBy = "category")
    private List<Fabric> fabrics=new ArrayList<Fabric>();

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

    public List<FabricSource> getDetailSources() {
        return detailSources;
    }

    public void setDetailSources(List<FabricSource> detailSources) {
        this.detailSources = detailSources;
    }

    public FabricSource getParent() {
        return parent;
    }

    public void setParent(FabricSource parent) {
        this.parent = parent;
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

    @PrePersist
    public void setCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public EcUser getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(EcUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getIsValid() {

        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public List<Fabric> getFabrics() {

        return fabrics;
    }

    public void setFabrics(List<Fabric> fabrics) {
        this.fabrics = fabrics;
    }

    public EcUser getCreatedBy() {

        return createdBy;
    }

    public void setCreatedBy(EcUser createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FabricSource that = (FabricSource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
