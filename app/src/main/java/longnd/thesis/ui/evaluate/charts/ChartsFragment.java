package longnd.thesis.ui.evaluate.charts;

import android.os.Handler;
import android.view.View;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Polar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.PolarSeriesType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.ScaleTypes;
import com.anychart.scales.Linear;

import java.util.ArrayList;
import java.util.List;

import longnd.thesis.R;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.model.ResultPsychological;
import longnd.thesis.databinding.FragmentChartsBinding;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.ui.custom.GraphNeoView;
import longnd.thesis.ui.custom.GraphRiasecView;
import longnd.thesis.ui.evaluate.EvaluateActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.EvaluateUtils;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.Utils;

public class ChartsFragment extends BaseFragment<ChartsViewModel, FragmentChartsBinding> {

    private GraphNeoView neoView;
    private GraphRiasecView riasecView;
    private Customer customer;

    @Override
    protected void initListenerOnClick() {
        binding.btnBack.setOnClickListener(this);
        binding.btnDown.setOnClickListener(this);
        binding.btnViewAll.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        binding.mContainer.removeAllViews();
        binding.tvTitle.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
        neoView = new GraphNeoView(getContext());
        riasecView = new GraphRiasecView(getContext());
    }

    @Override
    protected void initData() {
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_OFF)) {
            customer = DataUtils.getInstance().getCustomer();
            if (customer == null) {
                return;
            }
        }
        switch (evaluateViewModel.getType()) {
            case Define.Question.TYPE_NEO:
                binding.layoutRiasec.setVisibility(View.GONE);
                showChartNeo();
                break;

            case Define.Question.TYPE_RIASEC:
                binding.layoutNeo.setVisibility(View.GONE);
                showChartRiasec();
                break;

            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                binding.mContainer.setVisibility(View.GONE);
                binding.layoutNeo.setVisibility(View.GONE);
                binding.layoutRiasec.setVisibility(View.GONE);
                binding.chartPsy.setVisibility(View.VISIBLE);
                showChartPsycho();
                break;
        }

    }

    /**
     * Chart Psycho
     */
    private void showChartPsycho() {
        binding.mContainer.setVisibility(View.GONE);
        binding.layoutNeo.setVisibility(View.GONE);
        binding.layoutRiasec.setVisibility(View.GONE);
        binding.chartPsy.setVisibility(View.VISIBLE);
        AnyChartView anyChartView = binding.chartPsy;
        Polar polar = AnyChart.polar();

        List<DataEntry> data = new ArrayList<>();
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            for (int i = 0; i < DataUtils.getInstance().resultPsycho.size(); i++) {
                String name;
                int score;
                if (DataUtils.getInstance().typeListPsy.isEmpty()) {
                    name = DataUtils.getInstance().resultPsycho.get(i).trouble;
                    score = DataUtils.getInstance().resultPsycho.get(i).score;
                } else {
                    name = DataUtils.getInstance().typeListPsy.get(
                            DataUtils.getInstance().resultPsycho.get(i).typeId);
                    score = DataUtils.getInstance().resultPsycho.get(i).score;
                }
                data.add(new ValueDataEntry(name, score));
            }
        } else {
            Handler handler = new Handler();
            PsyLoading.getInstance(getContext()).show();
            handler.postDelayed(() -> PsyLoading.getInstance(getContext()).hidden(), 2000);
            ResultPsychological resultPsychological = DataUtils.getInstance().resultPsychological;
            data.add(new ValueDataEntry("Lo âu", resultPsychological.getLoAu()));
            data.add(new ValueDataEntry("Trầm cảm", resultPsychological.getTramCam()));
            data.add(new ValueDataEntry("Stress", resultPsychological.getStress()));
            data.add(new ValueDataEntry("Khó tập trung", resultPsychological.getKhoTapTrung()));
            data.add(new ValueDataEntry("Tăng động", resultPsychological.getTangDong()));
            data.add(new ValueDataEntry("KK giao tiếp xã hội", resultPsychological.getKkGiaoTiepXaHoi()));
            data.add(new ValueDataEntry("KK học tập", resultPsychological.getKkHocTap()));
            data.add(new ValueDataEntry("KK trong định hướng nghề nghiệp", resultPsychological.getKkDinhHuongNgheNghiep()));
            data.add(new ValueDataEntry("KK trong mối quan hệ với cha mẹ", resultPsychological.getKkQuanHeChaMe()));
            data.add(new ValueDataEntry("KK trong mối quan hệ với thầy cô", resultPsychological.getKkQuanHeThayCo()));
            data.add(new ValueDataEntry("KK trong mối quan hệ với bạn bè", resultPsychological.getKkQuanHeBanBe()));
            data.add(new ValueDataEntry("Hành vi thách thức - chống đối", resultPsychological.getHanhViChongDoi()));
            data.add(new ValueDataEntry("Rối loạn hành vi ứng xử", resultPsychological.getRoiLoanHanhVi()));
            data.add(new ValueDataEntry("Gây hấn", resultPsychological.getGayHan()));
        }

        Set set = Set.instantiate();
        set.data(data);
        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");

        polar.column(series1Data);

        polar.sortPointsByX(true)
                .defaultSeriesType(PolarSeriesType.COLUMN)
                .yAxis(true)
                .xScale(ScaleTypes.ORDINAL);

        polar.title().margin().bottom(20d);

        ((Linear) polar.yScale(Linear.class)).stackMode(ScaleStackMode.VALUE);
        anyChartView.setChart(polar);
    }

    private void showChartRiasec() {
        riasecView.setData(evaluateViewModel.getMin(), evaluateViewModel.getSpaceRiasec(), evaluateViewModel.getResults());
        binding.mContainer.addView(riasecView);

        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            if (DataUtils.getInstance().resultRiasec.highestFieldExplain == null) {
                return;
            }
            binding.tvResultRiasec.setText(getContext().getResources().getString(R.string.evaluate_riasec,
                    DataUtils.getInstance().getProfile().getFullname().toUpperCase(),
                    DataUtils.getInstance().resultRiasec.highestFieldExplain.type));
        } else {
            binding.tvResultRiasec.setText(getContext().getResources().getString(R.string.evaluate_riasec,
                    DataUtils.getInstance().getCustomer().getLastName().toUpperCase(),
                    EvaluateUtils.resultRiasecTitle(getContext(), evaluateViewModel.getMaxInPosition()).toUpperCase()));
        }
    }

    private void showChartNeo() {
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            neoView.setData(evaluateViewModel.getMin(), evaluateViewModel.getSpaceNeo(), evaluateViewModel.getResults());
            binding.mContainer.addView(neoView);
            int[] results = evaluateViewModel.getResults();
            binding.tvResult01.setText(String.valueOf(results[0]));
            binding.tvResult02.setText(String.valueOf(results[1]));
            binding.tvResult03.setText(String.valueOf(results[2]));
            binding.tvResult04.setText(String.valueOf(results[3]));
            binding.tvResult05.setText(String.valueOf(results[4]));

            binding.tvResult11.setText(DataUtils.getInstance().resultNeo.get(0).getLevel());
            binding.tvResult22.setText(DataUtils.getInstance().resultNeo.get(1).getLevel());
            binding.tvResult33.setText(DataUtils.getInstance().resultNeo.get(2).getLevel());
            binding.tvResult44.setText(DataUtils.getInstance().resultNeo.get(3).getLevel());
            binding.tvResult55.setText(DataUtils.getInstance().resultNeo.get(4).getLevel());
        } else {
            neoView.setData(evaluateViewModel.getMin(), evaluateViewModel.getSpaceNeo(), evaluateViewModel.getResults());
            binding.mContainer.addView(neoView);
            int[] results = evaluateViewModel.getResults();
            binding.tvResult01.setText(String.valueOf(results[0]));
            binding.tvResult02.setText(String.valueOf(results[1]));
            binding.tvResult03.setText(String.valueOf(results[2]));
            binding.tvResult04.setText(String.valueOf(results[3]));
            binding.tvResult05.setText(String.valueOf(results[4]));

            int[] nResults = viewModel.getResultsnNeo(customer.getGender(), results);
            binding.tvResult11.setText(EvaluateUtils.wordResults(nResults[0]));
            binding.tvResult22.setText(EvaluateUtils.wordResults(nResults[1]));
            binding.tvResult33.setText(EvaluateUtils.wordResults(nResults[2]));
            binding.tvResult44.setText(EvaluateUtils.wordResults(nResults[3]));
            binding.tvResult55.setText(EvaluateUtils.wordResults(nResults[4]));
        }
    }

    @Override
    public Class<ChartsViewModel> getModelClass() {
        return ChartsViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_charts;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnDown:
                ((EvaluateActivity) getActivity()).viewDownload();
                break;
            case R.id.btnViewAll:
                ((EvaluateActivity) getActivity()).viewDetail();
                break;
        }
    }
}
