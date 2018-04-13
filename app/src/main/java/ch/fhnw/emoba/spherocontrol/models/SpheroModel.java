package ch.fhnw.emoba.spherocontrol.models;

import android.util.Log;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class SpheroModel {

    private static final long DRIVE_BLOCKING_TIME = 200;

    private static long drivingTimestamp = System.currentTimeMillis();

    private static float robotAngle;

    private static float robotVelocity;

    public static void setDiscoveryLight(SpheroWorkerThread spheroWorkerThread, final boolean discoverStatus) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                float ledRange = discoverStatus ? 0.0f : 0.5f;
                float backLedBrightness = discoverStatus ? 1.0f : 0.0f;

                SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
                spheroRobotProxy.setLed(ledRange, ledRange, ledRange);
                sleep(100); // Wait some time so the LED light can change its status
                spheroRobotProxy.setBackLedBrightness(backLedBrightness);
            }
        });
    }

    public static void setZeroHeading(SpheroWorkerThread spheroWorkerThread) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
                spheroRobotProxy.setZeroHeading();
            }
        });
    }

    public static void turnLeft(SpheroWorkerThread spheroWorkerThread) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                robotAngle = (robotAngle + 30 < 360) ? robotAngle + 30 : 0;
                SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
                spheroRobotProxy.drive(robotAngle, 0);
            }
        });
    }

    public static void turnRight(SpheroWorkerThread spheroWorkerThread) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                robotAngle = (robotAngle - 30 > 30) ? robotAngle - 30 : 360;
                SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
                spheroRobotProxy.drive(robotAngle, 0);
            }
        });
    }

    public static void startDriving(SpheroWorkerThread spheroWorkerThread, final double x, final double y) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                long currentTimestamp = System.currentTimeMillis();
                if (drivingTimestamp + DRIVE_BLOCKING_TIME < currentTimestamp) {
                    drivingTimestamp = currentTimestamp;
                    float angle = calculateAngle(x, y);
                    float velocity = calculateVelocity(x, y);
                    if (!isAroundValue(robotAngle, angle, 10) || !isAroundValue(robotVelocity, velocity, 0.1f)) {
                        Log.d("sphero", "Accepted at " + currentTimestamp);
                        robotAngle = angle;
                        robotVelocity = velocity;
                        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
                        spheroRobotProxy.drive(angle, velocity);
                    } else {
                        Log.d("sphero", "Similar value at " + currentTimestamp);
                    }
                } else {
                    Log.d("sphero", "Blocked at " + currentTimestamp);
                }
            }
        });
    }

    public static void stopDriving(SpheroWorkerThread spheroWorkerThread) {
        spheroWorkerThread.postTask(new Runnable() {

            @Override
            public void run() {
                if (robotVelocity != 0) {
                    robotVelocity = 0;
                    SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
                    spheroRobotProxy.drive(robotAngle, robotVelocity);
                }
            }
        });
    }

    private static float calculateAngle(double x, double y) {
        double degrees = Math.atan2(y, x) * (180 / Math.PI);
        return (float) (degrees + 360) % 360;
    }

    private static float calculateVelocity(double x, double y) {
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return (distance <= 1) ? (float) distance : 1;
    }

    private static boolean isAroundValue(float expectedValue, float givenValue, float diffence) {
        return expectedValue - diffence < givenValue && expectedValue + diffence > givenValue;
    }

    private static void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep: " + exception.getMessage(), exception);
        }
    }
}
