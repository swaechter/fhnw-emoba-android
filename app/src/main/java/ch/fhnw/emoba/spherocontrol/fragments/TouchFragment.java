package ch.fhnw.emoba.spherocontrol.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.DriveActivity;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;
import ch.fhnw.emoba.spherocontrol.views.VectorView;
import ch.fhnw.emoba.spherocontrol.views.VectorViewListener;

public class TouchFragment extends Fragment implements TabbedFragment, VectorViewListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new VectorView(getContext(), this);
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
    public void onMove(float x, float y, float angle) {
        SpheroModel.startDriving(DriveActivity.spheroWorkerThread, x, y);
    }

    @Override
    public void onRelease() {
        SpheroModel.stopDriving(DriveActivity.spheroWorkerThread);
    }
}
