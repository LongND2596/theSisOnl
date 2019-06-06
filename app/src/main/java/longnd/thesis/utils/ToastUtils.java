package longnd.thesis.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import longnd.thesis.R;

public class ToastUtils {
    /**
     * used to show toast error
     *
     * @param context
     */
    public static void showToastError(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_error, null);
        Toast toastError = new Toast(context);
        toastError.setGravity(Gravity.TOP, 0, 0);
        toastError.setDuration(Toast.LENGTH_SHORT);
        toastError.setView(layout);
        toastError.show();
    }

    /**
     * used to show toas success
     *
     * @param context
     * @param messages
     */
    public static void showToastSuccess(Context context, String messages) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_success, null);
        TextView textView = layout.findViewById(R.id.tvTitle);
        textView.setText(messages);
        Toast toastError = new Toast(context);
        toastError.setGravity(Gravity.TOP, 0, 0);
        toastError.setDuration(Toast.LENGTH_SHORT);
        toastError.setView(layout);
        toastError.show();
    }

    /**
     * used to show toas success
     *
     * @param context
     * @param messages
     */
    public static void showToastNotification(Context context, String messages) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_notification, null);
        TextView textView = layout.findViewById(R.id.tvTitle);
        textView.setText(messages);
        Toast toastError = new Toast(context);
        toastError.setGravity(Gravity.TOP, 0, 0);
        toastError.setDuration(Toast.LENGTH_SHORT);
        toastError.setView(layout);
        toastError.show();
    }
}
