package com.wcc.postcode.distance.model;

import com.wcc.postcode.repository.UKPostCodeEntity;

import java.util.Objects;

public class PostCodeDetail {
    private String postCode;
    private String latitude;
    private String longitude;

    public PostCodeDetail() {
    }

    public PostCodeDetail(String postCode, String latitude, String longitude) {
        this.postCode = postCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PostCodeDetail(String postCode, double latitude, double longitude) {
        this.postCode = postCode;
        this.latitude = convertDecimalToDegree(latitude);
        this.longitude = convertDecimalToDegree(longitude);
    }

    public PostCodeDetail(UKPostCodeEntity entity) {
        this(entity.getPostcode(), entity.getLatitude(), entity.getLongitude());
    }

    private String convertDecimalToDegree(double decimalValue) {
        var isNegative = decimalValue < 0 ? "-" : "";
        var absValue = Math.abs(decimalValue);
        var degree = absValue > 0 ? Math.floor(absValue) : 0;
        var minuteTemp = (absValue - degree) * 60;
        var minute = minuteTemp > 0 ? Math.floor(minuteTemp) : 0;
        var seconds = (minuteTemp - minute) * 60;

        return String.format("%s%.0f° %.0f' %.4f\"", isNegative, degree, minute, seconds);
    }

    public String getPostCode() {
        return postCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "PostCodeDetail{" +
                "postCode='" + postCode + '\'' +
                ", latitude='" + latitude + '\'' +
                ", logitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PostCodeDetail that = (PostCodeDetail) o;
        return Objects.equals(postCode, that.postCode) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postCode, latitude, longitude);
    }
}
