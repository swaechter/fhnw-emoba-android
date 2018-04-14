package ch.fhnw.emoba.spherocontrol;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class PairingActivity extends FragmentActivity implements SpheroRobotDiscoveryListener {

    public static final boolean DEBUG = Build.PRODUCT.startsWith("sdk");

    private SpheroRobotProxy spheroRobotProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        spheroRobotProxy = SpheroRobotFactory.createRobot(DEBUG);
        spheroRobotProxy.setDiscoveryListener(this);
    }

    public void onConnectButtonClicked(View view) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showErrorAlert("Alert", "Bluetooth permissions were not granted for this app. Please head over to Settings > Apps > Sphero Control > Permissions and grant the Location right!");
        } else if ((bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) && !DEBUG) {
            showErrorAlert("Alert", "The device has no bluetooth adapter or bluetooth isn't enabled. Please enable bluetooth!");
        } else {
            if (!spheroRobotProxy.isDiscovering()) {
                spheroRobotProxy.startDiscovering(getApplicationContext());
                setConnectButtonText("Connecting...");
            } else {
                spheroRobotProxy.stopDiscovering();
                setConnectButtonText("Connect");
            }
        }
    }

    @Override
    public void handleRobotChangedState(SpheroRobotBluetoothNotification spheroRobotBluetoothNotification) {
        switch (spheroRobotBluetoothNotification) {
            case Online: {
                spheroRobotProxy.stopDiscovering();
                setConnectButtonText("Connect");
                Intent intent = new Intent(this, DriveActivity.class);
                startActivity(intent);
                break;
            }
            case Offline: {
                setConnectButtonText("Connect");
                break;
            }
        }
    }

    private void setConnectButtonText(final String buttonText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button connectButton = findViewById(R.id.connectButton);
                connectButton.setText(buttonText);
            }
        });
    }

    private void showErrorAlert(String titleMessage, String errorMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(PairingActivity.this).create();
        alertDialog.setTitle(titleMessage);
        alertDialog.setMessage(errorMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
