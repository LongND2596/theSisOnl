package longnd.thesis.offline.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import longnd.thesis.R;
import longnd.thesis.adapter.HistoryOffAdapter;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Result;
import longnd.thesis.data.model.ResultNeo;
import longnd.thesis.data.model.ResultPsychological;
import longnd.thesis.data.model.ResultRiasec;
import longnd.thesis.databinding.FragmentHistoryOffBinding;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.ui.evaluate.EvaluateActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.ToastUtils;
import longnd.thesis.utils.Utils;

public class HistoryOffFragment extends BaseFragment<HistoryOffViewModel, FragmentHistoryOffBinding> implements HistoryOffAdapter.OpenHistoryEvaluate {
    private Handler handler;
    private HistoryOffAdapter adapter;
    private Intent intent;
    private Bundle bundle;
    private static final String KEY_BUNDLE = "EVALUATE";

    @Override
    protected void initListenerOnClick() {

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void initView() {
        if (DataUtils.getInstance().getCustomer() != null) {
            binding.tvNotifiNoSignin.setVisibility(View.GONE);
            binding.layoutLoading.setVisibility(View.VISIBLE);
            binding.tvSubLoading.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
            binding.tvLoading.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
            if (handler == null) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 0:
                                binding.tvLoading.setText("Xin chờ.");
                                break;
                            case 1:
                                binding.tvLoading.setText("Xin chờ..");
                                break;
                            case 2:
                                binding.tvLoading.setText("Xin chờ...");
                                break;
                            case 3:
                                binding.layoutLoading.setVisibility(View.GONE);
                                binding.layoutHistory.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                };
            }
            runThread();

            binding.listHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getResults().observe(getViewLifecycleOwner(), this::observeGetResults);
    }

    @Override
    protected void initObserve() {
        viewModel.getResultNeo().observe(getViewLifecycleOwner(), this::observeGetNeo);
        viewModel.getResultRiasec().observe(getViewLifecycleOwner(), this::observeGetRíaec);
        viewModel.getResultPsycho().observe(getViewLifecycleOwner(), this::observeGetPsycho);
    }

    private void observeGetPsycho(ObjectResponse<ResultPsychological> resultPsycho) {
        if (resultPsycho == null) {
            return;
        }
        switch (resultPsycho.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getResultPsycho().removeObservers(getViewLifecycleOwner());
                viewModel.setResultPsycho(null);
                intent.putExtra(Fields.KEY_TYPE, Define.Question.TYPE_PSY_POCHOLIGICAL);
                bundle.putSerializable(Fields.KEY_VALUE, resultPsycho.getData());
                intent.putExtra(KEY_BUNDLE, bundle);
                startActivity(intent);
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getResultPsycho().removeObservers(getViewLifecycleOwner());
                viewModel.setResultPsycho(null);
                ToastUtils.showToastError(getContext());
                break;
        }
    }

    private void observeGetRíaec(ObjectResponse<ResultRiasec> resultRiasecObjectResponse) {
        if (resultRiasecObjectResponse == null) {
            return;
        }
        switch (resultRiasecObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getResultRiasec().removeObservers(getViewLifecycleOwner());
                viewModel.setResultRiasec(null);
                intent.putExtra(Fields.KEY_TYPE, Define.Question.TYPE_RIASEC);
                bundle.putSerializable(Fields.KEY_VALUE, resultRiasecObjectResponse.getData());
                intent.putExtra(KEY_BUNDLE, bundle);
                startActivity(intent);
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getResultRiasec().removeObservers(getViewLifecycleOwner());
                viewModel.setResultRiasec(null);
                ToastUtils.showToastError(getContext());
                break;
        }
    }

    private void observeGetNeo(ObjectResponse<ResultNeo> resultNeoObjectResponse) {
        if (resultNeoObjectResponse == null) {
            return;
        }
        switch (resultNeoObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setResultNeo(null);
                intent.putExtra(Fields.KEY_TYPE, Define.Question.TYPE_NEO);
                bundle.putSerializable(Fields.KEY_VALUE, resultNeoObjectResponse.getData());
                intent.putExtra(KEY_BUNDLE, bundle);
                startActivity(intent);
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setResultNeo(null);
                ToastUtils.showToastError(getContext());
                break;
        }
    }

    private void observeGetResults(ListResponse<Result> resultListResponse) {
        if (resultListResponse == null) {
            return;
        }
        switch (resultListResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getResults().removeObservers(getViewLifecycleOwner());
                viewModel.setResults(null);
                adapter = new HistoryOffAdapter(getContext(), resultListResponse.getData());
                adapter.setOpenHistoryEvaluate(this);
                binding.listHistory.setAdapter(adapter);
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getResults().removeObservers(getViewLifecycleOwner());
                viewModel.setResults(null);
                break;
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
        if (DataUtils.getInstance().getCustomer() != null) {
            viewModel.getListResult(DataUtils.getInstance().getCustomer().getId());
        }

        intent = new Intent(getActivity(), EvaluateActivity.class);
        bundle = new Bundle();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public Class<HistoryOffViewModel> getModelClass() {
        return HistoryOffViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history_off;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onOpenEvaluate(int type, int id) {
        switch (type) {
            case Define.Question.TYPE_NEO:
                viewModel.getNeo(id);
                break;
            case Define.Question.TYPE_RIASEC:
                viewModel.getRiasec(id);
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                viewModel.getPsycho(id);
                break;
        }
    }
}
