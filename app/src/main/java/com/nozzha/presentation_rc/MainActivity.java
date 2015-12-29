package com.nozzha.presentation_rc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nozzha.presentation_rc.core.server.NetworkServer;
import com.nozzha.presentation_rc.core.server.OnServerStatusChangedListener;
import com.nozzha.presentation_rc.core.server.RemoteControl;
import com.nozzha.presentation_rc.core.util.Settings;
import com.nozzha.presentation_rc.dialogs.DownloadWinAppDialog;
import com.nozzha.presentation_rc.dialogs.VolumeControlsDialog;
import com.nozzha.presentation_rc.dialogs.WhatsThisDialog;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnServerStatusChangedListener {

    protected EditText ipAddressET;
    protected EditText portET;
    protected Button connectBtn;

    /**
     * To update UI when server state changes
     */
    protected Handler handler;

    protected NetworkServer networkServer;

    protected RemoteControl remoteControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipAddressET = (EditText) findViewById(R.id.ipAddressET);
        portET = (EditText) findViewById(R.id.portET);

        connectBtn = (Button) findViewById(R.id.connect);

        initSettings();

        handler = new Handler();

        networkServer = new NetworkServer();
        networkServer.setStatusListener(this);

        // Check if user has unchecked the `Show again` option
        // for the download dialog
        if(!Settings.hasLearntDownloadWinApp(this)) {
            // Tell users that they need to install Nozzha PRC for Windows
            // application in order to control their presentation slides
            AlertDialog dialog = DownloadWinAppDialog.create(this);
            dialog.show();
        }
    }

    /**
     * Load last used IP address and port
     */
    private void initSettings() {
        ipAddressET.setText(Settings.getIPAddress(this));

        if(ipAddressET.getText().length() >= 1) {
            // Move caret to the end of the text
            ipAddressET.setSelection(ipAddressET.getText().length());
        }

        portET.setText("" + Settings.getPort(this));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Listening to volume up/down physical buttons, when server is running,
        // to move slides. And if server is not running, proceeds with default action

        if (!networkServer.isRunning() || remoteControl == null) {
            // Server is not running
            return super.dispatchKeyEvent(event);
        }

        if (event.getAction() != KeyEvent.ACTION_UP) {
            // To avoid calling the action more than once, as there are at least two actions
            // are called when pressing a button `ACTION_UP` & `ACTION_DOWN`.
            // And why listening to `ACTION_UP` instead of `ACTION_DOWN`? That is because
            // if user keep pressing the button the `ACTION_DOWN` would be called more than
            // once.

            return super.dispatchKeyEvent(event);
        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
            remoteControl.nextSlide();

            // `true` means action was handled
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
            remoteControl.previousSlide();
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();

        if (_id == R.id.connect) {
            connectBtn.setEnabled(false);

            if (!networkServer.isRunning()) {
                saveCurrentSettings();

                connectBtn.setText(R.string.connecting);
                networkServer.setHost(Settings.getIPAddress(this));
                networkServer.setPort(Settings.getPort(this));
                networkServer.start();
            } else {
                connectBtn.setText(R.string.disconnecting);
                networkServer.stop();
            }
        } else if (_id == R.id.whatIsThis) {
            AlertDialog dialog = WhatsThisDialog.create(this);
            dialog.show();
        } else if (_id == R.id.aboutLink) {
            startActivity(new Intent(this, AboutActivity.class));
        }
    }

    /**
     * Saves last IP address & port that were used in a successful
     * connection to the Windows application server. So, that they
     * can be loaded using initSettings();
     */
    private void saveCurrentSettings() {
        String ipAddress = ipAddressET.getText().toString();
        int port = Settings.getPort(this);

        try {
            port = Integer.valueOf(portET.getText().toString());
        } catch (Exception e) {
            // FIXME: 12/29/2015 should tell user that the port is invalid
        }

        Settings.setIPAddress(this, ipAddress);
        Settings.setPort(this, port);
    }

    @Override
    public void OnServerStatusChanged(final boolean connected) {
        // Using handler, because this method is called from another thread
        handler.post(new Runnable() {
            @Override
            public void run() {
                connectBtn.setEnabled(true);

                if (connected) {
                    connectBtn.setText(R.string.disconnect);
                    remoteControl = networkServer.getRemoteControl();


                    // Check if user has unchecked the `Show again` option
                    // for the slides controls dialog
                    if(!Settings.hasLearntVolumeControls(MainActivity.this)) {
                        // Tell users how they can control their presentation slides
                        AlertDialog dialog = VolumeControlsDialog.create(MainActivity.this);
                        dialog.show();
                    }
                } else {
                    connectBtn.setText(R.string.connect);
                    remoteControl = null;
                }
            }
        });
    }
}
