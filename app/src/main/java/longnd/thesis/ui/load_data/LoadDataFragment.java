package longnd.thesis.ui.load_data;

import android.view.View;
import android.widget.Toast;
import longnd.thesis.R;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.databinding.FragmentLoadDataBinding;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.PsyLoading;

public class LoadDataFragment extends BaseFragment<LoadDataViewModel, FragmentLoadDataBinding> {
    @Override
    protected void initListenerOnClick() {
        binding.btnNeo.setOnClickListener(this);
        binding.btnRiasec.setOnClickListener(this);
        binding.btnPsyPocholigical.setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        initObserve();
    }

    @Override
    protected void initObserve() {
        viewModel.getIsInsertListQuestionSuccess().observe(getViewLifecycleOwner(), this::observeInsertListQuestions);
    }

    private void observeInsertListQuestions(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        switch (booleanObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                PsyLoading.getInstance(getContext()).hidden();
                viewModel.setIsInsertListQuestionSuccess(null);
                Toast.makeText(getContext(), "Kiểm tra đi, đã lưu thành công", Toast.LENGTH_SHORT).show();
                break;
            case Define.ResponseStatus.ERROR:
                break;
            default:
                break;
        }
    }

    @Override
    public Class<LoadDataViewModel> getModelClass() {
        return LoadDataViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_load_data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNeo:
                PsyLoading.getInstance(getContext()).show();
                viewModel.saveData(getContext(), 1);
                break;

            case R.id.btnRiasec:
                PsyLoading.getInstance(getContext()).show();
                viewModel.saveData(getContext(), Define.Question.TYPE_RIASEC);
                break;

            case R.id.btnPsyPocholigical:
                PsyLoading.getInstance(getContext()).show();
                viewModel.saveData(getContext(), Define.Question.TYPE_PSY_POCHOLIGICAL);
                break;
        }
    }
}
