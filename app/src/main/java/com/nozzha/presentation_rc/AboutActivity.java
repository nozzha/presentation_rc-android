package com.nozzha.presentation_rc;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String version = "v";

        try {
            // get current version
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            version += pi.versionName + " - " + pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        appVersion = (TextView) findViewById(R.id.appVersion);
        appVersion.setText(version);
    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();

        if (_id == R.id.download_win_app_link) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BuildVars.Links.WIN_APP_DOWNLOAD_PAGE)));
        } else if (_id == R.id.nozzha_website) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BuildVars.Links.NOZZHA_WEBSITE)));
        }
    }
}
