package com.example.android.geekhub.entities;

public class Order {

    private long idAd;
    private long idShop;
    private long idMaterialType;
    private long idDimension;
    private long idDesign;
    private long quantity;
    private long materialPrice;
    private long printPrice;

    public Order() {
    }

    public Order(long idAd, long idShop, long idMaterialType, long idDimension, long idDesign, long quantity, long materialPrice, long printPrice) {
        this.idAd = idAd;
        this.idShop = idShop;
        this.idMaterialType = idMaterialType;
        this.idDimension = idDimension;
        this.idDesign = idDesign;
        this.quantity = quantity;
        this.materialPrice = materialPrice;
        this.printPrice = printPrice;
    }

    public long getIdAd() {
        return idAd;
    }

    public void setIdAd(long idAd) {
        this.idAd = idAd;
    }

    public long getIdShop() {
        return idShop;
    }

    public void setIdShop(long idShop) {
        this.idShop = idShop;
    }

    public long getIdMaterialType() {
        return idMaterialType;
    }

    public void setIdMaterialType(long idMaterialType) {
        this.idMaterialType = idMaterialType;
    }

    public long getIdDimension() {
        return idDimension;
    }

    public void setIdDimension(long idDimension) {
        this.idDimension = idDimension;
    }

    public long getIdDesign() {
        return idDesign;
    }

    public void setIdDesign(long idDesign) {
        this.idDesign = idDesign;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(long materialPrice) {
        this.materialPrice = materialPrice;
    }

    public long getPrintPrice() {
        return printPrice;
    }

    public void setPrintPrice(long printPrice) {
        this.printPrice = printPrice;
    }
}
