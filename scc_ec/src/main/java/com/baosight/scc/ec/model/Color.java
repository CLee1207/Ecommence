package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by ThinkPad on 2014/5/6.
 */
//颜色类
@Embeddable
public class Color implements Serializable {
    private int id;

    private String rgb; //rgb颜色

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }
}
