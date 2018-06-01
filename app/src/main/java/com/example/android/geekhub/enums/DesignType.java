package com.example.android.geekhub.enums;

import android.support.annotation.StringDef;

@StringDef({DesignType.CHEAP, DesignType.EXPENSIVE})
public @interface DesignType {
    String CHEAP = "Cheap";
    String EXPENSIVE = "Expensive";
}

