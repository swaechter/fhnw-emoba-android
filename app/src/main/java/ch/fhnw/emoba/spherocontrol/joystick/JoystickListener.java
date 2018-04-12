package ch.fhnw.emoba.spherocontrol.joystick;

public interface JoystickListener {

    void onJoystickMoved(float xPercent, float yPercent, int id);
}
