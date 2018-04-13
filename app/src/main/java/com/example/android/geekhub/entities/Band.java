package com.example.android.geekhub.entities;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Band implements Parcelable {

    private String name;
    private List<String> genres;

    public Band(String name, List<String> genres) {
        this.name = name;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.genres);
    }

    protected Band(Parcel in) {
        this.name = in.readString();
        this.genres = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Band> CREATOR = new Parcelable.Creator<Band>() {
        @Override
        public Band createFromParcel(Parcel source) {
            return new Band(source);
        }

        @Override
        public Band[] newArray(int size) {
            return new Band[size];
        }
    };
}
