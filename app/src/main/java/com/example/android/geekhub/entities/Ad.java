package com.example.android.geekhub.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.geekhub.enums.DesignType;
import com.example.android.geekhub.enums.Dimension;
import com.example.android.geekhub.enums.MaterialType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ad implements Parcelable {

    private String name;
    private Date startDate;
    private Date endDate;
    @Dimension
    private String dimension;
    @MaterialType
    private String material;
    @DesignType
    private String designType;

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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.startDate != null ? this.startDate.getTime() : -1);
        dest.writeLong(this.endDate != null ? this.endDate.getTime() : -1);
        dest.writeString(this.dimension);
        dest.writeString(this.material);
        dest.writeString(this.designType);
    }

    protected Ad(Parcel in) {
        this.name = in.readString();
        long tmpStartDate = in.readLong();
        this.startDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
        long tmpEndDate = in.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
        this.dimension = in.readString();
        this.material = in.readString();
        this.designType = in.readString();
    }

    public static final Creator<Ad> CREATOR = new Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel source) {
            return new Ad(source);
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };
}
