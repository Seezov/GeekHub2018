package com.example.android.geekhub.entities;

import java.util.List;

public class Shop {

    private Long id;
    private String name;

    public Shop (){

    }

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
