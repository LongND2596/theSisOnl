package longnd.thesis.ui.history;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.history.HistoryResponse;
import longnd.thesis.network.result.NeoResultResponse;
import longnd.thesis.network.result.PsyResultResponse;
import longnd.thesis.network.result.RiasecResultResponse;
import longnd.thesis.utils.DataUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private APIService mAPIService;

    private MutableLiveData<ListResponse<HistoryResponse.DataHistory>> dataHistory;

    private MutableLiveData<ObjectResponse<NeoResultResponse.DataNeo>> resultNeo;
    private MutableLiveData<ObjectResponse<RiasecResultResponse.DataRiasec>> resultRiasec;
    private MutableLiveData<ObjectResponse<PsyResultResponse.DataPsy>> resultPsy;

    @Inject
    HistoryViewModel() {
        compositeDisposable = new CompositeDisposable();
        mAPIService = ApiClient.getAPIService();

        dataHistory = new MutableLiveData<>();

        resultNeo = new MutableLiveData<>();
        resultRiasec = new MutableLiveData<>();
        resultPsy = new MutableLiveData<>();
    }

    // region -> Get Historys

    public void getHistory() {
        Disposable disposable = mAPIService.getListHistory(DataUtils.getInstance().getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::getHistoryResponse, this::getHistoryError);
        compositeDisposable.add(disposable);
    }

    private void getHistoryError(Throwable throwable) {
        dataHistory.setValue(new ListResponse<HistoryResponse.DataHistory>().error(throwable));
    }

    private void getHistoryResponse(HistoryResponse historyResponse) {
        if (historyResponse.isSuccess()) {
            dataHistory.setValue(new ListResponse<HistoryResponse.DataHistory>().success(historyResponse.data));
        }
    }

    // endregion

    // region -> Get History Neo

    public void getHistoryNeo(int id) {
        Disposable disposable = mAPIService.getHistoryNeo(DataUtils.getInstance().getToken(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::getNeoResponse, this::getNeoError);
        compositeDisposable.add(disposable);
    }

    private void getNeoResponse(NeoResultResponse neoResultResponse) {
        if (neoResultResponse.isSuccess()) {
            resultNeo.setValue(new ObjectResponse<NeoResultResponse.DataNeo>().success(neoResultResponse.getData()));
        }
    }

    private void getNeoError(Throwable throwable) {
        resultNeo.setValue(new ObjectResponse<NeoResultResponse.DataNeo>().error(throwable));
    }

    // endregion

    // region -> Get History Riasec

    public void getHistoryRiasec(int id) {
        Disposable disposable = mAPIService.getHistoryRiasec(DataUtils.getInstance().getToken(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::getRiasecResponse, this::getRiasecError);
        compositeDisposable.add(disposable);
    }

    private void getRiasecError(Throwable throwable) {
        resultRiasec.setValue(new ObjectResponse<RiasecResultResponse.DataRiasec>().error(throwable));
    }

    private void getRiasecResponse(RiasecResultResponse resultResponse) {
        if (resultResponse.isSuccess()) {
            resultRiasec.setValue(new ObjectResponse<RiasecResultResponse.DataRiasec>().success(resultResponse.data));
        }
    }

    // endregion

    // region -> Get History Psycho

    public void getHistoryPsycho(int id) {
        Disposable disposable = mAPIService.getHistoryPsy(DataUtils.getInstance().getToken(), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::getPsyResponse, this::getPsyError);
        compositeDisposable.add(disposable);
    }

    private void getPsyError(Throwable throwable) {
        resultPsy.setValue(new ObjectResponse<PsyResultResponse.DataPsy>().error(throwable));
    }

    private void getPsyResponse(PsyResultResponse psyResultResponse) {
        if (psyResultResponse.isSuccess()) {
            resultPsy.setValue(new ObjectResponse<PsyResultResponse.DataPsy>().success(psyResultResponse.data));
        }
    }

    // endregion

    public MutableLiveData<ListResponse<HistoryResponse.DataHistory>> getDataHistory() {
        return dataHistory;
    }

    public void setDataHistory(ListResponse<HistoryResponse.DataHistory> value) {
        dataHistory.setValue(value);
    }

    public MutableLiveData<ObjectResponse<NeoResultResponse.DataNeo>> getResultNeo() {
        return resultNeo;
    }

    public void setResultNeo(ObjectResponse<NeoResultResponse.DataNeo> value) {
        resultNeo.setValue(value);
    }

    public MutableLiveData<ObjectResponse<RiasecResultResponse.DataRiasec>> getResultRiasec() {
        return resultRiasec;
    }

    public void setResultRiasec(ObjectResponse<RiasecResultResponse.DataRiasec> value) {
        resultRiasec.setValue(value);
    }

    public MutableLiveData<ObjectResponse<PsyResultResponse.DataPsy>> getResultPsy() {
        return resultPsy;
    }

    public void setResultPsy(ObjectResponse<PsyResultResponse.DataPsy> value) {
        resultPsy.setValue(value);
    }
}
