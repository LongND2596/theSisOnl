package longnd.thesis.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import longnd.thesis.R;
import longnd.thesis.directional.SettingTest;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.SharedPrefs;

public class DialogSetting extends DialogFragment {
    private SettingTest settingTest;
    private SwitchCompat mSwitchNextTap;
    private TextView buttonResetSync;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSwitchNextTap = view.findViewById(R.id.switchNextTap);
        buttonResetSync = view.findViewById(R.id.buttonResetSync);

        mSwitchNextTap.setChecked(SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_NEXT_TAP, false));
        mSwitchNextTap.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                        settingTest.setNextTap(isChecked)
        );

        buttonResetSync.setOnClickListener(v -> {
            SharedPrefs.getInstance().putString(Define.SharedPref.KEY_SELECT_SYNC, Define.SharedPref.VALUE_DEFAULT_SELECT_SYNC);
            Snackbar.make(view, "Reset Success!", Snackbar.LENGTH_SHORT).show();
        });
    }

    public void setSettingTest(SettingTest settingTest) {
        this.settingTest = settingTest;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
