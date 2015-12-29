package com.nozzha.presentation_rc;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/29/2015.
 */
public class BuildVars {

    // Settings defaults
    public static final class Defaults {
        public static final String IP_ADDRESS = null;
        public static final int PORT = 3020;
        public static final boolean LEARNT_VOL_CTRLS = false;
        public static final boolean LEARNT_DOWN_WIN_APP = false;
    }

    public static final class Links {
        public static final String WIN_APP_DOWNLOAD_PAGE = "http://store.nozzha.com/apps/view/presentation_rc/";
        public static final String NOZZHA_WEBSITE = "http://nozzha.com/";
    }

    public static final class Server {
        public static final int KEEP_ALIVE_INTERVAL = 3000;
        public static final char CMD_KEEP_ALIVE = 'k';
        public static final char CMD_NEXT_SLIDE = 'n';
        public static final char CMD_PREVIOUS_SLIDE = 'p';
    }

}
