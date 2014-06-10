package com.baosight.scc.ec.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Charles on 2014/6/5.
 * 广告管理
 */
@Entity
@Table(name = "T_ec_information")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Information implements Serializable {
    @Id
    private String id;

    private String title;

    private String content;

    private String coverPath;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    private int isValid;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private InformationCategory informationCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private EcUser createdBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    @PrePersist
    public void setCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public InformationCategory getInformationCategory() {
        return informationCategory;
    }

    public void setInformationCategory(InformationCategory informationCategory) {
        this.informationCategory = informationCategory;
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
        if (!(o instanceof Information)) return false;

        Information that = (Information) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
