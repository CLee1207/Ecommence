package com.baosight.scc.ec.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * Created by ThinkPad on 2014/5/6.
 */

public class ShoppingCart implements Serializable{
    private List<CartLine> items=new ArrayList<CartLine>(); //购物车商品条目

    public List<CartLine> getItems() {
        return items;
    }

    public void setItems(List<CartLine> items) {
        this.items = items;
    }

}
