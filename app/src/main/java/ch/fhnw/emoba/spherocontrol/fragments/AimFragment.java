package ch.fhnw.emoba.spherocontrol.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ch.fhnw.emoba.spherocontrol.R;
import ch.fhnw.emoba.spherocontrol.models.SpheroModel;
import ch.fhnw.emoba.spherocontrol.tabs.TabbedFragment;

public class AimFragment extends Fragment implements TabbedFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aim, container, false);

        Button buttonTurnLeft = view.findViewById(R.id.buttonTurnLeft);
        buttonTurnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SpheroModel.turnLeft();
            }
        });

        Button buttonTurnRight = view.findViewById(R.id.buttonTurnRight);
        buttonTurnRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SpheroModel.turnRight();
            }
        });

        return view;
    }

    @Override
    public void onFragmentTabGainedFocus() {
        SpheroModel.setDiscoveryLight(true);
    }

    @Override
    public void onFragmentTabLostFocus() {
        SpheroModel.setDiscoveryLight(false);
        SpheroModel.setZeroHeading();
    }
}
