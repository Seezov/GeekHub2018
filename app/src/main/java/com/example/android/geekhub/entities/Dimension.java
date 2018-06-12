package com.example.android.geekhub.entities;

import com.example.android.geekhub.enums.DimensionType;

public class Dimension {

    private long id;
    @DimensionType
    private String name;

    public Dimension() {
    }

    public Dimension(long id, String name) {
        this.id = id;
        this.name = name;
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
}
