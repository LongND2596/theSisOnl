package longnd.thesis.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import longnd.thesis.R;
import longnd.thesis.adapter.HistoryAdapter;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.databinding.FragmentHistoryBinding;
import longnd.thesis.network.pojo.login.history.HistoryResponse;
import longnd.thesis.network.result.NeoResultResponse;
import longnd.thesis.network.result.PsyResultResponse;
import longnd.thesis.network.result.RiasecResultResponse;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.ui.evaluate.EvaluateActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.NetworkUtils;

public class HistoryFragment extends BaseFragment<HistoryViewModel, FragmentHistoryBinding> implements HistoryAdapter.OpenHistoryEvaluate {
    private Handler handler;
    private HistoryAdapter adapter;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void initListenerOnClick() {

    }

    @Override
    protected void initView() {
        if (DataUtils.getInstance().getProfile() != null) {
            binding.tvNotifiNoSignin.setVisibility(View.GONE);
            binding.layoutLoading.setVisibility(View.VISIBLE);
            binding.listHistory.setLayoutManager(new LinearLayoutManager(getContext()));
            if (NetworkUtils.hasConnection(getContext())) {
                viewModel.getHistory();
            } else {
                Snackbar.make(getView(), "Đường truyền kết nối không ổn định", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initObserve() {
        viewModel.getDataHistory().observe(getViewLifecycleOwner(), this::observeGetDataHistory);
        viewModel.getResultNeo().observe(getViewLifecycleOwner(), this::observeGetNeo);
        viewModel.getResultRiasec().observe(getViewLifecycleOwner(), this::observeGetRiasec);
        viewModel.getResultPsy().observe(getViewLifecycleOwner(), this::observeGetPsy);
    }

    private void observeGetPsy(ObjectResponse<PsyResultResponse.DataPsy> dataPsy) {
        if (dataPsy == null) {
            return;
        }
        viewModel.getResultPsy().removeObservers(getViewLifecycleOwner());
        viewModel.setResultPsy(null);
        if (dataPsy.getStatus() == Define.ResponseStatus.SUCCESS) {
            DataUtils.getInstance().resultPsycho = dataPsy.getData().result;
            openEvaluateActivity();
        } else if (dataPsy.getStatus() == Define.ResponseStatus.ERROR) {
            Snackbar.make(getView(), "Lấy dữ liệu không thành công!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void observeGetRiasec(ObjectResponse<RiasecResultResponse.DataRiasec> dataRiasec) {
        if (dataRiasec == null) {
            return;
        }
        viewModel.getResultRiasec().removeObservers(getViewLifecycleOwner());
        viewModel.setResultRiasec(null);
        if (dataRiasec.getStatus() == Define.ResponseStatus.SUCCESS) {
            DataUtils.getInstance().resultRiasec = dataRiasec.getData();
            openEvaluateActivity();
        } else if (dataRiasec.getStatus() == Define.ResponseStatus.ERROR) {
            Snackbar.make(getView(), "Lấy dữ liệu không thành công!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void observeGetNeo(ObjectResponse<NeoResultResponse.DataNeo> dataNeo) {
        if (dataNeo == null) {
            return;
        }
        viewModel.getResultNeo().removeObservers(getViewLifecycleOwner());
        viewModel.setResultNeo(null);
        if (dataNeo.getStatus() == Define.ResponseStatus.SUCCESS) {
            Log.d("TAGs", dataNeo.getData().result.get(0).getType() + dataNeo.getData().result.get(0).getScore());
            DataUtils.getInstance().resultNeo = dataNeo.getData().result;
            openEvaluateActivity();
        } else if (dataNeo.getStatus() == Define.ResponseStatus.ERROR) {
            Snackbar.make(getView(), "Lấy dữ liệu không thành công!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void observeGetDataHistory(ListResponse<HistoryResponse.DataHistory> dataHistory) {
        if (dataHistory == null) {
            return;
        }
        viewModel.getDataHistory().removeObservers(getViewLifecycleOwner());
        viewModel.setDataHistory(null);
        if (dataHistory.getStatus() == Define.ResponseStatus.SUCCESS) {
            binding.tvNotifiNoSignin.setVisibility(View.GONE);
            binding.layoutLoading.setVisibility(View.GONE);
            binding.layoutHistory.setVisibility(View.VISIBLE);
            adapter = new HistoryAdapter(getContext(), dataHistory.getData());
            adapter.setOpenHistoryEvaluate(this);
            binding.listHistory.setAdapter(adapter);
        } else if (dataHistory.getStatus() == Define.ResponseStatus.ERROR) {
            Snackbar.make(getView(), "Xảy ra lỗi trong quá trình lấy dữ liệu!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void runThread() {
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (i < 3) {
                    switch (i % 3) {
                        case 0:
                            handler.sendEmptyMessage(0);
                            break;
                        case 1:
                            handler.sendEmptyMessage(1);
                            break;
                        case 2:
                            handler.sendEmptyMessage(2);
                            break;
                    }
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e("AppLog", "runThread: ", e);
                    }
                }
                handler.sendEmptyMessage(3);
            }
        }.start();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public Class<HistoryViewModel> getModelClass() {
        return HistoryViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onClick(View v) {

    }

    private int type = -1;

    @Override
    public void onOpenEvaluate(int type, int id) {
        this.type = type;
        if (!NetworkUtils.hasConnection(getContext())) {
            Snackbar.make(getView(), "Đường truyền kết nối không ổn định", Snackbar.LENGTH_SHORT).show();
            return;
        }
        switch (type) {
            case Define.Question.TYPE_NEO:
                DataUtils.getInstance().historyId = "/neo/" + id;
                viewModel.getHistoryNeo(id);
                break;
            case Define.Question.TYPE_RIASEC:
                DataUtils.getInstance().historyId = "/riasec/" + id;
                viewModel.getHistoryRiasec(id);
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                DataUtils.getInstance().historyId = "/psychology/" + id;
                viewModel.getHistoryPsycho(id);
                break;
        }
    }

    private void openEvaluateActivity() {
        if (type == -1) {
            return;
        }
        Intent intent = new Intent(getActivity(), EvaluateActivity.class);
        intent.putExtra(Fields.KEY_TYPE, type);
        startActivity(intent);
    }
}
