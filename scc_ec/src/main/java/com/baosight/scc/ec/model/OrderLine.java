package com.baosight.scc.ec.model;

import com.baosight.scc.ec.utils.GuidUtil;

import javax.persistence.*;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Entity
@Table(name="T_ec_OrderLine")
//订单明细
@NamedQueries({@NamedQuery(name="OrderLine.findTest",query="select a,count(a.item) from OrderLine a left join a.item b "),
@NamedQuery(name = "OrderLine.countByStateAndItem",query="select count(o) from OrderLine o where o.item=:item and o.item.state<>'FINISH'"),
@NamedQuery(name="OrderLine.findByItem",query="select o from OrderLine o join o.orderItem oi join o.orderItem.buyer b where o.item=:item")})
public class OrderLine {
    @Id
    private String id;

    private double sum;
    private double price;
    private int quantity;
    @Transient
    private int num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderItem_id")
    private OrderItem orderItem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    public OrderLine(){}

    public OrderLine(CartLine cartLine){
    	setId(GuidUtil.newGuid());
        setSum(cartLine.getSummary());
        setQuantity(cartLine.getQuantity());
        setPrice(cartLine.getPrice());
        setItem(cartLine.getItem());
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine that = (OrderLine)o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
