package com.example.android.geekhub.entities;

import com.example.android.geekhub.enums.DesignType;
import com.example.android.geekhub.enums.DimensionType;
import com.example.android.geekhub.enums.MaterialType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ad {

    private long id;
    private Date startDate;
    private Date endDate;
    private String name;

    public Ad () {

    }

    public Ad(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
