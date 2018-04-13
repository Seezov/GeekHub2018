package com.example.android.geekhub.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Festival implements Parcelable {

    private String name;
    private String location;
    private float price;
    private List<Band> bands;
    private Date startDate;
    private Date endDate;
    private boolean isActive;

    public Festival(String name, String location, float price, List<Band> bands, Date startDate, Date endDate) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.bands = bands;
        this.startDate = startDate;
        this.endDate = endDate;

        updateIsActive();
    }

    private void updateIsActive() {
        Calendar calendar = Calendar.getInstance();
        isActive = calendar.getTimeInMillis() < endDate.getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Band> getBands() {
        return bands;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        updateIsActive();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        updateIsActive();
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeFloat(this.price);
        dest.writeList(this.bands);
        dest.writeLong(this.startDate != null ? this.startDate.getTime() : -1);
        dest.writeLong(this.endDate != null ? this.endDate.getTime() : -1);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
    }

    protected Festival(Parcel in) {
        this.name = in.readString();
        this.location = in.readString();
        this.price = in.readFloat();
        this.bands = new ArrayList<>();
        in.readList(this.bands, Band.class.getClassLoader());
        long tmpStartDate = in.readLong();
        this.startDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
        long tmpEndDate = in.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
        this.isActive = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Festival> CREATOR = new Parcelable.Creator<Festival>() {
        @Override
        public Festival createFromParcel(Parcel source) {
            return new Festival(source);
        }

        @Override
        public Festival[] newArray(int size) {
            return new Festival[size];
        }
    };
}
