package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/6/3.
 */
@Entity
@Table(name="T_ec_shop")
public class Shop {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private EcUser user;

    @OneToMany(mappedBy = "shop")
    private List<FavouriteShops> favouriteShops=new ArrayList<FavouriteShops>();

    @Transient
    private List<Item> newestItem=new ArrayList<Item>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public List<FavouriteShops> getFavouriteShops() {
        return favouriteShops;
    }

    public void setFavouriteShops(List<FavouriteShops> favouriteShops) {
        this.favouriteShops = favouriteShops;
    }

    public List<Item> getNewestItem() {
        return newestItem;
    }

    public void setNewestItem(List<Item> newestItem) {
        this.newestItem = newestItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (id != null ? !id.equals(shop.id) : shop.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
