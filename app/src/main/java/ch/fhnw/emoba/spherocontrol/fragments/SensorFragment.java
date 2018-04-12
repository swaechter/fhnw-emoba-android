package ch.fhnw.emoba.spherocontrol.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.R;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;

public class SensorFragment extends Fragment implements TabbedFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onFragmentTabGainedFocus() {
    }

    @Override
    public void onFragmentTabLostFocus() {
    }
}
