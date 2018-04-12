package ch.fhnw.emoba.spherocontrol;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import ch.fhnw.emoba.spherocontrol.fragments.AimFragment;
import ch.fhnw.emoba.spherocontrol.fragments.SensorFragment;
import ch.fhnw.emoba.spherocontrol.fragments.TouchFragment;
import ch.fhnw.emoba.spherocontrol.tabs.TabListener;
import ch.fhnw.emoba.spherocontrol.tabs.TabsPagerAdapter;

public class DriveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager());
        tabsPagerAdapter.addFragment(new AimFragment(), "Aim");
        tabsPagerAdapter.addFragment(new TouchFragment(), "Touch");
        tabsPagerAdapter.addFragment(new SensorFragment(), "Sensor");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.addOnTabSelectedListener(new TabListener(tabsPagerAdapter));
        tabLayout.setupWithViewPager(viewPager);
    }
}
