package longnd.thesis.ui.splash;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import longnd.thesis.R;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.entity.User;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.model.Question;
import longnd.thesis.databinding.ActivitySplashBinding;
import longnd.thesis.offline.main.MainOffActivity;
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
import longnd.thesis.utils.Utils;

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

    /**
     *
     */
    private void doRequestConfiguration() {
        String token = SharedPrefs.getInstance().getString(Define.SharedPref.KEY_TOKEN, Define.SharedPref.VALUE_DEFAULT);
        if (token.equals(Define.SharedPref.VALUE_DEFAULT)) {
            openWorkingScreen();
        } else {
            DataUtils.getInstance().setToken(getResources().getString(R.string.send_token, token));
            viewModel.getProfileByToken(getResources().getString(R.string.send_token, token));
        }
    }

    /**
     *
     */
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

    /**
     *
     */
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

        CubeGrid stylePro = new CubeGrid();
        binding.progressBar.setIndeterminateDrawable(stylePro);
        binding.progressBar.setVisibility(View.VISIBLE);

        /**
         * Select the app
         */
        versionApp = SharedPrefs.getInstance().getString(Define.SharedPref.KEY_SELECT_SYNC, Define.SharedPref.VALUE_DEFAULT_SELECT_SYNC);
        if (versionApp.equals(Define.SharedPref.VALUE_DEFAULT_SELECT_SYNC)) {
            binding.selectTheApp.setVisibility(View.VISIBLE);
        } else {
            DataUtils.getInstance().versionApp = versionApp;
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
        // offline
        customerViewModel.getObserveCustomerByEmail().observe(this, this::observeCustomer);

        // online
        viewModel.getIsGetDataProfileSuccess().observe(this, this::observeGetProfile);

        viewModel.getIsInsertListQuestionSuccess().observe(this, this::observeSaveQuestion);
        viewModel.getListQuestions().observe(this, this::observeListQuestion);

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

                DataUtils.getInstance().versionApp = versionApp;
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
        User user = Utils.getUser();
        if (!user.isEmpty()) {
            customerViewModel.getCustomerByEmail(user.getEmail(), user.getPass());
        } else {
            loadQuestion();
        }
    }

    private void loadQuestion() {
        if (!SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_SAVE_QUESTION, false)) {
            createQuestionDB();
        } else {
            loadingDataQuestion();
        }
    }

    /**
     * Read data and save data in database
     */
    private void createQuestionDB() {
        DataUtils.getInstance().setNeos(viewModel.readDataNeo(this, Define.Question.TYPE_NEO));
        DataUtils.getInstance().setRiasec(viewModel.readDataRiasec(this, Define.Question.TYPE_RIASEC));
        DataUtils.getInstance().setPsychological(viewModel.readDataPsy(this, Define.Question.TYPE_PSY_POCHOLIGICAL));

        viewModel.saveDataInDB();
    }

    /**
     * Load question data from the database
     */
    private void loadingDataQuestion() {
        // kiểm tra nếu có mạng thì kiểm tra version câu hỏi


        // nếu có thì tiến hành cập nhật lại bộ câu hỏi


        // ngược lại nếu không thì tiến hành load data như thường
        viewModel.loadQuestionDataInDB();
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
                if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainOffActivity.class);
                }
            }
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }, 2000);
    }

    /**
     * Observe get list question
     *
     * @param questionListResponse
     */
    private void observeListQuestion(ListResponse<Question> questionListResponse) {
        if (questionListResponse == null) {
            return;
        }
        switch (questionListResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getListQuestions().removeObservers(this);
                viewModel.setListQuestions(null);
                viewModel.classifyTypeQuestion(questionListResponse.getData());
                openWorkingScreen();
                break;
            case Define.ResponseStatus.ERROR:
                break;
        }
    }

    /**
     * Observe Save Question
     *
     * @param booleanObjectResponse
     */
    private void observeSaveQuestion(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        switch (booleanObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                Log.d("LongNDs", "observeSaveQuestion: loading");
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.setIsInsertListQuestionSuccess(null);
                viewModel.getIsInsertListQuestionSuccess().removeObservers(this);
                SharedPrefs.getInstance().putBoolean(Define.SharedPref.KEY_SAVE_QUESTION, true);
                openWorkingScreen();
                break;
            case Define.ResponseStatus.ERROR:
                Log.d("LongNDs", "observeSaveQuestion: error");
                break;
            default:
                break;
        }
    }

    private void observeCustomer(ObjectResponse<Customer> customerObjectResponse) {
        if (customerObjectResponse == null) {
            return;
        }
        customerViewModel.getObserveCustomerByEmail().setValue(null);
        switch (customerObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                Customer customer = customerObjectResponse.getData();
                if (customer != null) {
                    ToastUtils.showToastSuccess(this, "Chào mừng " + customer.getLastName());
                    DataUtils.getInstance().setCustomer(customer);
                }
                loadQuestion();
                break;
            case Define.ResponseStatus.ERROR:
                Toast.makeText(this, "Rất tiếc, việc lấy dữ liệu đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}