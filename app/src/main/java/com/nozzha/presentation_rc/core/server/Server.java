package com.nozzha.presentation_rc.core.server;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/22/2015.
 */
public abstract class Server implements Runnable {

    private OnServerStatusChangedListener statusListener;

    private boolean running = false;
    private int timeout = 5 * 1000;

    protected OutputStream outStream;

    protected RemoteControl remoteControl;

    public void start() {
        if (running) {
            throw new RuntimeException("Server is already running");
        }

        running = true;

        Thread thread = new Thread(this);
        thread.start();
    }

    protected void keepConnectionAlive() {
        KeepAlive keepAlive = new KeepAlive(this);
        keepAlive.start();
    }

    protected void initRemoteControl() {
        remoteControl = new RemoteControl(outStream);
        remoteControl.start();
    }

    public void stop() {
        if (!running) {
            throw new RuntimeException("Server is not running yet");
        }

        running = false;

        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        outStream = null;

        stopServer();

        if(statusListener != null) {
            statusListener.OnServerStatusChanged(false);
        }
    }

    protected void updateServerStatus(boolean connected) {
        if(statusListener == null) {
            return;
        }

        statusListener.OnServerStatusChanged(connected);
    }

    abstract protected void init();

    abstract protected void stopServer();

    @Override
    public void run() {
        init();
    }

    protected void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public OnServerStatusChangedListener getStatusListener() {
        return statusListener;
    }

    public void setStatusListener(OnServerStatusChangedListener statusListener) {
        this.statusListener = statusListener;
    }

    public RemoteControl getRemoteControl() {
        return remoteControl;
    }
}
