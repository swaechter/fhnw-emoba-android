package ch.fhnw.emoba.spherocontrol;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import ch.fhnw.emoba.spherocontrol.tabs.TabListener;
import ch.fhnw.emoba.spherocontrol.tabs.TabsPagerAdapter;

public class DriveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);

        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.addOnTabSelectedListener(new TabListener(tabsPagerAdapter));
        tabLayout.setupWithViewPager(viewPager);
    }
}
