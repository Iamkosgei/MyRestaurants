
package com.example.brian.myrestaurants.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Category {

    @SerializedName("alias")
    @Expose
    String alias;
    @SerializedName("title")
    @Expose
    String title;

    public Category() {}

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
