package com.wcc.postcode.services;

public class EquiRectangularDistanceCalculator implements DistanceCalculator{
    private static final double EARTH_RADIUS = 6371;

    @Override
    public double calculate(Position start, Position end) {
        double lat1Rad = Math.toRadians(start.latitude());
        double lat2Rad = Math.toRadians(end.latitude());
        double lon1Rad = Math.toRadians(start.longitude());
        double lon2Rad = Math.toRadians(end.longitude());

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }
}
