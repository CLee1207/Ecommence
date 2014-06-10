package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.Item;

/**
 * Created by zodiake on 2014/6/3.
 */
public class ItemJSON {
    private String id;
    private String title;
    private Integer bidCount;
    private Double price;
    private String url;

    public ItemJSON(){}

    public ItemJSON(Item item){
        setId(item.getId());
        setBidCount(item.getBidCount());
        setTitle(item.getName());
        setPrice(item.getPrice());
        setUrl(item.getUrl());
    }

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

    public Integer getBidCount() {
        return bidCount;
    }

    public void setBidCount(Integer bidCount) {
        this.bidCount = bidCount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
