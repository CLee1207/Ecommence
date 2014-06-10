package com.baosight.scc.ec.model;

import com.baosight.scc.ec.utils.GuidUtil;

import javax.persistence.*;

/**
 * Created by Charles on 2014/5/20.
 */
@Entity
@Table(name="T_ec_OrderAddress")
//地址
public class OrderAddress {
    @Id
    private String id;

    private String title;

    private String state;

    private String city;

    private String zipCode;

    private String street;

    private String receiverName;

    private String mobile;

    private String zipPhone;

    private String phone;

    private String childPhone;

    public OrderAddress(){}

    public OrderAddress(Address address,String title){
        setId(GuidUtil.newGuid());
        setTitle(title);
        setChildPhone(address.getChildPhone());
        setState(address.getState());
        setCity(address.getCity());
        setMobile(address.getMobile());
        setPhone(address.getPhone());
        setReceiverName(address.getReceiverName());
        setStreet(address.getStreet());
        setZipCode(address.getZipCode());
        setZipPhone(address.getZipPhone());
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getZipPhone() {
        return zipPhone;
    }

    public void setZipPhone(String zipPhone) {
        this.zipPhone = zipPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone;
    }
}
