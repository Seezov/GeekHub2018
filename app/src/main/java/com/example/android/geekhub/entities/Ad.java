package com.example.android.geekhub.entities;

import com.example.android.geekhub.enums.DesignType;
import com.example.android.geekhub.enums.DimensionType;
import com.example.android.geekhub.enums.MaterialType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ad {

    private long id;
    private String name;
    private Date startDate;
    private Date endDate;
    @DimensionType
    private String dimension;
    @MaterialType
    private String material;
    @DesignType
    private String designType;

    public Ad () {

    }

    public Ad(String name, Date startDate, Date endDate, String dimension, String material, String design) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dimension = dimension;
        this.material = material;
        this.designType = design;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = new Date(startDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = new Date(endDate);
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDesignType() {
        return designType;
    }

    public void setDesignType(String designType) {
        this.designType = designType;
    }

    public String getDateString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(startDate) + " - " + df.format(endDate);
    }

    public String getDesignString() {
        return "Design is " + designType + " and " + dimension;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
