package longnd.thesis.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import longnd.thesis.R;

public class DialogBuilder {
    public static class NoticeDialog extends DialogFragment implements View.OnClickListener {
        private static final String ARG_TITLE = "title";
        private static final String ARG_MESSAGE = "message";
        private static final String ARG_BUTTON_TEXT = "button";
        private static final String ARG_CANCELED_ON_TOUCH_OUTSIDE = "cancel_outside";

        private OnClickListener onClickListener;
        private OnDialogBackPress onDialogBackPress;

        private String mTitle, mMessage, mButtonText;

        public static NoticeDialog newInstance(String message) {
            NoticeDialog noticeDialog = new NoticeDialog();

            Bundle bundle = new Bundle();
            bundle.putString(ARG_MESSAGE, message);
            noticeDialog.setArguments(bundle);
            return noticeDialog;
        }

        public static NoticeDialog newInstance(String message, String button) {
            NoticeDialog noticeDialog = new NoticeDialog();

            Bundle bundle = new Bundle();
            bundle.putString(ARG_MESSAGE, message);
            bundle.putString(ARG_BUTTON_TEXT, button);
            noticeDialog.setArguments(bundle);
            return noticeDialog;
        }

        public static NoticeDialog newInstance(String title, String message, String button) {
            NoticeDialog noticeDialog = newInstance(message, button);

            noticeDialog.getArguments().putString(ARG_TITLE, title);
            return noticeDialog;
        }

        public static NoticeDialog newInstance(String message, String button, boolean canceledOnTouchOutSide) {
            NoticeDialog noticeDialog = newInstance(message, button);
            Bundle bundle = noticeDialog.getArguments();
            bundle.putBoolean(ARG_CANCELED_ON_TOUCH_OUTSIDE, canceledOnTouchOutSide);
            noticeDialog.setArguments(bundle);
            return noticeDialog;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
            Bundle bundle = getArguments();
            if (bundle != null) {
                mTitle = bundle.getString(ARG_TITLE);
                mMessage = bundle.getString(ARG_MESSAGE);
                mButtonText = bundle.getString(ARG_BUTTON_TEXT);
            }
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity(), getTheme()) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                    if (onDialogBackPress != null) {
                        onDialogBackPress.onDialogBackPress();
                    }
                }
            };
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_white_background);
            }
            return dialog;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Bundle arg = getArguments();
            if (null != arg && null != getDialog()) {
                boolean cancelOutside = arg.getBoolean(ARG_CANCELED_ON_TOUCH_OUTSIDE);
                getDialog().setCanceledOnTouchOutside(cancelOutside);
            }
            return inflater.inflate(R.layout.layout_dialog_notice, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            TextView tvOk = view.findViewById(R.id.btn_ok);
            tvOk.setOnClickListener(this);
            if (!TextUtils.isEmpty(mButtonText)) {
                tvOk.setText(mButtonText);
            }

            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(mMessage);

            TextView tvTitle = view.findViewById(R.id.tv_title);
            if (mTitle != null) {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(mTitle);
            } else {
                tvTitle.setVisibility(View.GONE);
            }
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public void setOnDialogBackPress(OnDialogBackPress onDialogBackPress) {
            this.onDialogBackPress = onDialogBackPress;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok:
                    if (onClickListener != null) {
                        if (isShowing()) {
                            dismiss();
                        }
                        onClickListener.onOkClick(null);
                    }
                    break;
            }
        }

        public boolean isShowing() {
            return getDialog() != null
                    && getDialog().isShowing();
        }
    }

    public interface OnClickListener {
        void onOkClick(Object object);

        void onCancelClick();
    }

    public interface OnDialogBackPress {
        void onDialogBackPress();
    }
}
