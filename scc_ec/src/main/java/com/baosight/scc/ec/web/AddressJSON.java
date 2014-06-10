package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.Address;

/**
 * Created by zodiake on 2014/6/3.
 */
public class AddressJSON {
    private String receiveName;
    private String addressDetail;
    private String street;
    private String mobile;

    public AddressJSON(){}

    public AddressJSON(Address address){
        setReceiveName(address.getReceiverName());
        setAddressDetail(address.getCity()+" "+address.getState());
        setStreet(address.getStreet());
        setMobile(address.getMobile()==null?address.getMobile():address.getPhone());
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
