package longnd.thesis.ui.evaluate;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import longnd.thesis.R;
import longnd.thesis.adapter.EvaluatePagerAdapter;
import longnd.thesis.databinding.ActivityEvaluateBinding;
import longnd.thesis.network.result.RiasecResultResponse;
import longnd.thesis.ui.base.BaseActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;

public class EvaluateActivity extends BaseActivity<EvaluateViewModel, ActivityEvaluateBinding> {
    private Intent intent;
    private int[] results;

    @Override
    protected void initView() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EvaluateViewModel.class);
        if (getIntent() == null) {
            return;
        }
        intent = getIntent();
        int type = intent.getIntExtra(Fields.KEY_TYPE, -1);
        if (type == -1) {
            return;
        }

        viewModel.setType(type);
        graphChartsType(type);

        viewModel.setResults(results);
        FragmentManager fm = getSupportFragmentManager();
        EvaluatePagerAdapter pagerAdapter = new EvaluatePagerAdapter(fm);
        binding.viewPager.setAdapter(pagerAdapter);
    }

    private void graphChartsType(int type) {
        switch (type) {
            case Define.Question.TYPE_NEO:
                results = new int[5];
                // type : 0 - A, 1 - C, 2 - O, 3 - N, 4 - E
                results[0] = DataUtils.getInstance().resultNeo.get(0).getScore();
                results[1] = DataUtils.getInstance().resultNeo.get(1).getScore();
                results[2] = DataUtils.getInstance().resultNeo.get(2).getScore();
                results[3] = DataUtils.getInstance().resultNeo.get(3).getScore();
                results[4] = DataUtils.getInstance().resultNeo.get(4).getScore();
                break;
            case Define.Question.TYPE_RIASEC:
                results = new int[6];
                RiasecResultResponse.RiasecResult riasecResult;
                // type: 0 - rule, 1- society, 2 - discover, 3 - reality, 4 - art, 5 - convince
                if (DataUtils.getInstance().resultRiasec.results != null) {
                    riasecResult = DataUtils.getInstance().resultRiasec.results;
                } else {
                    riasecResult = DataUtils.getInstance().resultRiasec.result;
                }
                results[0] = riasecResult.quyTac;
                results[1] = riasecResult.xaHoi;
                results[2] = riasecResult.khamPha;
                results[3] = riasecResult.thucTe;
                results[4] = riasecResult.ngheThuat;
                results[5] = riasecResult.thuyetPhuc;
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                break;
        }
    }

    @Override
    protected void initListenerOnClick() {
    }

    @Override
    protected void initData() {
        PsyLoading.getInstance(this).hidden();
        PsyLoading.getInstance(this).destroyLoadingDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void onClick(View v) {
    }

    public void viewDetail() {
        binding.viewPager.setCurrentItem(1);
    }

    public void viewDownload() {
        String url = "https://danhgiatamly.edu.vn/history-export-pdf/"
                + DataUtils.getInstance().getProfile().getId()
                + DataUtils.getInstance().historyId;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
