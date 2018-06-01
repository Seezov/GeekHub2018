package com.example.android.geekhub.enums;

import android.support.annotation.StringDef;

@StringDef({SpaceType.WALL, SpaceType.STAND})
public @interface SpaceType {
    String WALL = "Wall";
    String STAND = "Stand";
}

