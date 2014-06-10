package com.baosight.scc.ec.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/30.
 */
@Entity
@Table(name = "t_ec_favouriteItems")
@NamedQueries({@NamedQuery(name = "FavouriteItems_findByUser", query = "select fi from  FavouriteItems fi where fi.user=:user"),
        @NamedQuery(name = "FavouriteItems_countByUser", query = "select count(fi) from  FavouriteItems fi where fi.user=:user")})
public class FavouriteItems {
    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "user_id")
        private String userId;

        @Column(name = "item_id")
        private String itemId;

        public Id() {
        }

        public Id(String userId, String itemId) {
            this.userId = userId;
            this.itemId = itemId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            if (itemId != null ? !itemId.equals(id.itemId) : id.itemId != null) return false;
            if (userId != null ? !userId.equals(id.userId) : id.userId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (itemId != null ? itemId.hashCode() : 0);
            return result;
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private EcUser user;

    @ManyToOne
    @JoinColumn(name = "item_id", insertable = false, updatable = false)
    private Item item;

    @PrePersist
    private void prePersist() {
        DateTime dateTime = new DateTime();
        this.createdTime = dateTime.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public FavouriteItems() {
    }

    public FavouriteItems(EcUser user, Item item) {
        this.id.itemId = item.getId();
        this.id.userId = user.getId();
        this.user = user;
        this.item = item;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }
}
