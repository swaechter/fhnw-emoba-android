package ch.fhnw.emoba.spherocontrol.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.DriveActivity;
import ch.fhnw.emoba.spherocontrol.joystick.JoystickListener;
import ch.fhnw.emoba.spherocontrol.joystick.JoystickView;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;

public class TouchFragment extends Fragment implements TabbedFragment, JoystickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new JoystickView(getContext(), this);
    }

    @Override
    public void onFragmentTabGainedFocus() {
        // No operation
    }

    @Override
    public void onFragmentTabLostFocus() {
        // No operation
    }

    @Override
    public void onJoystickMoved(float x, float y, int id) {
        if (x != 0 && y != 0) {
            SpheroModel.startDriving(DriveActivity.spheroWorkerThread, x, y);
        } else {
            SpheroModel.stopDriving(DriveActivity.spheroWorkerThread);
        }
    }
}
