package sphero.emoba.fhnw.ch.spherocontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.orbotix.DualStackDiscoveryAgent;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class MainActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener {

    public static final boolean DEBUG = false;

    private SpheroRobotProxy spheroRobotProxy;

    private float angle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spheroRobotProxy = SpheroRobotFactory.createRobot(DEBUG);
        spheroRobotProxy.setDiscoveryListener(this);

        findViewById(R.id.button1).setOnClickListener(view -> {
            spheroRobotProxy.startDiscovering(getApplicationContext());
        });

        findViewById(R.id.button2).setOnClickListener(view -> {
            angle = (angle + 30 < 360) ? angle + 30 : 0;
            spheroRobotProxy.drive(angle, 0);
        });
    }

    @Override
    public void handleRobotChangedState(final SpheroRobotBluetoothNotification spheroRobotBluetoothNotification) {
        switch (spheroRobotBluetoothNotification) {
            case Connecting: {
                Log.i("sphero", "======================> Sphero is connecting");
                break;
            }
            case Connected: {
                Log.i("sphero", "======================> Sphero is connected");
                break;
            }
            case Online: {
                Log.i("sphero", "======================> Sphero is online");
                spheroRobotProxy.setZeroHeading();
                break;
            }
        }
    }
}
