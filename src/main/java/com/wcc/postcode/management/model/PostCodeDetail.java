package com.wcc.postcode.management.model;

import com.wcc.postcode.repository.UKPostCodeEntity;

public class PostCodeDetail {
    private String postcode;
    private double latitude;
    private double longitude;

    public PostCodeDetail() {
    }

    public PostCodeDetail(String postcode, double latitude, double longitude) {
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PostCodeDetail(UKPostCodeEntity entity) {
       this(entity.getPostcode(), entity.getLatitude(), entity.getLongitude());
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
