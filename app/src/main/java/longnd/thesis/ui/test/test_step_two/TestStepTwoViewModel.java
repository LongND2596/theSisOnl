package longnd.thesis.ui.test.test_step_two;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.result.NeoResultResponse;
import longnd.thesis.network.result.PsyResultResponse;
import longnd.thesis.network.result.RiasecResultResponse;
import longnd.thesis.utils.DataUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestStepTwoViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;

    private MutableLiveData<ObjectResponse<NeoResultResponse.DataNeo>> submitNeoSuccess;
    private MutableLiveData<ObjectResponse<RiasecResultResponse.DataRiasec>> submitRiasecSuccess;
    private MutableLiveData<ObjectResponse<PsyResultResponse.DataPsy>> submitPsychoSuccess;

    private int[] results;
    public Map<String, String> dapAnNguoiDung;
    private APIService mAPIService;

    public int[] resultsLoAu = new int[20];
    public int[] resultsTramCam = new int[20];
    public int[] resultsStress = new int[20];
    public int[] resultsKhoTapTrung = new int[20];
    public int[] resultsTangDong = new int[20];
    public int[] resultsKKGiaoTiepXaHoi = new int[20];
    public int[] resultsKKHocTap = new int[20];
    public int[] resultsKKDinhHuongNgheNghiep = new int[20];
    public int[] resultsKKQuanHeChaMe = new int[20];
    public int[] resultsKKQuanHeThayCo = new int[20];
    public int[] resultsKKQuanHeBanBe = new int[20];
    public int[] resultsHanhViChongDoi = new int[20];
    public int[] resultsRoiLoanHanhVi = new int[20];
    public int[] resultsGayHan = new int[20];

    @Inject
    TestStepTwoViewModel() {
        compositeDisposable = new CompositeDisposable();
        submitNeoSuccess = new MutableLiveData<>();
        submitRiasecSuccess = new MutableLiveData<>();
        submitPsychoSuccess = new MutableLiveData<>();

        mAPIService = ApiClient.getAPIService();

        dapAnNguoiDung = new HashMap<>();
    }

    public void initResults(int size) {
        results = new int[size];
        for (int i = 0; i < size; i++) {
            results[i] = -1;
        }
    }

    // online
    public void setDataResults(int position, int questionId, int result, double score) {
        dapAnNguoiDung.put(String.valueOf(questionId), String.valueOf(score));
        Log.d("TAGs", "Size : " + dapAnNguoiDung.size());
        results[position] = result;
    }

    // offline
    public void setDataResults(int position, int result) {
        results[position] = result;
    }

    /**
     * used to submit question neo
     */
    public void submitNeo() {
        Disposable disposable = mAPIService.submitNeo(DataUtils.getInstance().getToken(), dapAnNguoiDung)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSubmitNeoSuccess, this::handleSubmitNeoError);

        compositeDisposable.add(disposable);
    }

    private void handleSubmitNeoSuccess(NeoResultResponse neoResultResponse) {
        if (neoResultResponse.isSuccess()) {
            submitNeoSuccess.setValue(new ObjectResponse<NeoResultResponse.DataNeo>().success(neoResultResponse.getData()));
        }
    }

    private void handleSubmitNeoError(Throwable throwable) {
        submitNeoSuccess.setValue(new ObjectResponse<NeoResultResponse.DataNeo>().success(null));
    }
    // end

    /**
     * used to submit question riasec
     */
    public void submitRiasec() {
        Disposable disposable = mAPIService.submitRiasec(DataUtils.getInstance().getToken(), dapAnNguoiDung)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSubmitRiasecSuccess, this::handleSubmitRiasecError);

        compositeDisposable.add(disposable);
    }

    private void handleSubmitRiasecError(Throwable throwable) {
        submitRiasecSuccess.setValue(new ObjectResponse<RiasecResultResponse.DataRiasec>().success(null));
    }

    private void handleSubmitRiasecSuccess(RiasecResultResponse riasecResultResponse) {
        if (riasecResultResponse.isSuccess()) {
            submitRiasecSuccess.setValue(new ObjectResponse<RiasecResultResponse.DataRiasec>().success(riasecResultResponse.data));
        }
    }
    // end

    /**
     * used to submit question riasec
     */
    public void submitPsy() {
        Disposable disposable = mAPIService.submitPsycho(DataUtils.getInstance().getToken(), dapAnNguoiDung)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSubmitPsychoSuccess, this::handleSubmitPsychoError);

        compositeDisposable.add(disposable);
    }

    private void handleSubmitPsychoError(Throwable throwable) {
        submitPsychoSuccess.setValue(new ObjectResponse<PsyResultResponse.DataPsy>().success(null));
    }

    private void handleSubmitPsychoSuccess(PsyResultResponse psyResultResponse) {
        if (psyResultResponse.isSuccess()) {
            submitPsychoSuccess.setValue(new ObjectResponse<PsyResultResponse.DataPsy>().success(psyResultResponse.data));
        }
    }
    // end

    public int getDataResults(int position) {
        return results[position];
    }

    public int[] getResuls() {
        return results;
    }

    // GET SET
    public MutableLiveData<ObjectResponse<NeoResultResponse.DataNeo>> getSubmitNeoSuccess() {
        return submitNeoSuccess;
    }

    public void setSubmitNeoSuccess(ObjectResponse<NeoResultResponse.DataNeo> value) {
        submitNeoSuccess.setValue(value);
    }

    public MutableLiveData<ObjectResponse<RiasecResultResponse.DataRiasec>> getSubmitRiasecSuccess() {
        return submitRiasecSuccess;
    }

    public void setSubmitRiasecSuccess(ObjectResponse<RiasecResultResponse.DataRiasec> value) {
        submitRiasecSuccess.setValue(value);
    }

    public MutableLiveData<ObjectResponse<PsyResultResponse.DataPsy>> getSubmitPsychoSuccess() {
        return submitPsychoSuccess;
    }

    public void setSubmitPsychoSuccess(ObjectResponse<PsyResultResponse.DataPsy> value) {
        submitPsychoSuccess.setValue(value);
    }
}
