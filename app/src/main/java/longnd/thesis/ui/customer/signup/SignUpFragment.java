package longnd.thesis.ui.customer.signup;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import longnd.thesis.R;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.databinding.FragmentSignUpBinding;
import longnd.thesis.di.OnOpenCustomer;
import longnd.thesis.network.pojo.login.DataError;
import longnd.thesis.network.pojo.login.DataSuccess;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.PsyNotification;
import longnd.thesis.utils.SharedPrefs;
import longnd.thesis.utils.ToastUtils;
import longnd.thesis.utils.Utils;

public class SignUpFragment extends BaseFragment<SignUpViewModel, FragmentSignUpBinding> {
    private OnOpenCustomer onOpenCustomer;
    private String email;
    private String pass;

    @Override
    protected void initListenerOnClick() {
        binding.tvSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {
        viewModel.getIsInsertCustomer().observe(getViewLifecycleOwner(), this::observeInsertCustomer);
        viewModel.getObserveCustomerByEmail().observe(getViewLifecycleOwner(), this::observeGetCustomer);

        viewModel.getIsRegisterFail().observe(getViewLifecycleOwner(), this::observeRegisterFail);
        viewModel.getIsRegisterSuccess().observe(getViewLifecycleOwner(), this::observeRegisterSuccess);
    }

    private void observeRegisterSuccess(ObjectResponse<DataSuccess> registerSuccessObjectResponse) {
        if (registerSuccessObjectResponse == null) {
            return;
        }
        if (registerSuccessObjectResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            PsyLoading.getInstance(getContext()).hidden();
            viewModel.setIsRegisterSuccess(null);
            viewModel.getIsRegisterSuccess().removeObservers(this);
            Snackbar.make(getView(), "Đăng ký thành công", Snackbar.LENGTH_SHORT).show();
            // save data
            SharedPrefs.getInstance().putString(Define.SharedPref.KEY_TOKEN, registerSuccessObjectResponse.getData().getToken());
            if (registerSuccessObjectResponse.getData().getUser() != null) {
                DataUtils.getInstance().setUser(registerSuccessObjectResponse.getData().getUser());
            }
            if (registerSuccessObjectResponse.getData().getProfile() != null) {
                DataUtils.getInstance().setProfile(registerSuccessObjectResponse.getData().getProfile());
            }
            if (registerSuccessObjectResponse.getData().getToken() != null) {
                DataUtils.getInstance().setToken("Bearer " + registerSuccessObjectResponse.getData().getToken());
            }
            onOpenCustomer.openCustomer();
        }
    }

    private void observeRegisterFail(ObjectResponse<DataError> loginErrorObjectResponse) {
        if (loginErrorObjectResponse == null) {
            return;
        }
        if (loginErrorObjectResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            PsyLoading.getInstance(getContext()).hidden();
            viewModel.setIsRegisterFail(null);
            viewModel.getIsRegisterFail().removeObservers(this);
            Snackbar.make(getView(), loginErrorObjectResponse.getData().getEmail().get(0), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void observeInsertCustomer(ObjectResponse<Long> longObjectResponse) {
        if (longObjectResponse == null) {
            return;
        }
        switch (longObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                // TODO: loading
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.setIsInsertCustomer(null);
                viewModel.getIsInsertCustomer().removeObservers(this);
                Utils.onSaveCustomerInSharedPrefs(email, pass);
                viewModel.signInCustomer(email, pass);
                break;
            case Define.ResponseStatus.ERROR:
                PsyLoading.getInstance(getContext()).hidden();
                Toast.makeText(getContext(), "Rất tiếc đăng ký đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void observeGetCustomer(ObjectResponse<Customer> customerObjectResponse) {
        if (customerObjectResponse == null) {
            return;
        }
        switch (customerObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                // TODO: loading
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.setCustomerByEmail(null);
                viewModel.getObserveCustomerByEmail().removeObservers(this);
                DataUtils.getInstance().setCustomer(customerObjectResponse.getData());
                PsyLoading.getInstance(getContext()).hidden();
                onOpenCustomer.openCustomer();
                PsyNotification.getInstance(getContext(), "Chúc mừng bạn đã đăng ký thành công!").show();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.setCustomerByEmail(null);
                viewModel.getObserveCustomerByEmail().removeObservers(this);
                PsyLoading.getInstance(getContext()).hidden();
                ToastUtils.showToastError(getContext());
                break;
        }
    }

    @Override
    protected void initView() {
        binding.tvTitle1.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_NABILA));
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.editFullName.setText("");
        binding.editEmail.setText("");
        binding.editPass.setText("");
        binding.editConfirmPass.setText("");
    }

    @Override
    protected void initData() {

    }

    @Override
    public Class<SignUpViewModel> getModelClass() {
        return SignUpViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onClick(View v) {
        Utils.hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.tvSignIn:
                onOpenCustomer.openSignInCustomer();
                break;
            case R.id.btnSignUp:
                String fullname = binding.editFullName.getText().toString().trim();
                String email = binding.editEmail.getText().toString().trim();
                String pass = binding.editPass.getText().toString().trim();
                String confirmPass = binding.editConfirmPass.getText().toString().trim();
                if (!avalidData(fullname, email, pass, confirmPass)) {
                    return;
                }
                PsyLoading.getInstance(getContext()).show();
                viewModel.regiterServer(fullname, email, pass);
                break;
        }
    }

    /**
     * used to check valid data register
     *
     * @param fullname
     * @param email
     * @param pass
     * @param confirmPass
     * @return
     */
    private boolean avalidData(String fullname, String email, String pass, String confirmPass) {
        if (fullname.isEmpty()) {
            showInputError(binding.editFullName, "Thiếu trường Fullname");
            return false;
        }

        if (fullname.length() < 6) {
            showInputError(binding.editFullName, "Fullname tối thiểu 6 ký tự");
            return false;
        }

        if (email.isEmpty()) {
            showInputError(binding.editEmail, "Thiếu trường email");
            return false;
        }

        if (!viewModel.isValidDEmail(email)) {
            showInputError(binding.editEmail, "Email chưa chính xác");
            return false;
        }

        if (pass.isEmpty()) {
            showInputError(binding.editPass, "Thiếu trường Password");
            return false;
        }

        if (pass.length() < 6) {
            showInputError(binding.editPass, "Password tối thiểu 6 ký tự");
            return false;
        }

        if (!Utils.isValidPassword(pass)) {
            showInputError(binding.editPass, "Password cần ít nhất 6 ký tự có cả chữ hoa, thường, số và ký tự đặc biệt!");
            return false;
        }

        if (confirmPass.isEmpty()) {
            showInputError(binding.editConfirmPass, "Nhập lại mật khẩu");
            return false;
        }

        if (!confirmPass.equals(pass)) {
            showInputError(binding.editConfirmPass, "Không trùng khớp");
            return false;
        }
        return true;
    }

    public void setOnOpenCustomer(OnOpenCustomer onOpenCustomer) {
        this.onOpenCustomer = onOpenCustomer;
    }
}
