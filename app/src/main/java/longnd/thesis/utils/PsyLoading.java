package longnd.thesis.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.github.ybq.android.spinkit.style.CubeGrid;

import longnd.thesis.R;
import longnd.thesis.databinding.PsyLoadingBinding;


public class PsyLoading {
    private static boolean shown = false;
    private AlertDialog dialog = null;
    private PsyLoadingBinding binding;
    private static PsyLoading instance = null;
    private Context context;

    public static PsyLoading getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new PsyLoading(context);
            return instance;
        }
    }

    public static PsyLoading getInstance(Context context, ViewGroup viewGroup) {
        if (instance != null) {
            return instance;
        } else {
            instance = new PsyLoading(context, viewGroup);
            return instance;
        }
    }

    private PsyLoading(Context context) {
        this.context = context;
        if (context != null && !PsyLoading.isShown()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.psy_loading, null, false);
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
            CubeGrid stylePro = new CubeGrid();
            binding.progressBar.setIndeterminateDrawable(stylePro);
        }
    }

    private PsyLoading(Context context, ViewGroup viewGroup) {
        this.context = context;
        if (context != null && !PsyLoading.isShown()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.psy_loading, viewGroup, false);
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
            CubeGrid stylePro = new CubeGrid();
            binding.progressBar.setIndeterminateDrawable(stylePro);
        }
    }

    public void show() {
        if (!((Activity) context).isFinishing()) {
            if (!PsyLoading.isShown() && dialog != null) {
                forceShown();
                dialog.show();
            }
        }
    }

    public void hidden() {
        if (PsyLoading.isShown() && dialog != null && dialog.isShowing()) {
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

