package longnd.thesis.utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import longnd.thesis.R;
import longnd.thesis.databinding.PsyNotificationBinding;

public class PsyNotification {
    private static boolean shown = false;
    private static PsyNotification instance;
    private Context context;
    private AlertDialog dialog = null;
    private PsyNotificationBinding binding;

    public static PsyNotification getInstance(Context context, String message) {
        if (instance != null) {
            return instance;
        } else {
            instance = new PsyNotification(context, message);
            return instance;
        }
    }

    private PsyNotification(Context context, String message) {
        this.context = context;
        if (context != null && !PsyNotification.isShown()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.psy_notification, null, false);
            View dialogView = binding.getRoot();
            builder.setView(dialogView);
            dialog = builder.create();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            // binding.ivMessage.setTypeface(Utils.getTypeFace(context, Fields.FONT_NABILA));
            binding.tvMessage.setText(message);
        }
    }

    public void show() {
        shown = false;
        if (!((Activity) context).isFinishing()) {
            if (!PsyNotification.isShown() && dialog != null) {
                forceShown();
                dialog.show();
            }
        }
    }

    public void hidden() {
        if (PsyNotification.isShown() && dialog != null && dialog.isShowing()) {
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
