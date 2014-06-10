package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by zodiake on 2014/6/3.
 */
@Entity
@Table(name="t_ec_favouriteShops")
public class FavouriteShops {
    @Embeddable
    public static class Id implements Serializable{
        @Column(name="user_id")
        private String userId;

        @Column(name="shop_id")
        private String shopId;

        public Id(){}

        public Id(String userId,String shopId){
            this.userId=userId;
            this.shopId=shopId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Id id = (Id) o;

            if (shopId != null ? !shopId.equals(id.shopId) : id.shopId != null) return false;
            if (userId != null ? !userId.equals(id.userId) : id.userId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
            return result;
        }
    }

    @EmbeddedId
    private Id id;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private EcUser user;

    @ManyToOne
    @JoinColumn(name = "shop_id", insertable = false, updatable = false)
    private Shop shop;

    public FavouriteShops(){}

    public FavouriteShops(EcUser user,Shop shop){
        this.id.shopId=shop.getId();
        this.id.userId=user.getId();
        this.user=user;
        this.shop=shop;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
