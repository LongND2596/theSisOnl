package longnd.thesis.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;

import androidx.databinding.DataBindingUtil;
import longnd.thesis.R;
import longnd.thesis.databinding.PsyProcessBinding;


public class PsyProcess {
    private static boolean shown = false;
    private AlertDialog dialog = null;
    private PsyProcessBinding binding;
    private static PsyProcess instance = null;
    private Context context;

    public static PsyProcess getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new PsyProcess(context);
            return instance;
        }
    }

    private PsyProcess(Context context) {
        this.context = context;
        if (context != null && !PsyProcess.isShown()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.psy_process, null, false);
            View dialogView = binding.getRoot();
            builder.setView(dialogView);
            builder.setCancelable(false);
            dialog = builder.create();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // we cannot close dialog when we press back button
                }
                return false;
            });
            Glide.with(context)
                    .load(R.drawable.image_process)
                    .into(binding.ivProcess);
        }
    }

    public void show() {
        if (!((Activity) context).isFinishing()) {
            if (!PsyProcess.isShown() && dialog != null) {
                forceShown();
                dialog.show();
            }
        }
    }

    public void hidden() {
        if (PsyProcess.isShown() && dialog != null && dialog.isShowing()) {
            initialize();
            dialog.dismiss();
        }
    }

    private static boolean isShown() {
        return shown;
    }

    private static void forceShown() {
        shown = true;
    }

    private static void initialize() {
        shown = false;
    }

    public void destroyLoadingDialog() {
        instance = null;
    }
}

