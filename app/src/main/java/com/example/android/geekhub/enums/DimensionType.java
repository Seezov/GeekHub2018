package com.example.android.geekhub.enums;

import android.support.annotation.StringDef;

@StringDef({DimensionType.LARGE, DimensionType.SMALL})
public @interface DimensionType {
    String LARGE = "Large";
    String SMALL = "Small";
}

