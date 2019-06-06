package longnd.thesis.ui.customer.signin;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import longnd.thesis.R;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.databinding.FragmentSignInBinding;
import longnd.thesis.di.OnOpenCustomer;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.ToastUtils;
import longnd.thesis.utils.Utils;

public class SignInFragment extends BaseFragment<SignInViewModel, FragmentSignInBinding> {
    private Customer customer;
    private OnOpenCustomer onOpenCustomer;

    @Override
    protected void initListenerOnClick() {
        binding.tvSignUp.setOnClickListener(this);
        binding.btnSignIn.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {
        viewModel.getObserveCustomerByEmail().observe(getViewLifecycleOwner(), this::observeGetCustomer);

        viewModel.getIsLoginSuccess().observe(getViewLifecycleOwner(), this::observeLogin);
    }

    private void observeLogin(ObjectResponse<Boolean> objectFail) {
        if (objectFail == null) {
            return;
        }
        if (objectFail.getStatus() == Define.ResponseStatus.SUCCESS) {
            PsyLoading.getInstance(getContext()).hidden();
            viewModel.setIsLoginSuccess(null);
            viewModel.getIsLoginSuccess().removeObservers(this);
            if (!objectFail.getData()) {
                Snackbar.make(getView(), "Tài khoản hoặc mật khẩu không đúng", Snackbar.LENGTH_SHORT).show();
            } else {
                onOpenCustomer.openCustomer();
            }
        }
    }

    @Override
    protected void initView() {
        binding.editEmail.setError(null);
        binding.editPass.setError(null);
        binding.tvTitle1.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_NABILA));
    }

    @Override
    protected void initData() {
        mainViewModel.setNumberBack(Fields.DONT_BACK);
    }

    @Override
    public Class<SignInViewModel> getModelClass() {
        return SignInViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.editPass.setText("");
        binding.editEmail.setText("");
    }

    @Override
    public void onClick(View v) {
        Utils.hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.tvSignUp:
                onOpenCustomer.openSignUpCustomer();
                break;
            case R.id.btnSignIn:
                Utils.hideKeyboard(getActivity());
                String email = binding.editEmail.getText().toString();
                String pass = binding.editPass.getText().toString();
                if (!avalidData(email, pass)) {
                    return;
                }
                PsyLoading.getInstance(getContext()).show();
                viewModel.loginServer(email, pass);
                break;
        }
    }

    /**
     * used to check avlid data login
     *
     * @param email
     * @param pass
     * @return
     */
    private boolean avalidData(String email, String pass) {
        if (email.isEmpty()) {
            showInputError(binding.editEmail, "Thiếu trường email");
            return false;
        }

        if (!viewModel.isValidDEmail(email)) {
            showInputError(binding.editEmail, "Email chưa chính xác");
            return false;
        }

        if (pass.isEmpty()) {
            showInputError(binding.editPass, "Thiếu trường password");
            return false;
        }

        if (pass.length() < 6) {
            showInputError(binding.editPass, "Password tối thiểu 6 ký tự");
            return false;
        }
        return true;
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
                Customer customer = customerObjectResponse.getData();
                PsyLoading.getInstance(getContext()).hidden();
                Utils.onSaveCustomerInSharedPrefs(customer.getEmail(), customer.getPass());
                DataUtils.getInstance().setCustomer(customerObjectResponse.getData());
                onOpenCustomer.openCustomer();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.setCustomerByEmail(null);
                PsyLoading.getInstance(getContext()).hidden();
                ToastUtils.showToastError(getContext());
                break;
        }
    }


    public void setOnOpenCustomer(OnOpenCustomer onOpenCustomer) {
        this.onOpenCustomer = onOpenCustomer;
    }

}
