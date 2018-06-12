package com.example.android.geekhub.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.geekhub.enums.DimensionType;
import com.example.android.geekhub.enums.SpaceType;

public class SpaceForAds {

    private Long idShop;
    private Long idDimension;
    private Long idSpaceType;
    private Long quantity;

    public SpaceForAds() {
    }

    public Long getIdShop() {
        return idShop;
    }

    public void setIdShop(Long idShop) {
        this.idShop = idShop;
    }

    public Long getIdDimension() {
        return idDimension;
    }

    public void setIdDimension(Long idDimension) {
        this.idDimension = idDimension;
    }

    public Long getIdSpaceType() {
        return idSpaceType;
    }

    public void setIdSpaceType(Long idSpaceType) {
        this.idSpaceType = idSpaceType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
