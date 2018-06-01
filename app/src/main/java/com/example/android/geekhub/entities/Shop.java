package com.example.android.geekhub.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.geekhub.enums.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Shop implements Parcelable {

    private String name;
    private List<Ad> ads;
    private List<SpaceForAds> spacesForAds;

    public Shop(String name, List<Ad> ads, List<SpaceForAds> spacesForAds) {
        this.name = name;
        this.ads = ads;
        this.spacesForAds = spacesForAds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public List<SpaceForAds> getSpacesForAds() {
        return spacesForAds;
    }

    public void setSpacesForAds(List<SpaceForAds> spacesForAds) {
        this.spacesForAds = spacesForAds;
    }

    public String getSpacesString() {
        if (spacesForAds.isEmpty()) {
            return "There are no spaces for ads";
        }
        int largeSpaces = 0;
        int smallSpaces = 0;
        for (SpaceForAds space: spacesForAds) {
            if (space.getDimension().equals(Dimension.LARGE) ){
                largeSpaces++;
            } else if (space.getDimension().equals(Dimension.SMALL)) {
                smallSpaces++;
            }
        }
        return "There are " + largeSpaces + " large spaces and " + smallSpaces + " small spaces.";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.ads);
        dest.writeList(this.spacesForAds);
    }

    protected Shop(Parcel in) {
        this.name = in.readString();
        this.ads = new ArrayList<Ad>();
        in.readList(this.ads, Ad.class.getClassLoader());
        this.spacesForAds = new ArrayList<SpaceForAds>();
        in.readList(this.spacesForAds, SpaceForAds.class.getClassLoader());
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
