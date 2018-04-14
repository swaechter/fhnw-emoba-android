package ch.fhnw.emoba.spherocontrol;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import ch.fhnw.emoba.spherocontrol.fragments.AimFragment;
import ch.fhnw.emoba.spherocontrol.fragments.SensorFragment;
import ch.fhnw.emoba.spherocontrol.fragments.TouchFragment;
import ch.fhnw.emoba.spherocontrol.models.SpheroWorkerThread;
import ch.fhnw.emoba.spherocontrol.tabs.TabListener;
import ch.fhnw.emoba.spherocontrol.tabs.TabsPagerAdapter;

public class DriveActivity extends FragmentActivity {

    public static SpheroWorkerThread spheroWorkerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        spheroWorkerThread = new SpheroWorkerThread("sphero");
        spheroWorkerThread.start();

        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        tabsPagerAdapter.addFragment(new AimFragment(), "Aim");
        tabsPagerAdapter.addFragment(new TouchFragment(), "Touch");
        tabsPagerAdapter.addFragment(new SensorFragment(), "Sensor");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.addOnTabSelectedListener(new TabListener(tabsPagerAdapter));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStop() {
        super.onStop();

        spheroWorkerThread.quit();
    }
}
