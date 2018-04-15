package ch.fhnw.emoba.spherocontrol.models;

public class SpheroMath {

    public static float calculateSpheroAngle(double x, double y) {
        double degrees = Math.atan2(x, y) * (180 / Math.PI);
        return (float) (degrees + 360) % 360;
    }

    public static float calculateAngle(double x, double y) {
        double degrees = Math.atan2(y, x) * (180 / Math.PI);
        return (float) (degrees + 360) % 360;
    }

    public static float calculateVelocity(double x, double y) {
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return (float) distance;
    }

    public static boolean isAroundValue(float expectedValue, float givenValue, float diffence) {
        return expectedValue - diffence < givenValue && expectedValue + diffence > givenValue;
    }

}
