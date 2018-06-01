package com.example.android.geekhub.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.geekhub.enums.Dimension;
import com.example.android.geekhub.enums.SpaceType;

public class SpaceForAds implements Parcelable {

    @SpaceType
    private String spaceType;
    @Dimension
    private String dimension;

    public SpaceForAds(String spaceType, String dimension) {
        this.spaceType = spaceType;
        this.dimension = dimension;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.spaceType);
        dest.writeString(this.dimension);
    }

    protected SpaceForAds(Parcel in) {
        this.spaceType = in.readString();
        this.dimension = in.readString();
    }

    public static final Creator<SpaceForAds> CREATOR = new Creator<SpaceForAds>() {
        @Override
        public SpaceForAds createFromParcel(Parcel source) {
            return new SpaceForAds(source);
        }

        @Override
        public SpaceForAds[] newArray(int size) {
            return new SpaceForAds[size];
        }
    };
}
