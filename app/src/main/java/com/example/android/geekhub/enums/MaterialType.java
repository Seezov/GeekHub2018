package com.example.android.geekhub.enums;

import android.support.annotation.StringDef;

@StringDef({MaterialType.METAL, MaterialType.PAPER})
public @interface MaterialType {
    String METAL = "Metal";
    String PAPER = "Paper";
}

