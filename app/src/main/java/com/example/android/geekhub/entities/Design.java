package com.example.android.geekhub.entities;

import com.example.android.geekhub.enums.DesignType;
import com.example.android.geekhub.enums.DimensionType;

public class Design {

    private long id;
    private long idDimension;
    private long idAd;
    @DesignType
    private String name;
    private long price;

    public Design() {
    }

    public Design(long id, long idDimension, long idAd, String name, long price) {
        this.id = id;
        this.idDimension = idDimension;
        this.idAd = idAd;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdDimension() {
        return idDimension;
    }

    public void setIdDimension(long idDimension) {
        this.idDimension = idDimension;
    }

    public long getIdAd() {
        return idAd;
    }

    public void setIdAd(long idAd) {
        this.idAd = idAd;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
