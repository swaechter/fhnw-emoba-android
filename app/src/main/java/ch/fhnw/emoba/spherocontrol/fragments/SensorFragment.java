package ch.fhnw.emoba.spherocontrol.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.emoba.spherocontrol.activities.DriveActivity;
import ch.fhnw.emoba.spherocontrol.models.SpheroMath;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;
import ch.fhnw.emoba.spherocontrol.views.VectorView;
import ch.fhnw.emoba.spherocontrol.views.VectorViewListener;

public class SensorFragment extends Fragment implements TabbedFragment, VectorViewListener, SensorEventListener {

    private static final double MIN_ANGLE = 2;

    private static final double THROTTLE_RATIO = 0.2;

    private SensorManager sensorManager;

    private Sensor sensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new VectorView(getActivity(), this, VectorView.DrawStrategy.SENSOR);
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
        SpheroModel.stopDriving(DriveActivity.spheroWorkerThread);
    }

    @Override
    public void onMove(float angle, float velocity) {
        // Do nothing
    }

    @Override
    public void onRelease() {
        // Do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[0];
        double y = event.values[1];

        float angle = SpheroMath.calculateSensorAngle(x, y);
        float velocity = (float) Math.max(0, (angle - MIN_ANGLE) / 6d);
        if (velocity > 1.0) {

            velocity = 1.0f;
        }

        velocity *= THROTTLE_RATIO;

        double sum = Math.abs(x) + Math.abs(y);

        if (sum > MIN_ANGLE) {
            SpheroModel.startDriving(DriveActivity.spheroWorkerThread, angle, velocity);
        } else {
            SpheroModel.stopDriving(DriveActivity.spheroWorkerThread);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
