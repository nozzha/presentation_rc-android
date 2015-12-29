package com.nozzha.presentation_rc.core.server;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/22/2015.
 */
public class NetworkServer extends Server {

    private String host;
    private int port;

    private Socket socket;
    private SocketAddress address;

    @Override
    public void init() {
        socket = new Socket();
        address = new InetSocketAddress(host, port);
    }

    @Override
    protected void stopServer() {
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        socket = null;
    }

    @Override
    public void run() {
        try {
            super.run();
            runOrThrow();
        } catch (IOException e) {
            setRunning(false);
            updateServerStatus(false);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void runOrThrow() throws IOException, InterruptedException {
        socket.setSoTimeout(getTimeout());
        socket.connect(address, getTimeout());

        outStream = socket.getOutputStream();

        keepConnectionAlive();

        initRemoteControl();

        updateServerStatus(true);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
