package com.wcc.postcode.services;

import org.springframework.stereotype.Service;

@Service
public class HaversineDistanceCalculator2 implements DistanceCalculator{
    private final static double EARTH_RADIUS = 6371;

    @Override
    public double calculate(Position start, Position end) {
        return calculateDistance(start.latitude(), start.longitude(), end.latitude(), end.longitude());
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
