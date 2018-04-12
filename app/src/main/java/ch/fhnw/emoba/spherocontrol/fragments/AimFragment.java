package ch.fhnw.emoba.spherocontrol.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;
import ch.fhnw.emoba.spherocontrol.R;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;
import ch.fhnw.emoba.spherocontrol.utils.Utils;

public class AimFragment extends Fragment implements TabbedFragment {

    private SpheroRobotProxy spheroRobotProxy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        spheroRobotProxy = SpheroRobotFactory.getActualRobotProxy();

        View view = inflater.inflate(R.layout.fragment_aim, container, false);
        //changeDiscoveryLights(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        changeDiscoveryLights(isVisibleToUser);
    }

    private void changeDiscoveryLights(boolean discoverStatus) {
        if (spheroRobotProxy != null) {
            float ledRange = discoverStatus ? 0.0f : 0.5f;
            float backLedBrightness = discoverStatus ? 1.0f : 0.0f;

            spheroRobotProxy.setLed(ledRange, ledRange, ledRange);
            Utils.sleep(100); // Wait some time so the LED light can change their status
            spheroRobotProxy.setBackLedBrightness(backLedBrightness);
        }
    }

    @Override
    public void onFragmentTabGainedFocus() {
        Log.d("sphero", "=============> Resume");

    }

    @Override
    public void onFragmentTabLostFocus() {
        Log.d("sphero", "=============> Pause");
    }
}
