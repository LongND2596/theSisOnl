package longnd.thesis.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import longnd.thesis.R;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.databinding.ActivitySplashBinding;
import longnd.thesis.ui.base.BaseActivity;
import longnd.thesis.ui.customer.CustomerViewModel;
import longnd.thesis.ui.dialog.DialogBuilder;
import longnd.thesis.ui.main.MainActivity;
import longnd.thesis.ui.tutorial.TutorialActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Helpers;
import longnd.thesis.utils.NetworkUtils;
import longnd.thesis.utils.SharedPrefs;
import longnd.thesis.utils.ToastUtils;

public class SplashActivity extends BaseActivity<SplashViewModel, ActivitySplashBinding> {
    private CustomerViewModel customerViewModel;
    private DialogBuilder.NoticeDialog mRetryDialog;
    private String versionApp;

    @Override
    protected void initView() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.my_statusbar_color));
        }
    }

    private void doRequestConfiguration() {
        CubeGrid stylePro = new CubeGrid();
        binding.progressBar.setIndeterminateDrawable(stylePro);
        binding.progressBar.setVisibility(View.VISIBLE);
        String token = SharedPrefs.getInstance().getString(Define.SharedPref.KEY_TOKEN, Define.SharedPref.VALUE_DEFAULT);
        if (token.equals(Define.SharedPref.VALUE_DEFAULT)) {
            openWorkingScreen();
        } else {
            DataUtils.getInstance().setToken(getResources().getString(R.string.send_token, token));
            viewModel.getProfileByToken(getResources().getString(R.string.send_token, token));
        }
    }

    private void showRetryConnectionDialog() {
        handler.postDelayed(() -> {
            boolean dialogShown = mRetryDialog != null && mRetryDialog.getDialog() != null
                    && mRetryDialog.getDialog().isShowing();
            if (!dialogShown) {
                mRetryDialog = DialogBuilder.NoticeDialog.newInstance(
                        getResources().getString(R.string.can_not_connect_to_server),
                        getResources().getString(R.string.retry),
                        false);
                mRetryDialog.setOnClickListener(new DialogBuilder.OnClickListener() {
                    @Override
                    public void onOkClick(Object object) {
                        mRetryDialog.dismiss();
                        onRetryConnectionOk();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });

                mRetryDialog.setOnDialogBackPress(
                        () -> onRetryConnectionOk()
                );
                Helpers.showDialogFragment(getSupportFragmentManager(), mRetryDialog);
            }
        }, 1000);
    }

    private void onRetryConnectionOk() {
        if (NetworkUtils.hasConnection(SplashActivity.this)) {
            doRequestConfiguration();
        } else {
            showRetryConnectionDialog();
        }
    }

    @Override
    protected void initData() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        customerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CustomerViewModel.class);

        /**
         * Select the app
         */
        versionApp = SharedPrefs.getInstance().getString(Define.SharedPref.KEY_SELECT_SYNC, Define.SharedPref.VALUE_DEFAULT_SELECT_SYNC);
        if (versionApp.equals(Define.SharedPref.VALUE_DEFAULT_SELECT_SYNC)) {
            binding.selectTheApp.setVisibility(View.VISIBLE);
        } else {
            openVersionApp();
        }
    }

    @Override
    protected void initListenerOnClick() {
        binding.imageOnline.setOnClickListener(this);
        binding.textCheckOnline.setOnClickListener(this);
        binding.checkOnline.setOnClickListener(this);

        binding.imageOffline.setOnClickListener(this);
        binding.textCheckOffline.setOnClickListener(this);
        binding.checkOffline.setOnClickListener(this);

        binding.buttonSelected.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        versionApp = Define.VERSION_ONL;
        initObserve();
    }

    private void initObserve() {
        viewModel.getIsGetDataProfileSuccess().observe(this, this::observeGetProfile);
    }

    private void observeGetProfile(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        if (booleanObjectResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            if (booleanObjectResponse.getData()) {
                ToastUtils.showToastSuccess(this, "Chào mừng " + DataUtils.getInstance().getUser().getName());
            } else {
                Snackbar.make(getCurrentFocus(), "Đã xảy ra lỗi trong việc lưu thông tin người dùng", Snackbar.LENGTH_SHORT).show();
            }
        }
        viewModel.setIsGetDataProfileSuccess(null);
        viewModel.getIsGetDataProfileSuccess().removeObservers(this);
        openWorkingScreen();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageOnline:
            case R.id.textCheckOnline:
            case R.id.checkOnline:
                binding.checkOnline.setBackgroundResource(R.drawable.bg_select_checkbox);
                binding.checkOffline.setBackgroundResource(R.drawable.bg_no_select_checkbox);
                versionApp = Define.VERSION_ONL;
                break;

            case R.id.imageOffline:
            case R.id.textCheckOffline:
            case R.id.checkOffline:
                binding.checkOnline.setBackgroundResource(R.drawable.bg_no_select_checkbox);
                binding.checkOffline.setBackgroundResource(R.drawable.bg_select_checkbox);
                versionApp = Define.VERSION_OFF;
                break;

            case R.id.buttonSelected:
                if (binding.checkBoxSaveSelected.isChecked()) {
                    SharedPrefs.getInstance().putString(Define.SharedPref.KEY_SELECT_SYNC, versionApp);
                }
                binding.selectTheApp.setVisibility(View.GONE);
                openVersionApp();
                break;
        }
    }

    /**
     * Open the corresponding application version
     */
    private void openVersionApp() {
        if (versionApp.equals(Define.VERSION_ONL)) {
            openOnline();
        } else {
            openOffline();
        }
    }

    /**
     * Open application offline
     */
    private void openOffline() {

    }

    /**
     * Open application online
     */
    private void openOnline() {
        // kiểm tra có kết nối mạng
        if (NetworkUtils.hasConnection(SplashActivity.this)) {
            doRequestConfiguration();
        } else {
            showRetryConnectionDialog();
        }
    }

    private void openWorkingScreen() {
        handler.postDelayed(() -> {
            Intent intent;
            if (!SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_IS_FIRST, false)) {
                intent = new Intent(SplashActivity.this, TutorialActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 2000);
    }
}
