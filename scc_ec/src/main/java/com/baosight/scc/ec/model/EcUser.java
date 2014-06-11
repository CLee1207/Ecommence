package com.baosight.scc.ec.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ThinkPad on 2014/5/6.
 */
//本系统对应的用户父类
@Entity
@Table(name = "T_ec_EcUser")
public class EcUser extends User implements Serializable {
    @Id
    protected String id;

    protected String name;

    protected String password;

    private String type;

    @OneToMany(mappedBy = "buyer")
    protected List<OrderItem> buyerOrders = new ArrayList<OrderItem>();

    @OneToMany(mappedBy = "seller")
    protected List<OrderItem> sellerOrders = new ArrayList<OrderItem>();

    @Transient
    protected ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<Address>();

    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "defaultAddress_id")
    private Address defaultAddress;

    @OneToMany(mappedBy = "user")
    private List<Comment> sendComments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "user")
    private List<SellerComment> sellerComments = new ArrayList<SellerComment>();

    @OneToMany(mappedBy = "creator")
    private List<SampleOrder> sampleOrders = new ArrayList<SampleOrder>();

    @OneToMany(mappedBy = "provider")
    private List<SampleOrder> receivedSampleOrders = new ArrayList<SampleOrder>();

    @OneToMany(mappedBy = "createdBy")
    private List<DemandOrder> demandOrders = new ArrayList<DemandOrder>();

    @OneToMany(mappedBy = "createdBy")
    private List<Information> informations = new ArrayList<Information>();

    @Embedded
    private PreferFabricCategory preferFabricCategory = new PreferFabricCategory();

    @Embedded
    private PreferMaterialCategory preferMaterialCategory = new PreferMaterialCategory();

    @OneToMany(mappedBy = "createdBy", cascade = {CascadeType.PERSIST})
    private List<Item> fabrics = new ArrayList<Item>();

    @OneToMany(mappedBy = "createdBy", cascade = {CascadeType.PERSIST})
    private List<Item> materials = new ArrayList<Item>();

    @OneToMany(mappedBy = "createdBy", cascade = {CascadeType.PERSIST})
    private List<FabricCategory> fabricCategoryListCreatedBy = new ArrayList<FabricCategory>();

    @OneToMany(mappedBy = "createdBy", cascade = {CascadeType.PERSIST})
    private List<MaterialCategory> materialCategoryListCreatedBy = new ArrayList<MaterialCategory>();

    @OneToMany(mappedBy = "updatedBy", cascade = {CascadeType.PERSIST})
    private List<FabricCategory> fabricCategoryListUpdatedBy = new ArrayList<FabricCategory>();

    @OneToMany(mappedBy = "updatedBy", cascade = {CascadeType.PERSIST})
    private List<MaterialCategory> materialCategoryListUpdatedBy = new ArrayList<MaterialCategory>();

    @OneToMany(mappedBy = "user")
    private List<FavouriteItems> favouriteItems=new ArrayList<FavouriteItems>();

    @OneToMany(mappedBy = "user")
    private List<Shop> shops=new ArrayList<Shop>();

    @OneToMany(mappedBy = "user")
    private List<FavouriteShops> favouriteShops=new ArrayList<FavouriteShops>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @OneToMany(mappedBy = "user")
    private List<SellerCredit> sellerCredits = new ArrayList<SellerCredit>();

    private String companyName;

    private String companyAddress;

    private String url;//公司主页

    @OneToOne
    @Transient
    private CompositeScore compositeScore;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SampleOrder> getReceivedSampleOrders() {
        return receivedSampleOrders;
    }

    public void setReceivedSampleOrders(List<SampleOrder> receivedSampleOrders) {
        this.receivedSampleOrders = receivedSampleOrders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Address getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Address defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public List<Comment> getSendComments() {
        return sendComments;
    }

    public void setSendComments(List<Comment> sendComments) {
        this.sendComments = sendComments;
    }

    public List<SampleOrder> getSampleOrders() {
        return sampleOrders;
    }

    public void setSampleOrders(List<SampleOrder> sampleOrders) {
        this.sampleOrders = sampleOrders;
    }

    public List<DemandOrder> getDemandOrders() {
        return demandOrders;
    }

    public void setDemandOrders(List<DemandOrder> demandOrders) {
        this.demandOrders = demandOrders;
    }

    public PreferFabricCategory getPreferFabricCategory() {
        return preferFabricCategory;
    }

    public void setPreferFabricCategory(PreferFabricCategory preferFabricCategory) {
        this.preferFabricCategory = preferFabricCategory;
    }

    public List<OrderItem> getBuyerOrders() {
        return buyerOrders;
    }

    public void setBuyerOrders(List<OrderItem> buyerOrders) {
        this.buyerOrders = buyerOrders;
    }

    public List<OrderItem> getSellerOrders() {
        return sellerOrders;
    }

    public void setSellerOrders(List<OrderItem> sellerOrders) {
        this.sellerOrders = sellerOrders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PreferMaterialCategory getPreferMaterialCategory() {
        return preferMaterialCategory;
    }

    public void setPreferMaterialCategory(PreferMaterialCategory preferMaterialCategory) {
        this.preferMaterialCategory = preferMaterialCategory;
    }

    public List<Item> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<Item> fabrics) {
        this.fabrics = fabrics;
    }

    public List<Item> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Item> materials) {
        this.materials = materials;
    }


    public List<SellerComment> getSellerComments() {
        return sellerComments;
    }

    public void setSellerComments(List<SellerComment> sellerComments) {
        this.sellerComments = sellerComments;
    }

    public List<Information> getInformations() {
        return informations;
    }

    public void setInformations(List<Information> informations) {
        this.informations = informations;
    }

    public List<FavouriteItems> getFavouriteItems() {
        return favouriteItems;
    }

    public void setFavouriteItems(List<FavouriteItems> favouriteItems) {
        this.favouriteItems = favouriteItems;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
        this.authorities=authorities;
    }

    public List<SellerCredit> getSellerCredits() {
        return sellerCredits;
    }

    public void setSellerCredits(List<SellerCredit> sellerCredits) {
        this.sellerCredits = sellerCredits;
    }

    public List<FabricCategory> getFabricCategoryListCreatedBy() {
        return fabricCategoryListCreatedBy;
    }

    public void setFabricCategoryListCreatedBy(List<FabricCategory> fabricCategoryListCreatedBy) {
        this.fabricCategoryListCreatedBy = fabricCategoryListCreatedBy;
    }

    public List<MaterialCategory> getMaterialCategoryListCreatedBy() {
        return materialCategoryListCreatedBy;
    }

    public void setMaterialCategoryListCreatedBy(List<MaterialCategory> materialCategoryListCreatedBy) {
        this.materialCategoryListCreatedBy = materialCategoryListCreatedBy;
    }

    public List<FabricCategory> getFabricCategoryListUpdatedBy() {
        return fabricCategoryListUpdatedBy;
    }

    public void setFabricCategoryListUpdatedBy(List<FabricCategory> fabricCategoryListUpdatedBy) {
        this.fabricCategoryListUpdatedBy = fabricCategoryListUpdatedBy;
    }

    public List<MaterialCategory> getMaterialCategoryListUpdatedBy() {
        return materialCategoryListUpdatedBy;
    }

    public void setMaterialCategoryListUpdatedBy(List<MaterialCategory> materialCategoryListUpdatedBy) {
        this.materialCategoryListUpdatedBy = materialCategoryListUpdatedBy;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public List<FavouriteShops> getFavouriteShops() {
        return favouriteShops;
    }

    public void setFavouriteShops(List<FavouriteShops> favouriteShops) {
        this.favouriteShops = favouriteShops;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CompositeScore getCompositeScore() {
        return compositeScore;
    }

    public void setCompositeScore(CompositeScore compositeScore) {
        this.compositeScore = compositeScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcUser user = (EcUser) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
