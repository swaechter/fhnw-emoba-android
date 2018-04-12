package ch.fhnw.emoba.spherocontrol.utils;

public class Utils {

    public static void sleep(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException exception) {
            throw new RuntimeException("Unable to sleep: " + exception.getMessage(), exception);
        }
    }
}
