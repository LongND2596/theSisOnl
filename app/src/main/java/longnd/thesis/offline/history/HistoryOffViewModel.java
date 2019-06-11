package longnd.thesis.offline.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Result;
import longnd.thesis.data.model.ResultNeo;
import longnd.thesis.data.model.ResultPsychological;
import longnd.thesis.data.model.ResultRiasec;
import longnd.thesis.data.repository.ResultRespository;

public class HistoryOffViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private ResultRespository resultRespository;
    private MutableLiveData<ListResponse<Result>> results;

    private MutableLiveData<ObjectResponse<ResultNeo>> resultNeo;
    private MutableLiveData<ObjectResponse<ResultRiasec>> resultRiasec;
    private MutableLiveData<ObjectResponse<ResultPsychological>> resultPsycho;

    @Inject
    HistoryOffViewModel(ResultRespository resultRespository) {
        this.resultRespository = resultRespository;
        compositeDisposable = new CompositeDisposable();
        results = new MutableLiveData<>();
        resultNeo = new MutableLiveData<>();
        resultRiasec = new MutableLiveData<>();
        resultPsycho = new MutableLiveData<>();
    }

    /**
     * used to get list tbResult
     *
     * @param idCustomer
     */
    public void getListResult(int idCustomer) {
        compositeDisposable.add(
                resultRespository.getListResultByIdCustomer(idCustomer)
                        .doOnSubscribe(dispone -> results.setValue(new ListResponse<Result>().loading()))
                        .subscribe(response -> results.setValue(new ListResponse<Result>().success(response))
                                , throwable -> results.setValue(new ListResponse<Result>().error(throwable)))
        );
    }

    public void getNeo(int id){
        compositeDisposable.add(
                resultRespository.getResultNeo(id)
                        .doOnSubscribe(dispone -> resultNeo.setValue(new ObjectResponse<ResultNeo>().loading()))
                        .subscribe(response -> resultNeo.setValue(new ObjectResponse<ResultNeo>().success(response))
                                , throwable -> resultNeo.setValue(new ObjectResponse<ResultNeo>().error(throwable)))
        );
    }

    public void getRiasec(int id){
        compositeDisposable.add(
                resultRespository.getResultRiasec(id)
                        .doOnSubscribe(dispone -> resultRiasec.setValue(new ObjectResponse<ResultRiasec>().loading()))
                        .subscribe(response -> resultRiasec.setValue(new ObjectResponse<ResultRiasec>().success(response))
                                , throwable -> resultRiasec.setValue(new ObjectResponse<ResultRiasec>().error(throwable)))
        );
    }

    public void getPsycho(int id) {
        compositeDisposable.add(
                resultRespository.getResultPsycho(id)
                        .doOnSubscribe(dispone -> resultPsycho.setValue(new ObjectResponse<ResultPsychological>().loading()))
                        .subscribe(response -> resultPsycho.setValue(new ObjectResponse<ResultPsychological>().success(response))
                                , throwable -> resultPsycho.setValue(new ObjectResponse<ResultPsychological>().error(throwable)))
        );
    }

    public MutableLiveData<ObjectResponse<ResultNeo>> getResultNeo() {
        return resultNeo;
    }

    public void setResultNeo(ObjectResponse<ResultNeo> value) {
        resultNeo.setValue(value);
    }

    public MutableLiveData<ObjectResponse<ResultRiasec>> getResultRiasec() {
        return resultRiasec;
    }

    public void setResultRiasec(ObjectResponse<ResultRiasec> value) {
        resultRiasec.setValue(value);
    }

    public MutableLiveData<ListResponse<Result>> getResults() {
        return results;
    }

    public void setResults(ListResponse<Result> value) {
        results.setValue(value);
    }

    public MutableLiveData<ObjectResponse<ResultPsychological>> getResultPsycho() {
        return resultPsycho;
    }

    public void setResultPsycho(ObjectResponse<ResultPsychological> value) {
        resultPsycho.setValue(value);
    }
}
