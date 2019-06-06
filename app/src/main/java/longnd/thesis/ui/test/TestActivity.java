package longnd.thesis.ui.test;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import longnd.thesis.R;
import longnd.thesis.databinding.ActivityTestBinding;
import longnd.thesis.directional.OpenStepTest;
import longnd.thesis.directional.SettingTest;
import longnd.thesis.ui.base.BaseActivity;
import longnd.thesis.ui.dialog.DialogSetting;
import longnd.thesis.ui.test.test_step_one.TestStepOneFragment;
import longnd.thesis.ui.test.test_step_two.TestStepTwoFragment;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.SharedPrefs;
import longnd.thesis.utils.Utils;

public class TestActivity extends BaseActivity<TestViewModel, ActivityTestBinding> implements OpenStepTest, SettingTest {
    private TestStepOneFragment testStepOne;
    private TestStepTwoFragment testStepTwo;

    @Override
    protected void initView() {
        testStepOne = new TestStepOneFragment();
        testStepOne.setOpenStepTest(this);
        testStepTwo = new TestStepTwoFragment();
    }

    @Override
    protected void initListenerOnClick() {
        binding.btnBack.setOnClickListener(this);
        binding.btnSettings.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        int type = getIntent().getIntExtra(Define.TYPE_QUESTION, Define.DEFAULT_VALUE);
        if (type == Define.DEFAULT_VALUE) {
            return;
        }
        binding.tvTitle.setText(Utils.getTitleQuestion(type));

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TestViewModel.class);
        viewModel.setType(type);
        viewModel.setDetail(Utils.getDetailQuestion(this, type));
        viewModel.setNumberBack(Fields.ON_BACK);
        addFragmentTestScren(R.id.mContainer, testStepOne, TestStepOneFragment.class.getName());
        PsyLoading.getInstance(this).hidden();
        PsyLoading.getInstance(this).destroyLoadingDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initObserve();
    }

    private void initObserve() {
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnSettings:
                DialogSetting setting = new DialogSetting();
                setting.setSettingTest(this);
                setting.show(getSupportFragmentManager(), setting.getClass().getName());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PsyLoading.getInstance(this).destroyLoadingDialog();
    }

    @Override
    public void openStepTwo() {
        viewModel.setListQuestion();
        addFragmentTestScren(R.id.mContainer, testStepTwo, TestStepTwoFragment.class.getName());
    }

    @Override
    public void setNextTap(boolean isChecked) {
        SharedPrefs.getInstance().putBoolean(Define.SharedPref.KEY_NEXT_TAP, isChecked);
    }
}
