package ch.fhnw.emoba.spherocontrol.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.DriveActivity;
import ch.fhnw.emoba.spherocontrol.models.SpheroMath;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;
import ch.fhnw.emoba.spherocontrol.views.VectorView;
import ch.fhnw.emoba.spherocontrol.views.VectorViewListener;

public class SensorFragment extends Fragment implements TabbedFragment, VectorViewListener, SensorEventListener {

    private static final double MIN_ANGLE = 2;

    private SensorManager sensorManager;

    private Sensor sensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new VectorView(getActivity(), this);
    }

    @Override
    public void onStart() {
        super.onStart();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            throw new RuntimeException("Unable to get the system sensor manager");
        }

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null) {
            throw new RuntimeException("Unable to initialize the rotation sensor");
        }
    }

    @Override
    public void onFragmentTabGainedFocus() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onFragmentTabLostFocus() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onMove(float x, float y, float angle, float velocity) {
        // Do nothing
    }

    @Override
    public void onRelease() {
        // Do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO: Implement funny stuff in 42 dimensions
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
