package com.wcc.postcode.services;

import org.springframework.stereotype.Service;

public class HaversineDistanceCalculator implements DistanceCalculator{
    private final static double EARTH_RADIUS = 6371;

    @Override
    public double calculate(Position start, Position end) {
        double lon1Radians = Math.toRadians(start.longitude());
        double lon2Radians = Math.toRadians(end.longitude());
        double lat1Radians = Math.toRadians(start.latitude());
        double lat2Radians = Math.toRadians(end.longitude());
        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c);
        //return calculateDistance(start.latitude(), start.longitude(), end.latitude(), end.longitude());
    }

    private double haversine(double deg1, double deg2) {
        return square(Math.sin((deg1 - deg2) / 2.0));
    }
    private double square(double x) {
        return x * x;
    }

    double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
