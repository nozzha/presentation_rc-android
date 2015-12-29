package com.nozzha.presentation_rc.core.server;

import com.nozzha.presentation_rc.BuildVars;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/22/2015.
 * Keeps the connection between the android application and windows application alive
 * by sending some chars during the connection at a specified interval
 */
public class KeepAlive extends Thread {

    final private Server server;

    KeepAlive(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            runOrThrow();
        } catch (IOException e) {
            server.setRunning(false);
            server.updateServerStatus(false);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void runOrThrow() throws IOException, InterruptedException {
        OutputStream outStream = server.outStream;

        while(server.isRunning() && outStream != null) {
            outStream.write(BuildVars.Server.CMD_KEEP_ALIVE);
            outStream.flush();

            // Interval
            Thread.sleep(BuildVars.Server.KEEP_ALIVE_INTERVAL);
        }
    }

}
