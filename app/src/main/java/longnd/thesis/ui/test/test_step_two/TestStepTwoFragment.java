package longnd.thesis.ui.test.test_step_two;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;
import longnd.thesis.R;
import longnd.thesis.adapter.QuestionPagerAdapter;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.ResultPsy;
import longnd.thesis.data.model.ResultRiasec;
import longnd.thesis.databinding.FragmentTestStepTwoBinding;
import longnd.thesis.network.result.NeoResultResponse;
import longnd.thesis.network.result.PsyResultResponse;
import longnd.thesis.network.result.RiasecResultResponse;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.ui.evaluate.EvaluateActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.SharedPrefs;
import longnd.thesis.utils.ToastUtils;

public class TestStepTwoFragment extends BaseFragment<TestStepTwoViewModel, FragmentTestStepTwoBinding> implements QuestionPagerAdapter.SaveResult {
    private QuestionPagerAdapter questionPagerAdapter;
    private ResultRiasec resultRiasec;
    private ResultPsy resultPsy;

    @Override
    protected void initListenerOnClick() {
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        questionPagerAdapter = new QuestionPagerAdapter(getContext(), testViewModel.getQuestionShows(), viewModel, testViewModel.getType());
        viewModel.initResults(testViewModel.getQuestionShows().size());
        questionPagerAdapter.setSaveResult(this);
        binding.mViewPager.setOffscreenPageLimit(5);
        binding.mViewPager.setAdapter(questionPagerAdapter);
        binding.mViewPager.setAnimationEnabled(true);
        binding.mViewPager.setFadeEnabled(true);
        binding.mViewPager.setFadeFactor(0.5f);

        binding.indicator.setupWithViewPager(binding.mViewPager);
        binding.tvPage.setText("Page 1" + " of " + testViewModel.getQuestionShows().size());

        binding.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.tvPage.setText("Page " + (position + 1) + " of " + testViewModel.getQuestionShows().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {
        PsyLoading.getInstance(getContext()).hidden();
    }

    @Override
    public Class<TestStepTwoViewModel> getModelClass() {
        return TestStepTwoViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_step_two;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initObserve() {
        viewModel.getSubmitNeoSuccess().observe(getViewLifecycleOwner(), this::observeSubmitNeo);
        viewModel.getSubmitRiasecSuccess().observe(getViewLifecycleOwner(), this::observeSubmitRiasec);
        viewModel.getSubmitPsychoSuccess().observe(getViewLifecycleOwner(), this::observeSubmitPsycho);
    }

    private void observeSubmitPsycho(ObjectResponse<PsyResultResponse.DataPsy> resultPsy) {
        if (resultPsy == null) {
            return;
        }
        if (resultPsy.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getSubmitPsychoSuccess().removeObservers(this);
            viewModel.setSubmitPsychoSuccess(null);
            DataUtils.getInstance().resultPsycho = resultPsy.getData().result;
            openEvaluateActivity();
        }
    }

    private void observeSubmitRiasec(ObjectResponse<RiasecResultResponse.DataRiasec> dataRiasec) {
        if (dataRiasec == null) {
            return;
        }
        if (dataRiasec.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getSubmitRiasecSuccess().removeObservers(this);
            viewModel.setSubmitRiasecSuccess(null);
            Log.d("TAGs", "Thuyet phuc " + dataRiasec.getData().results.thuyetPhuc);
            Log.d("TAGs", "Kham pha " + dataRiasec.getData().results.khamPha);
            DataUtils.getInstance().resultRiasec = dataRiasec.getData();
            openEvaluateActivity();
        }
    }

    private void observeSubmitNeo(ObjectResponse<NeoResultResponse.DataNeo> dataNeo) {
        if (dataNeo == null) {
            return;
        }
        if (dataNeo.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getSubmitNeoSuccess().removeObservers(this);
            viewModel.setSubmitNeoSuccess(null);
            DataUtils.getInstance().resultNeo = dataNeo.getData().getResults();
            Log.d("TAGs", " " + DataUtils.getInstance().resultNeo.get(0).getScore());
            Log.d("TAGs", " " + DataUtils.getInstance().resultNeo.get(1).getScore());
            Log.d("TAGs", " " + DataUtils.getInstance().resultNeo.get(2).getScore());
            Log.d("TAGs", " " + DataUtils.getInstance().resultNeo.get(3).getScore());
            Log.d("TAGs", " " + DataUtils.getInstance().resultNeo.get(4).getScore());

            openEvaluateActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                // kiểm tra điều kiện, thỏa mãn mới làm tiếp
                for (int item = 0; item < viewModel.getResuls().length; item++) {
                    if (viewModel.getResuls()[item] == -1) {
                        ToastUtils.showToastNotification(getContext(), "Vui lòng chọn 1 trong các tùy chọn sau");
                        binding.mViewPager.setCurrentItem(item);
                        return;
                    }
                }
                PsyLoading.getInstance(getContext(), binding.mViewPager).show();
                if (testViewModel.getType() == Define.Question.TYPE_NEO) {
                    viewModel.submitNeo();
                } else if (testViewModel.getType() == Define.Question.TYPE_RIASEC) {
                    viewModel.submitRiasec();
                } else {
                    viewModel.submitPsy();
                }
                break;

        }
    }

    private void openEvaluateActivity() {
        PsyLoading.getInstance(getContext(), binding.mViewPager).hidden();
        Intent intent = new Intent(getActivity(), EvaluateActivity.class);
        intent.putExtra(Fields.KEY_TYPE, testViewModel.getType());
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onSaveDataResult(int position, int questionId, int typeId, int result) {
        double score = 0;
        switch (testViewModel.getType()) {
            case Define.Question.TYPE_NEO:
                score = testViewModel.getScoreNeo().get(result);
                break;
            case Define.Question.TYPE_RIASEC:
                score = testViewModel.getScoreRiasec().get(result);
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                switch (typeId) {
                    case 1:
                        score = DataUtils.getInstance().scoreLoAu.get(result);
                        break;
                    case 2:
                        score = DataUtils.getInstance().scoreTramCam.get(result);
                        break;
                    case 3:
                        score = DataUtils.getInstance().scoreStress.get(result);
                        break;
                    case 4:
                        score = DataUtils.getInstance().scoreKhoTapTrung.get(result);
                        break;
                    case 5:
                        score = DataUtils.getInstance().scoreTangDong.get(result);
                        break;
                    case 6:
                        score = DataUtils.getInstance().scoreKKVeGiaoTiepXaHoi.get(result);
                        break;
                    case 7:
                        score = DataUtils.getInstance().scoreKHHocTap.get(result);
                        break;
                    case 8:
                        score = DataUtils.getInstance().scoreKKTrongDinhHuongNgheNghiep.get(result);
                        break;
                    case 9:
                        score = DataUtils.getInstance().scoreKKTrongMoiQuanHeChaMe.get(result);
                        break;
                    case 10:
                        score = DataUtils.getInstance().scoreKKTrongMoiQuanHeThayCo.get(result);
                        break;
                    case 11:
                        score = DataUtils.getInstance().scoreKKTrongMoiQuanHeBanBe.get(result);
                        break;
                    case 12:
                        score = DataUtils.getInstance().scoreHanhViThachThucChongDoi.get(result);
                        break;
                    case 13:
                        score = DataUtils.getInstance().scoreRoiLoanHanhViUngXu.get(result);
                        break;
                    case 14:
                        score = DataUtils.getInstance().scoreGayHan.get(result);
                        break;
                }
                break;
        }
        viewModel.setDataResults(position, questionId, result, score);
        Log.d("TAGs", position + " " + questionId + " " + result);
        if (SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_NEXT_TAP, false)) {
            binding.mViewPager.setCurrentItem(position + 1);
        }
    }
}
