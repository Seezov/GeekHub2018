package com.example.android.geekhub.entities;

import com.example.android.geekhub.enums.Dimension;

import java.util.List;

public class Shop {

    private Long id;
    private String name;
    private List<Ad> ads;
    private List<SpaceForAds> spacesForAds;

    public Shop (){

    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
