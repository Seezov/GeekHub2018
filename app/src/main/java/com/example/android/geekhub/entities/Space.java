package com.example.android.geekhub.entities;

import com.example.android.geekhub.enums.DimensionType;
import com.example.android.geekhub.enums.SpaceType;

public class Space {

    private long id;
    @SpaceType
    private String name;

    public Space() {
    }

    public Space(long id, String name) {
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
