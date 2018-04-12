package ch.fhnw.emoba.spherocontrol.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.joystick.JoystickListener;
import ch.fhnw.emoba.spherocontrol.joystick.JoystickView;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;

public class TouchFragment extends Fragment implements TabbedFragment, JoystickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new JoystickView(getContext(), this);
    }

    @Override
    public void onFragmentTabGainedFocus() {
    }

    @Override
    public void onFragmentTabLostFocus() {
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        Log.d("sphero", "===============> Moved: " + xPercent + " " + yPercent + " " + id);
    }
}
