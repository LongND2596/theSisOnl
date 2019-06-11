package longnd.thesis.ui.test.test_step_one;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import longnd.thesis.R;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.databinding.FragmentTestStepOneBinding;
import longnd.thesis.directional.OpenStepTest;
import longnd.thesis.network.entity.QuestionShow;
import longnd.thesis.network.pojo.login.question.NeoQuestionResponse;
import longnd.thesis.network.pojo.login.question.PsyQuestionResponse;
import longnd.thesis.network.pojo.login.question.RiasecQuestionResponse;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.NetworkUtils;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.ToastUtils;
import longnd.thesis.utils.Utils;

public class TestStepOneFragment extends BaseFragment<TestStepOneViewModel, FragmentTestStepOneBinding> {
    private OpenStepTest openStepTest;

    @Override
    protected void initListenerOnClick() {
        binding.btnStart.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {
        viewModel.getQuestionsNeo().observe(getViewLifecycleOwner(), this::observeGetQuestionsNeo);
        viewModel.getQuestionsRiasec().observe(getViewLifecycleOwner(), this::observeGetQuestionsRiasec);
        viewModel.getQuestionPsy().observe(getViewLifecycleOwner(), this::observeGetQuestionsPsy);
    }

    private void observeGetQuestionsPsy(ObjectResponse<PsyQuestionResponse.PsyData> psyDataResponse) {
        if (psyDataResponse == null) {
            return;
        }
        if (psyDataResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getQuestionPsy().removeObservers(this);
            viewModel.setQuestionsPsy(null);
            if (psyDataResponse.getData() != null) {
                List<QuestionShow> questionShows = new ArrayList<>();
                for (int i = 0; i < psyDataResponse.getData().getTypeList().size(); i++) {
                    DataUtils.getInstance().typeListPsy.put(psyDataResponse.getData().getTypeList().get(i).getId(),
                            psyDataResponse.getData().getTypeList().get(i).getContent());
                    int stt = 1;
                    List<String> ansers = new ArrayList<>();
                    List<Integer> score = new ArrayList<>();
                    for (PsyQuestionResponse.PsyAnswer psyAnswer :
                            psyDataResponse.getData().getTypeList().get(i).getAnswers()) {
                        ansers.add(psyAnswer.getName());
                        score.add(psyAnswer.getScore());
                    }

                    psyDataResponse.getData().getTypeList().get(i).getAnswers().get(0).getScore();

                    switch (psyDataResponse.getData().getTypeList().get(i).getId()) {
                        case 1:
                            DataUtils.getInstance().scoreLoAu = score;
                            break;
                        case 2:
                            DataUtils.getInstance().scoreTramCam = score;
                            break;
                        case 3:
                            DataUtils.getInstance().scoreStress = score;
                            break;
                        case 4:
                            DataUtils.getInstance().scoreKhoTapTrung = score;
                            break;
                        case 5:
                            DataUtils.getInstance().scoreTangDong = score;
                            break;
                        case 6:
                            DataUtils.getInstance().scoreKKVeGiaoTiepXaHoi = score;
                            break;
                        case 7:
                            DataUtils.getInstance().scoreKHHocTap = score;
                            break;
                        case 8:
                            DataUtils.getInstance().scoreKKTrongDinhHuongNgheNghiep = score;
                            break;
                        case 9:
                            DataUtils.getInstance().scoreKKTrongMoiQuanHeChaMe = score;
                            break;
                        case 10:
                            DataUtils.getInstance().scoreKKTrongMoiQuanHeThayCo = score;
                            break;
                        case 11:
                            DataUtils.getInstance().scoreKKTrongMoiQuanHeBanBe = score;
                            break;
                        case 12:
                            DataUtils.getInstance().scoreHanhViThachThucChongDoi = score;
                            break;
                        case 13:
                            DataUtils.getInstance().scoreRoiLoanHanhViUngXu = score;
                            break;
                        case 14:
                            DataUtils.getInstance().scoreGayHan = score;
                            break;
                    }

                    for (int j = 0; j < psyDataResponse.getData().getTypeList().get(i).getQuestions().size(); j++) {
                        PsyQuestionResponse.PsyQuestion psyQuestion = psyDataResponse.getData().getTypeList().get(i).getQuestions().get(j);
                        questionShows.add(new QuestionShow(
                                stt,
                                psyDataResponse.getData().getTypeList().get(i).getQuestions().get(j).getId(),
                                psyDataResponse.getData().getTypeList().get(i).getId(),
                                psyDataResponse.getData().getTypeList().get(i).getContent(),
                                psyQuestion.getContent(),
                                ansers));
                        stt++;
                    }
                }
                testViewModel.setQuestionShow(questionShows);
                openStepTest.openStepTwo();
            } else {
                PsyLoading.getInstance(getContext()).hidden();
                Snackbar.make(getView(), "Lấy dữ liệu câu hỏi thất bại!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void observeGetQuestionsRiasec(ObjectResponse<RiasecQuestionResponse.RiasecData> riasecDataResponse) {
        if (riasecDataResponse == null) {
            return;
        }
        if (riasecDataResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getQuestionsRiasec().removeObservers(this);
            viewModel.setQuestionsRiasec(null);
            if (riasecDataResponse.getData() != null) {
                List<QuestionShow> questionShows = new ArrayList<>();
                for (int i = 0; i < riasecDataResponse.getData().getQuestions().size(); i++) {
                    int j = i + 1;
                    questionShows.add(new QuestionShow(j,
                            riasecDataResponse.getData().getQuestions().get(i).getId(),
                            0,
                            "",
                            riasecDataResponse.getData().getQuestions().get(i).getContent(), riasecDataResponse.getData().getAnswer()));
                }
                if (riasecDataResponse.getData().getSrore() == null) {
                    PsyLoading.getInstance(getContext()).hidden();
                    Snackbar.make(getView(), "Lỗi cấu trúc API trả về!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                testViewModel.setScoreRiasec(riasecDataResponse.getData().getSrore());
                testViewModel.setQuestionShow(questionShows);
                openStepTest.openStepTwo();
            } else {
                PsyLoading.getInstance(getContext()).hidden();
                Snackbar.make(getView(), "Lấy dữ liệu câu hỏi thất bại!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void observeGetQuestionsNeo(ObjectResponse<NeoQuestionResponse.NeoData> neoDataResponse) {
        if (neoDataResponse == null) {
            return;
        }
        if (neoDataResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getQuestionsNeo().removeObservers(this);
            viewModel.setQuestionsNeo(null);
            List<Boolean> reverseScore = new ArrayList<>();
            if (neoDataResponse.getData() != null) {
                List<QuestionShow> questionShows = new ArrayList<>();
                for (int i = 0; i < neoDataResponse.getData().getQuestions().size(); i++) {
                    int j = i + 1;
                    questionShows.add(new QuestionShow(
                            j,
                            neoDataResponse.getData().getQuestions().get(i).getId(),
                            0,
                            "",
                            neoDataResponse.getData().getQuestions().get(i).getContent(), neoDataResponse.getData().getAnswer()));
                    reverseScore.add(neoDataResponse.getData().getQuestions().get(i).getReverseScore());
                }
                testViewModel.setScoreNeo(neoDataResponse.getData().getSrore());
                testViewModel.setReverseScore(reverseScore);
                testViewModel.setQuestionShow(questionShows);
                openStepTest.openStepTwo();
            } else {
                PsyLoading.getInstance(getContext()).hidden();
                Snackbar.make(getView(), "Lấy dữ liệu câu hỏi thất bại!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initView() {
        binding.tvDetail.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
    }

    @Override
    protected void initData() {
        binding.tvDetail.setText(testViewModel.getDetail());
    }

    @Override
    public Class<TestStepOneViewModel> getModelClass() {
        return TestStepOneViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_step_one;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
                    // kiểm tra đường truyền
                    if (DataUtils.getInstance().getProfile() == null) {
                        Snackbar.make(getView(), "Bạn cần đăng nhập để sử dụng làm bài đánh giá!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (!NetworkUtils.hasConnection(getContext())) {
                        Snackbar.make(getView(), "Lỗi kết nối", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (testViewModel.getType() == Define.Question.TYPE_NEO) {
                        if (DataUtils.getInstance().getProfile().getSex() == null
                                || DataUtils.getInstance().getProfile().getSex().isEmpty()) {
                            ToastUtils.showToastNotification(getContext(), "Bạn cần cập nhật giới tính trước!");
                            return;
                        }
                    }
                    PsyLoading.getInstance(getContext()).show();
                    viewModel.getListQuestionsByType(testViewModel.getType());
                } else {
                    if (DataUtils.getInstance().getCustomer() == null) {
                        Snackbar.make(getView(), "Bạn cần đăng nhập để sử dụng làm bài đánh giá!", Snackbar.LENGTH_SHORT).show();
                    }
                    if (testViewModel.getType() == Define.Question.TYPE_NEO) {
                        if (DataUtils.getInstance().getCustomer().getGender() == -1) {
                            ToastUtils.showToastNotification(getContext(), "Bạn cần cập nhật giới tính trước!");
                            return;
                        }
                    }
                    PsyLoading.getInstance(getContext()).show();
                    openStepTest.openStepTwo();
                }
                break;
        }
    }

    public void setOpenStepTest(OpenStepTest openStepTest) {
        this.openStepTest = openStepTest;
    }
}
