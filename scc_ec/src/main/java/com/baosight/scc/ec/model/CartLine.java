package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by ThinkPad on 2014/5/6.
 * 购物车明细条目
 */

public class CartLine implements Serializable{
    private String itemId;  //商品id
    private String title;   //商品标题
    private String supplierId;  //供应商id
    private String supplierName;    //供应商名称
    private String itemType;    //商品类型(辅料，面料)
    private double price;   //单价
    private int quantity;   // 数量
    private double summary; //总价
    private String imgPath; //图片路径
    private Map priceRange; //价格区间
    private int isCheck;
    @OneToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private ShoppingCart cart;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    public CartLine(){}

    public  CartLine(Material material){
        setIsCheck(0);
        setItemId(material.getId());
        Item item1 = new Item();
        item1.setId(material.getId());
        setItem(item1);
        setTitle(material.getName());
        setSupplierId(material.getCreatedBy().getId());
        setSupplierName(material.getCreatedBy().getName());
        setItemType("M");
        //setImgPath(material.getImages().get(0).getLocation());
        setPriceRange(material.getRanges());
    }

    public CartLine(Fabric fabric){
        setIsCheck(0);
        setItemId(fabric.getId());
        Item item1 = new Item();
        item1.setId(fabric.getId());
        setItem(item1);
        setTitle(fabric.getName());
        setSupplierId(fabric.getCreatedBy().getId());
        setSupplierName(fabric.getCreatedBy().getName());
        setItemType("F");
        //setImgPath(fabric.getImages().get(0).getLocation());
        setPriceRange(fabric.getRanges());
    }
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Map getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Map priceRange) {
        this.priceRange = priceRange;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartLine)) return false;

        CartLine cartLine = (CartLine) o;

        if (!itemId.equals(cartLine.itemId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

    public boolean compare(CartLine cartLine){
        if(!itemId.equals(cartLine.getItemId())){
            return false;
        }else{
            return true;
        }
    }
}
