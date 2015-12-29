package com.nozzha.presentation_rc.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nozzha.presentation_rc.R;
import com.nozzha.presentation_rc.core.util.Settings;

/**
 * Created by Emad Omar <emad2030@gmail.com> on 12/28/2015.
 */
public class VolumeControlsDialog implements CompoundButton.OnCheckedChangeListener {

    final Context context;

    protected boolean showAgain = true;

    protected VolumeControlsDialog(Context context) {
        this.context = context;
    }

    protected AlertDialog build() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.moving_slides);

        builder.setView(getView());

        builder.setNeutralButton(R.string.dismiss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!showAgain) {
                    Settings.setLearntVolumeControls(context, true);
                }
            }
        });

        return builder.create();
    }

    protected View getView() {
        View view = View.inflate(context, R.layout.dialog_tutorial, null);

        TextView txtTop = (TextView) view.findViewById(R.id.txtTop);
        txtTop.setText(R.string.volume_ctrls_msg);

        ImageView winAppImg = (ImageView) view.findViewById(R.id.winAppImg);
        winAppImg.setImageResource(R.drawable.vloume_buttons_tutorial);

        CheckBox showAgain = (CheckBox) view.findViewById(R.id.showAgain);
        showAgain.setVisibility(View.VISIBLE);
        showAgain.setOnCheckedChangeListener(this);

        setWinAppImgHeight(winAppImg);

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
        VolumeControlsDialog dialog = new VolumeControlsDialog(context);

        return dialog.getDialog();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int _id = buttonView.getId();

        if(_id == R.id.showAgain) {
            showAgain = isChecked;
        }
    }
}
