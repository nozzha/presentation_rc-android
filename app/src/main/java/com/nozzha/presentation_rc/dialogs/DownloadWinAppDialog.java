package com.nozzha.presentation_rc.dialogs;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nozzha.presentation_rc.BuildVars;
import com.nozzha.presentation_rc.R;
import com.nozzha.presentation_rc.core.util.Settings;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/28/2015.
 */
public class DownloadWinAppDialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    final Context context;

    protected boolean showAgain = true;

    protected DownloadWinAppDialog(Context context) {
        this.context = context;
    }

    protected AlertDialog build() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.download_win_app);

        builder.setView(getView());

        builder.setNeutralButton(R.string.dismiss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!showAgain) {
                    Settings.setLearntDownloadWinApp(context, true);
                }
            }
        });

        return builder.create();
    }

    protected View getView() {
        View view = View.inflate(context, R.layout.dialog_tutorial, null);

        TextView txtTop = (TextView) view.findViewById(R.id.txtTop);
        txtTop.setText(R.string.download_win_app_msg);

        ImageView winAppImg = (ImageView) view.findViewById(R.id.winAppImg);
        winAppImg.setVisibility(View.GONE);

        TextView linkBottom = (TextView) view.findViewById(R.id.linkBottom);
        linkBottom.setVisibility(View.VISIBLE);
        linkBottom.setText(R.string.download_win_app_link);
        linkBottom.setOnClickListener(this);

        CheckBox showAgain = (CheckBox) view.findViewById(R.id.showAgain);
        showAgain.setVisibility(View.VISIBLE);
        showAgain.setOnCheckedChangeListener(this);

        return view;
    }

    protected void setWinAppImgHeight(final ImageView img) {
        ViewTreeObserver treeObserver = img.getViewTreeObserver();
        treeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                img.getViewTreeObserver().removeOnPreDrawListener(this);

                int w = img.getMeasuredWidth();

                img.setLayoutParams(new LinearLayout.LayoutParams(w, (int) (w * 0.75)));

                return true;
            }
        });
    }

    protected AlertDialog getDialog() {
        AlertDialog dialog = build();

        dialog.setCancelable(true);

        return dialog;
    }

    public static AlertDialog create(Context context) {
        DownloadWinAppDialog dialog = new DownloadWinAppDialog(context);

        return dialog.getDialog();
    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();

        if(_id == R.id.linkBottom) {
            try {
                context.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(BuildVars.Links.WIN_APP_DOWNLOAD_PAGE)
                ));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, R.string.no_app_can_handle_op, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int _id = buttonView.getId();

        if(_id == R.id.showAgain) {
            showAgain = isChecked;
        }
    }
}
