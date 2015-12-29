package com.nozzha.presentation_rc.core.server;

import android.os.Build;

import com.nozzha.presentation_rc.BuildVars;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/22/2015.
 * Sends commands to the windows application by writing to the output stream
 * of the current server connection
 */
public class RemoteControl extends Thread {

    final private OutputStream outStream;

    RemoteControl(OutputStream outStream) {
        this.outStream = outStream;
    }

    public void nextSlide() {
        sendCommand(BuildVars.Server.CMD_NEXT_SLIDE);
    }

    public void previousSlide() {
        sendCommand(BuildVars.Server.CMD_PREVIOUS_SLIDE);
    }

    protected void sendCommand(char cmd) {
        try {
            outStream.write(cmd);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
