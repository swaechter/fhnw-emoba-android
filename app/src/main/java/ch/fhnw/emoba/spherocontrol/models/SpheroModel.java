package ch.fhnw.emoba.spherocontrol.models;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class SpheroModel {

    private static float robotAngle;

    public static void setDiscoveryLight(boolean discoverStatus) {
        float ledRange = discoverStatus ? 0.0f : 0.5f;
        float backLedBrightness = discoverStatus ? 1.0f : 0.0f;

        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
        spheroRobotProxy.setLed(ledRange, ledRange, ledRange);
        sleep(100); // Wait some time so the LED light can change its status
        spheroRobotProxy.setBackLedBrightness(backLedBrightness);
    }

    public static void setZeroHeading() {
        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
        spheroRobotProxy.setZeroHeading();
    }

    public static void turnLeft() {
        robotAngle = (robotAngle + 30 < 360) ? robotAngle + 30 : 0;
        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
        spheroRobotProxy.drive(robotAngle, 0);
    }

    public static void turnRight() {
        robotAngle = (robotAngle - 30 > 30) ? robotAngle - 30 : 360;
        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
        spheroRobotProxy.drive(robotAngle, 0);
    }

    private static void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep: " + exception.getMessage(), exception);
        }
    }
}
