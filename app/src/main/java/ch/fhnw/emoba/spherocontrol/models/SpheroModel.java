package ch.fhnw.emoba.spherocontrol.models;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;
import ch.fhnw.emoba.spherocontrol.utils.Utils;

public class SpheroModel {

    private static float robotAngle;

    public static void enableDiscoveryLight(boolean discoverStatus) {
        float ledRange = discoverStatus ? 0.0f : 0.5f;
        float backLedBrightness = discoverStatus ? 1.0f : 0.0f;

        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
        spheroRobotProxy.setLed(ledRange, ledRange, ledRange);
        Utils.sleep(100); // Wait some time so the LED light can change its status
        spheroRobotProxy.setBackLedBrightness(backLedBrightness);
    }

    public static void turnRobot(boolean turnLeft) {
        if (turnLeft) {
            robotAngle = (robotAngle + 30 < 360) ? robotAngle + 30 : 0;
        } else {
            robotAngle = (robotAngle - 30 > 30) ? robotAngle - 30 : 360;
        }

        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
        spheroRobotProxy.drive(robotAngle, 0);
    }

    public static void setZeroHeadingRobot() {
        SpheroRobotProxy spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();
        spheroRobotProxy.setZeroHeading();
    }
}
