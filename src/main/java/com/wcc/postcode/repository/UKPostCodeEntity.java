package com.wcc.postcode.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "UKPostCode")
public class UKPostCodeEntity {
    @Column(name = "id")
    private Integer id;

    @Id
    @Column(name = "postcode")
    private String postcode;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    public UKPostCodeEntity() {
    }

    public UKPostCodeEntity(Integer id, String postcode, Double latitude, Double longitude) {
        this.id = id;
        this.postcode = postcode;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public UKPostCodeEntity(String[] values) {
        this(
                Integer.valueOf(values[0]),
                values[1],
                Double.valueOf(values[2]),
                Double.valueOf(values[3])
        );
    }

    public String getPostcode() {
        return postcode;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public UKPostCodeEntity toNewCoordinates(double latitude, double longitude) {
        return new UKPostCodeEntity(this.id, this.postcode, latitude, longitude);
    }

    @Override
    public String toString() {
        return "UKPostCodeEntity{" +
                "id=" + id +
                ", postcode='" + postcode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
