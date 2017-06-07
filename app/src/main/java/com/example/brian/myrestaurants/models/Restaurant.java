
package com.example.brian.myrestaurants.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Restaurant {

    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("image_url")
    @Expose
    String imageUrl;
    @SerializedName("is_closed")
    @Expose
    Boolean isClosed;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("review_count")
    @Expose
    Integer reviewCount;
    @SerializedName("categories")
    @Expose
    List<Category> categories = null;
    @SerializedName("rating")
    @Expose
    Double rating;
    @SerializedName("coordinates")
    @Expose
    Coordinates coordinates;
    @SerializedName("transactions")
    @Expose
    List<String> transactions = null;
    @SerializedName("price")
    @Expose
    String price;
    @SerializedName("location")
    @Expose
    Location location;
    @SerializedName("phone")
    @Expose
    String phone;
    @SerializedName("display_phone")
    @Expose
    String displayPhone;
    @SerializedName("distance")
    @Expose
    Double distance;

    /**
     * No args constructor for use in serialization
     *
     */
    public Restaurant() {
    }

    /**
     *
     * @param transactions
     * @param phone
     * @param imageUrl
     * @param location
     * @param url
     * @param displayPhone
     * @param id
     * @param distance
     * @param price
     * @param name
     * @param reviewCount
     * @param categories
     * @param rating
     * @param isClosed
     * @param coordinates
     */
    public Restaurant(String id, String name, String imageUrl, Boolean isClosed, String url, Integer reviewCount, List<Category> categories, Double rating, Coordinates coordinates, List<String> transactions, String price, Location location, String phone, String displayPhone, Double distance) {
        super();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.isClosed = isClosed;
        this.url = url;
        this.reviewCount = reviewCount;
        this.categories = categories;
        this.rating = rating;
        this.coordinates = coordinates;
        this.transactions = transactions;
        this.price = price;
        this.location = location;
        this.phone = phone;
        this.displayPhone = displayPhone;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getWebsite() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<String> getCategories() {
//         return categories.stream().map(Category::getTitle).collect(Collectors.toList());
        List<String> titles = new ArrayList<>();
        for (Category category:
             categories) {
            titles.add(category.getTitle());
        }

        return titles;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
