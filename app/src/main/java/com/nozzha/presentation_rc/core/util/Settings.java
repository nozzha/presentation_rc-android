package com.nozzha.presentation_rc.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nozzha.presentation_rc.BuildVars;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/25/2015.
 */
public class Settings {

    public static void setIPAddress(Context context, String ipAddress) {
        SharedPreferences file = context.getSharedPreferences("WiFi_Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();

        editor.putString("ip_address", ipAddress);

        editor.commit();
    }

    public static String getIPAddress(Context context) {
        SharedPreferences file = context.getSharedPreferences("WiFi_Settings", Context.MODE_PRIVATE);

        return file.getString("ip_address", BuildVars.Defaults.IP_ADDRESS);
    }

    public static void setPort(Context context, int port) {
        SharedPreferences file = context.getSharedPreferences("WiFi_Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();

        editor.putInt("port", port);

        editor.commit();
    }

    public static int getPort(Context context) {
        SharedPreferences file = context.getSharedPreferences("WiFi_Settings", Context.MODE_PRIVATE);

        return file.getInt("port", BuildVars.Defaults.PORT);
    }

    public static void setLearntVolumeControls(Context context, boolean state) {
        SharedPreferences file = context.getSharedPreferences("UX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();

        editor.putBoolean("learnt_volume_controls", state);

        editor.commit();
    }

    public static boolean hasLearntVolumeControls(Context context) {
        SharedPreferences file = context.getSharedPreferences("UX", Context.MODE_PRIVATE);

        return file.getBoolean("learnt_volume_controls", BuildVars.Defaults.LEARNT_VOL_CTRLS);
    }

    public static void setLearntDownloadWinApp(Context context, boolean state) {
        SharedPreferences file = context.getSharedPreferences("UX", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = file.edit();

        editor.putBoolean("learnt_download_win_app", state);

        editor.commit();
    }

    public static boolean hasLearntDownloadWinApp(Context context) {
        SharedPreferences file = context.getSharedPreferences("UX", Context.MODE_PRIVATE);

        return file.getBoolean("learnt_download_win_app", BuildVars.Defaults.LEARNT_DOWN_WIN_APP);
    }

}
