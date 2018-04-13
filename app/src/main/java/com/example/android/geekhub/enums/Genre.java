package com.example.android.geekhub.enums;

import android.support.annotation.StringDef;

@StringDef({Genre.ROCK, Genre.POP, Genre.RAP, Genre.METAL, Genre.FUNK, Genre.JAZZ, Genre.ELECTRONIC, Genre.POST_GRUNGE})
public @interface Genre {
    String ROCK = "Rock";
    String POP = "Pop";
    String RAP = "Rap";
    String METAL = "Metal";
    String FUNK = "Funk";
    String JAZZ = "Jazz";
    String ELECTRONIC = "Electronic";
    String POST_GRUNGE = "Post-Grunge";
}

