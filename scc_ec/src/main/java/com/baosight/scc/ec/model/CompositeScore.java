package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by sam on 2014/6/11.
 */
@Entity
@Table(name = "T_ec_Composite_Score")
public class CompositeScore implements Serializable {
    @Id
    protected Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EcUser user;

    private Double score;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

        CompositeScore that = (CompositeScore) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CompositeScore{" +
                "id='" + id + '\'' +
                '}';
    }
}
