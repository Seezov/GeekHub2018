package com.example.android.geekhub.enums;

import android.support.annotation.StringDef;

@StringDef({Dimension.LARGE, Dimension.SMALL})
public @interface Dimension {
    String LARGE = "Large";
    String SMALL = "Small";
}

