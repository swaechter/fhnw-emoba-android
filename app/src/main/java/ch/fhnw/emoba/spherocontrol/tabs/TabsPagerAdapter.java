package ch.fhnw.emoba.spherocontrol.tabs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import ch.fhnw.emoba.spherocontrol.fragments.AimFragment;
import ch.fhnw.emoba.spherocontrol.fragments.SensorFragment;
import ch.fhnw.emoba.spherocontrol.fragments.TouchFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private final Map<String, Fragment> fragments;

    public TabsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        this.fragments = new LinkedHashMap<>();
        this.fragments.put("Aim", new AimFragment());
        this.fragments.put("Touch", new TouchFragment());
        this.fragments.put("Sensor", new SensorFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return new ArrayList<>(fragments.values()).get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new ArrayList<>(fragments.keySet()).get(position);
    }
}
