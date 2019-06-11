package longnd.thesis.offline.test.test_step_one;

import android.view.View;

import longnd.thesis.R;
import longnd.thesis.databinding.FragmentTestStepOneBinding;
import longnd.thesis.directional.OpenStepTest;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.ToastUtils;
import longnd.thesis.utils.Utils;

public class TestStepOneOffFragment extends BaseFragment<TestStepOneOffViewModel, FragmentTestStepOneBinding> {
    private OpenStepTest openStepTest;

    @Override
    protected void initListenerOnClick() {
        binding.btnStart.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        binding.tvDetail.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
    }

    @Override
    protected void initData() {
        binding.tvDetail.setText(testOffViewModel.getDetail());
    }

    @Override
    public Class<TestStepOneOffViewModel> getModelClass() {
        return TestStepOneOffViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_step_one;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (testOffViewModel.getType() == Define.Question.TYPE_NEO) {
                    if (DataUtils.getInstance().getCustomer().getGender() == -1) {
                        ToastUtils.showToastNotification(getContext(), "Bạn cần cập nhật giới tính trước!");
                        return;
                    }
                }
                PsyLoading.getInstance(getContext()).show();
                openStepTest.openStepTwo();
                break;
        }
    }

    public void setOpenStepTest(OpenStepTest openStepTest) {
        this.openStepTest = openStepTest;
    }
}
