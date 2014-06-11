package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.FabricMainUseType;
import org.joda.time.DateTime;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ThinkPad on 2014/5/5.
 */
@Entity
@Table(name = "T_ec_fabric")
//面料
@DiscriminatorValue("f")
@NamedQueries({@NamedQuery(name="fabric.findByIdAndCreatedBy",query="select f from Fabric f where f.id=?1 and f.createdBy=?2")})
public class Fabric extends Item {

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="fabricSeasonType_id")
    private FabricSeasonType fabricSeasonType;  //适用季节
    */
    private String fabricSeasonType;

    private String ingredient;      //成分

    private String yarn;            //纱支

    private String density;         //密度

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="width_id")
    private FabricWidthType fabricWidthType;    //幅宽
    */
    private String fabricWidthType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="heightType_id")
    private FabricHeightType fabricHeightType;  //克重
    */
    private String fabricHeightType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="technology_id")
    private FabricTechnology fabricTechnology;  //染整工艺
    */
    private String fabricTechnology;

    private String fabricSecondTechnology; //染整工艺小类

    @ElementCollection
    @CollectionTable(name = "t_ec_fabric_color", joinColumns = {@JoinColumn(name = "fabric_id")})
    private List<Color> colors = new ArrayList<Color>();

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="provideType_id")
    private FabricProvideType provideType;    //供货方式
    */
    private String fabricProvideType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="FabricMeasureType_id")
    private FabricMeasureType measureType;    //计量单位
    */
    private String fabricMeasureType;

    @Transient
    private Double[] keys;

    @Transient
    private Double[] values;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private FabricSource source;    //面料成分一级

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detailSource_id")
    private FabricSource sourceDetail;

    @ElementCollection
    @CollectionTable(name = "t_ec_fabric_range", joinColumns = @JoinColumn(name = "fabric_id"))
    @MapKeyColumn(name = "unit_from")
    @Column(name = "price")
    private Map<Double, Double> ranges = new HashMap<Double, Double>();

    @Transient
    private Map<String ,Double> showRanges=new LinkedHashMap<String, Double>();

    private int shipInterval;   //发货时间

    @ElementCollection
    @CollectionTable(name = "t_ec_CultureImage", joinColumns = @JoinColumn(name = "item_id"))
    @OrderBy(value = "orderNum asc")
    private List<CultureImage> images = new ArrayList<CultureImage>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private FabricCategory category;

    @ElementCollection
    @CollectionTable(name = "t_ec_fabric_mainUse", joinColumns = @JoinColumn(name = "fabric_id"))
    private List<FabricMainUseType> mainUseTypes = new ArrayList<FabricMainUseType>();

    private double availableSum;    //可售总量

    private String itemNumber;

    @PrePersist
    private void setTheCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    @PreUpdate
    private void setTheUpdatedTime(){
        DateTime dt = new DateTime();
        this.updatedTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public String getFabricSeasonType() {
        return fabricSeasonType;
    }

    public void setFabricSeasonType(String fabricSeasonType) {
        this.fabricSeasonType = fabricSeasonType;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getYarn() {
        return yarn;
    }

    public void setYarn(String yarn) {
        this.yarn = yarn;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getFabricWidthType() {
        return fabricWidthType;
    }

    public void setFabricWidthType(String fabricWidthType) {
        this.fabricWidthType = fabricWidthType;
    }

    public String getFabricHeightType() {
        return fabricHeightType;
    }

    public void setFabricHeightType(String fabricHeightType) {
        this.fabricHeightType = fabricHeightType;
    }

    public String getFabricTechnology() {
        return fabricTechnology;
    }

    public void setFabricTechnology(String fabricTechnology) {
        this.fabricTechnology = fabricTechnology;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public String getFabricProvideType() {
        return fabricProvideType;
    }

    public void setFabricProvideType(String fabricProvideType) {
        this.fabricProvideType = fabricProvideType;
    }

    public String getFabricMeasureType() {
        return fabricMeasureType;
    }

    public void setFabricMeasureType(String fabricMeasureType) {
        this.fabricMeasureType = fabricMeasureType;
    }

    public Map<Double, Double> getRanges() {
        return ranges;
    }

    public void setRanges(Map<Double, Double> ranges) {
        this.ranges = ranges;
    }

    public int getShipInterval() {
        return shipInterval;
    }

    public void setShipInterval(int shipInterval) {
        this.shipInterval = shipInterval;
    }

    public List<CultureImage> getImages() {
        return images;
    }

    public void setImages(List<CultureImage> images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FabricCategory getCategory() {
        return category;
    }

    public void setCategory(FabricCategory category) {
        this.category = category;
    }

    public List<FabricMainUseType> getMainUseTypes() {
        return mainUseTypes;
    }

    public void setMainUseTypes(List<FabricMainUseType> mainUseTypes) {
        this.mainUseTypes = mainUseTypes;
    }

    public double getAvailableSum() {
        return availableSum;
    }

    public void setAvailableSum(double availableSum) {
        this.availableSum = availableSum;
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

    public FabricSource getSource() {
        return source;
    }

    public void setSource(FabricSource source) {
        this.source = source;
    }

    public FabricSource getSourceDetail() {
        return sourceDetail;
    }

    public void setSourceDetail(FabricSource sourceDetail) {
        this.sourceDetail = sourceDetail;
    }

    public String getFabricSecondTechnology() {
        return fabricSecondTechnology;
    }

    public void setFabricSecondTechnology(String fabricSecondTechnology) {
        this.fabricSecondTechnology = fabricSecondTechnology;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Map<String, Double> getShowRanges() {
        return showRanges;
    }

    public void setShowRanges(Map<String, Double> showRanges) {
        this.showRanges = showRanges;
    }

    public void validateSelectCategory(ValidationContext context) {
        if (context.getUserEvent().equals("next")) {
            MessageContext messages = context.getMessageContext();
            if (mainUseTypes == null)
                messages.addMessage(new MessageBuilder().error().source("mainUseTypes").code("fabric.required").build());
            if (category == null)
                messages.addMessage(new MessageBuilder().error().source("category").code("fabric.required").build());
            if (sourceDetail == null)
                messages.addMessage(new MessageBuilder().error().source("sourceDetail").code("fabric.required").build());
            if (source == null)
                messages.addMessage(new MessageBuilder().error().source("source").code("fabric.required").build());
        }
    }

    public void validateFillContent(ValidationContext context) {
        if (context.getUserEvent().equals("finish")) {
            MessageContext messages = context.getMessageContext();
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("fabric.required").build());
            if (keys == null||keys.length==0)
                messages.addMessage(new MessageBuilder().error().source("keys").code("fabric.required").build());
            if (values == null||values.length==0)
                messages.addMessage(new MessageBuilder().error().source("values").code("fabric.required").build());
            if (keys.length != values.length)
                messages.addMessage(new MessageBuilder().error().source("values").code("fabric.required").build());
            /*
            if (StringUtils.isEmpty(fabricSeasonType))
                messages.addMessage(new MessageBuilder().error().source("fabricSeasonType").code("fabric.required").build());
            if (StringUtils.isEmpty(ingredient))
                messages.addMessage(new MessageBuilder().error().source("ingredient").code("fabric.required").build());
            if (StringUtils.isEmpty(yarn))
                messages.addMessage(new MessageBuilder().error().source("yarn").code("fabric.required").build());
            if (StringUtils.isEmpty(density))
                messages.addMessage(new MessageBuilder().error().source("density").code("fabric.required").build());
            if (StringUtils.isEmpty(fabricWidthType))
                messages.addMessage(new MessageBuilder().error().source("fabricWidthType").code("fabric.required").build());
            if (StringUtils.isEmpty(fabricTechnology))
                messages.addMessage(new MessageBuilder().error().source("fabricTechnology").code("fabric.required").build());
            if (StringUtils.isEmpty(fabricSecondTechnology))
                messages.addMessage(new MessageBuilder().error().source("fabricSecondTechnology").code("fabric.required").build());
            if (colors.isEmpty())
                messages.addMessage(new MessageBuilder().error().source("colors").code("fabric.required").build());
            if (StringUtils.isEmpty(fabricProvideType))
                messages.addMessage(new MessageBuilder().error().source("fabricProvideType").code("fabric.required").build());
            if (StringUtils.isEmpty(fabricMeasureType))
                messages.addMessage(new MessageBuilder().error().source("fabricMeasureType").code("fabric.required").build());
            if(ranges.isEmpty())
                messages.addMessage(new MessageBuilder().error().source("ranges").code("fabric.required").build());
            */
        }
        if (context.getUserEvent().equals("temporary")) {
            MessageContext messages = context.getMessageContext();
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("fabric.required").build());
        }
    }
}
