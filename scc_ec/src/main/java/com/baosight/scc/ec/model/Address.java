package com.baosight.scc.ec.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by ThinkPad on 2014/5/8.
 */
@Entity
@Table(name="T_ec_Address")
//地址
public class Address implements Serializable{
    @Id
    private String id;

    @NotNull
    private String state;

    @NotNull
    private String city;

    private String zipCode;

    @NotNull
    private String street;

    @NotNull
    private String receiverName;

    private String mobile;

    private String zipPhone;

    private String phone;

    private String childPhone;

    @ManyToOne
    @JoinColumn(name="user_id")
    private EcUser user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != null ? !id.equals(address.id) : address.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
