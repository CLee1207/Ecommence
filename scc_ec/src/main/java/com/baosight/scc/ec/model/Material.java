package com.baosight.scc.ec.model;


import org.joda.time.DateTime;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Entity
@Table(name = "t_ec_material")
//辅料
public class Material extends Item {
    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="materialType_id")
    private MaterialType materialType;  //类型
    */
    private String materialType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="widthAndSize_id")
    private MaterialWidthAndSizeType widthAndSizeType;  //重量/厚薄
    */
    @Column(name = "widthAndSize")
    private String materialWidthAndSizeType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="materialScope_id")
    private MaterialScope materialScope;//适用范围
    */
    private String materialScope;

    @ElementCollection
    @CollectionTable(name = "t_ec_material_color", joinColumns = {@JoinColumn(name = "material_id")})
    private List<Color> colors;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="provideType_id")
    private MaterialProvideType provideType;//供货方式
    */
    @Column(name = "providerType")
    private String materialProvideType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="materialMeasureType_id")
    private MaterialMeasureType measureType;//计量单位
    */
    private String materialMeasureType;

    @ElementCollection
    @CollectionTable(name = "t_ec_material_range", joinColumns = @JoinColumn(name = "material_id"))
    @MapKeyColumn(name = "unit_from")
    @Column(name = "price")
    private Map<Double, Double> ranges = new HashMap<Double, Double>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MaterialCategory category;

    @Transient
    private Double[] keys;  //起定量

    @Transient
    private Double[] values;//起定量对应的价格

    @ElementCollection
    @CollectionTable(name = "t_ec_CultureImage", joinColumns = @JoinColumn(name = "item_id"))
    @OrderBy(value = "orderNum asc")
    private List<CultureImage> images = new ArrayList<CultureImage>();

    @PrePersist
    public void prePersist() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    @PreUpdate
    public void preUpdate() {
        DateTime dt = new DateTime();
        this.updatedTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialWidthAndSizeType() {
        return materialWidthAndSizeType;
    }

    public void setMaterialWidthAndSizeType(String materialWidthAndSizeType) {
        this.materialWidthAndSizeType = materialWidthAndSizeType;
    }

    public String getMaterialScope() {
        return materialScope;
    }

    public void setMaterialScope(String materialScope) {
        this.materialScope = materialScope;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public String getMaterialProvideType() {
        return materialProvideType;
    }

    public void setMaterialProvideType(String materialProvideType) {
        this.materialProvideType = materialProvideType;
    }

    public String getMaterialMeasureType() {
        return materialMeasureType;
    }

    public void setMaterialMeasureType(String materialMeasureType) {
        this.materialMeasureType = materialMeasureType;
    }

    public Map<Double, Double> getRanges() {
        return ranges;
    }

    public void setRanges(Map<Double, Double> ranges) {
        this.ranges = ranges;
    }

    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        this.category = category;
    }

    public Double[] getKeys() {
        return keys;
    }

    public void setKeys(Double[] keys) {
        this.keys = keys;
    }

    public Double[] getValues() {
        return values;
    }

    public void setValues(Double[] values) {
        this.values = values;
    }

    public List<CultureImage> getImages() {
        return images;
    }

    public void setImages(List<CultureImage> images) {
        this.images = images;
    }

    /*
        valid material select category step
     */
    public void validateSelectCategory(ValidationContext context) {
        if (context.getUserEvent().equals("next")) {
            MessageContext messages = context.getMessageContext();
            if (category == null)
                messages.addMessage(new MessageBuilder().error().source("category").code("material_category_required").build());
        }
    }

    /*
        valid material fill content step
     */
    public void validateFillContent(ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (context.getUserEvent().equals("finish")) {
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("material.required").build());
            if (keys == null)
                messages.addMessage(new MessageBuilder().error().source("keys").code("material.required").build());
            if (values == null)
                messages.addMessage(new MessageBuilder().error().source("values").code("material.required").build());
            if (values == null || values.length == 0)
                messages.addMessage(new MessageBuilder().error().source("values").code("material.required").build());
            if (keys.length != values.length)
                messages.addMessage(new MessageBuilder().error().source("values").code("material.required").build());
        }
        if (context.getUserEvent().equals("temporary")) {
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("material.required").build());
        }

    }
}
