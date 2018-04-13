package ch.fhnw.emoba.spherocontrol.fragments;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import ch.fhnw.emoba.spherocontrol.R;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;

public class SensorFragment extends Fragment implements TabbedFragment, SensorEventListener {

    private SensorManager sensorManager;

    private Sensor sensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            throw new RuntimeException("Unable to get the system sensor manager");
        }

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor == null) {
            throw new RuntimeException("Unable to initialize the rotation sensor");
        }

        return inflater.inflate(R.layout.fragment_sensor, container, false);
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
    public void onSensorChanged(SensorEvent event) {
        // TODO: Implement funny stuff in 42 dimensions
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
