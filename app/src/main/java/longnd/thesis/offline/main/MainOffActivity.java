package longnd.thesis.offline.main;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import longnd.thesis.R;
import longnd.thesis.databinding.ActivityMainBinding;
import longnd.thesis.di.OnOpenCustomer;
import longnd.thesis.offline.customer.CustomerOffFragment;
import longnd.thesis.offline.customer.signin.SignInOffFragment;
import longnd.thesis.offline.customer.signup.SignUpOffFragment;
import longnd.thesis.offline.history.HistoryOffFragment;
import longnd.thesis.offline.home.HomeOffFragment;
import longnd.thesis.ui.base.BaseActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Fields;

public class MainOffActivity extends BaseActivity<MainOffViewModel, ActivityMainBinding> implements OnOpenCustomer {

    private HomeOffFragment homeFragment;
    private SignInOffFragment signInFragment;
    private SignUpOffFragment signUpFragment;
    private CustomerOffFragment customerFragment;
    private HistoryOffFragment historyFragment;

    @Override
    protected void initView() {
        homeFragment = new HomeOffFragment();
        customerFragment = new CustomerOffFragment();
        historyFragment = new HistoryOffFragment();
        signInFragment = new SignInOffFragment();
        signInFragment.setOnOpenCustomer(this);
        signUpFragment = new SignUpOffFragment();
        signUpFragment.setOnOpenCustomer(this);
        customerFragment.setOnOpenCustomer(this);
    }

    @Override
    protected void initData() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainOffViewModel.class);
        viewModel.setNumberBack(Fields.ON_BACK);
        addFragment(R.id.mContainer, homeFragment, HomeOffFragment.class.getName());
    }

    @Override
    protected void initListenerOnClick() {
        binding.ivHome.setOnClickListener(this);
        binding.ivUser.setOnClickListener(this);
        binding.tvHome.setOnClickListener(this);
        binding.tvUser.setOnClickListener(this);
        binding.tvHistory.setOnClickListener(this);
        binding.ivHistory.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHome:
            case R.id.tvHome:
                openHomeFragment();
                break;
            case R.id.ivUser:
            case R.id.tvUser:
                openCustomerFragment();
                break;
            case R.id.tvHistory:
            case R.id.ivHistory:
                openHistoryFragment();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (viewModel.getNumberBack() == Fields.ON_BACK) {
            super.onBackPressed();
        } else {
            openHomeFragment();
        }
    }

    private void openHistoryFragment() {
        addFragment(R.id.mContainer, historyFragment, HistoryOffFragment.class.getName());
        binding.ivHistory.setImageResource(R.drawable.ic_history_selected);
        binding.tvHistory.setTextColor(getResources().getColor(R.color.main_text_select));
        binding.ivUser.setImageResource(R.drawable.icon_user_main);
        binding.tvUser.setTextColor(getResources().getColor(R.color.main_text));
        binding.ivHome.setImageResource(R.drawable.icon_home);
        binding.tvHome.setTextColor(getResources().getColor(R.color.main_text));
    }

    private void openHomeFragment() {
        addFragment(R.id.mContainer, homeFragment, HomeOffFragment.class.getName());
        binding.ivHome.setImageResource(R.drawable.icon_home_select);
        binding.tvHome.setTextColor(getResources().getColor(R.color.main_text_select));
        binding.ivUser.setImageResource(R.drawable.icon_user_main);
        binding.tvUser.setTextColor(getResources().getColor(R.color.main_text));
        binding.ivHistory.setImageResource(R.drawable.ic_history);
        binding.tvHistory.setTextColor(getResources().getColor(R.color.main_text));
    }

    private void openCustomerFragment() {
        if (DataUtils.getInstance().getCustomer() != null) {
            addFragment(R.id.mContainer, customerFragment, CustomerOffFragment.class.getName());
        } else {
            addFragment(R.id.mContainer, signInFragment, SignInOffFragment.class.getName());
        }
        binding.ivUser.setImageResource(R.drawable.icon_user_main_select);
        binding.tvUser.setTextColor(getResources().getColor(R.color.main_text_select));
        binding.ivHome.setImageResource(R.drawable.icon_home);
        binding.tvHome.setTextColor(getResources().getColor(R.color.main_text));
        binding.ivHistory.setImageResource(R.drawable.ic_history);
        binding.tvHistory.setTextColor(getResources().getColor(R.color.main_text));
    }

    @Override
    public void openSignInCustomer() {
        addFragment(R.id.mContainer, signInFragment, SignInOffFragment.class.getName());
    }

    @Override
    public void openSignUpCustomer() {
        addFragment(R.id.mContainer, signUpFragment, SignUpOffFragment.class.getName());
    }

    @Override
    public void openCustomer() {
        addFragment(R.id.mContainer, customerFragment, CustomerOffFragment.class.getName());
    }
}
