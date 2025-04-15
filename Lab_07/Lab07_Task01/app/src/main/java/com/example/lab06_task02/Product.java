package com.example.lab06_task02;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("title")
    String title;

    @SerializedName("price")
    String price;

    @SerializedName("description")
    String description;

    @SerializedName("rating")
    float rating;

    @SerializedName("thumbnail")
    String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getThumbnail() {
        return url;
    }

    public void setThumbnail(String url) {
        this.url = url;
    }
}

