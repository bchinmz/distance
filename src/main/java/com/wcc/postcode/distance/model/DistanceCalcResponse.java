package com.wcc.postcode.distance.model;

import java.util.Objects;

public class DistanceCalcResponse {


    private PostCodeDetail start;
    private PostCodeDetail end;
    private double distance;
    private String unit;

    public DistanceCalcResponse() {
    }

    public DistanceCalcResponse(PostCodeDetail start, PostCodeDetail end, double distance) {
        this.start = start;
        this.end = end;
        this.distance = distance;
        this.unit = "km";
    }

    public PostCodeDetail getStart() {
        return start;
    }

    public void setStart(PostCodeDetail start) {
        this.start = start;
    }

    public PostCodeDetail getEnd() {
        return end;
    }

    public void setEnd(PostCodeDetail end) {
        this.end = end;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "DistanceCalcResponse{" +
                "start=" + start +
                ", end=" + end +
                ", distance=" + distance +
                ", unit='" + unit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DistanceCalcResponse that = (DistanceCalcResponse) o;
        return Double.compare(distance, that.distance) == 0 && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, distance, unit);
    }
}
