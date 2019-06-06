package longnd.thesis.ui.test.test_step_one;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.question.NeoQuestionResponse;
import longnd.thesis.network.pojo.login.question.PsyQuestionResponse;
import longnd.thesis.network.pojo.login.question.RiasecQuestionResponse;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestStepOneViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private APIService mAPIService;
    private MutableLiveData<ObjectResponse<NeoQuestionResponse.NeoData>> questionsNeo;
    private MutableLiveData<ObjectResponse<RiasecQuestionResponse.RiasecData>> questionsRiasec;
    private MutableLiveData<ObjectResponse<PsyQuestionResponse.PsyData>> questionsPsy;

    @Inject
    TestStepOneViewModel() {
        compositeDisposable = new CompositeDisposable();
        mAPIService = ApiClient.getAPIService();

        questionsNeo = new MutableLiveData<>();
        questionsRiasec = new MutableLiveData<>();
        questionsPsy = new MutableLiveData<>();
    }

    /**
     * used to get list question by type
     *
     * @param type
     */
    public void getListQuestionsByType(int type) {
        Disposable disposable;
        switch (type) {
            case Define.Question.TYPE_NEO:
                disposable = mAPIService.getListQuestionNeo(DataUtils.getInstance().getToken())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::getQuestionsNeoResponse, this::getQuestionsNeoError);
                break;
            case Define.Question.TYPE_RIASEC:
                disposable = mAPIService.getListQuestionRiasec(DataUtils.getInstance().getToken())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::getQuestionsRiasecResponse, this::getQuestionsRiasecError);
                break;
            default:
                disposable = mAPIService.getListQuestionPsy(DataUtils.getInstance().getToken())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::getQuestionsPsyResponse, this::getQuestionsPsyError);
                break;
        }
        compositeDisposable.add(disposable);
    }

    /// Observer Question Type Riasec
    private void getQuestionsPsyError(Throwable throwable) {
        questionsPsy.setValue(new ObjectResponse<PsyQuestionResponse.PsyData>().success(null));
    }

    private void getQuestionsPsyResponse(PsyQuestionResponse psyQuestionResponse) {
        if (psyQuestionResponse.getData() != null) {
            questionsPsy.setValue(new ObjectResponse<PsyQuestionResponse.PsyData>().success(psyQuestionResponse.getData()));
        }
    }
    /// EndObserver

    /// Observer Question Type Riasec
    private void getQuestionsRiasecError(Throwable throwable) {
        questionsRiasec.setValue(new ObjectResponse<RiasecQuestionResponse.RiasecData>().success(null));
    }

    private void getQuestionsRiasecResponse(RiasecQuestionResponse riasecQuestionResponse) {
        if (riasecQuestionResponse.getData() != null) {
            questionsRiasec.setValue(new ObjectResponse<RiasecQuestionResponse.RiasecData>().success(riasecQuestionResponse.getData()));
        }
    }
    /// EndObserver

    /// Observer Question Type Neo
    private void getQuestionsNeoError(Throwable throwable) {
        questionsNeo.setValue(new ObjectResponse<NeoQuestionResponse.NeoData>().success(null));
    }

    private void getQuestionsNeoResponse(NeoQuestionResponse neoQuestionResponse) {
        if (neoQuestionResponse.getData() != null) {
            questionsNeo.setValue(new ObjectResponse<NeoQuestionResponse.NeoData>().success(neoQuestionResponse.getData()));
        }
    }
    /// EndObserver


    /// Get object
    public MutableLiveData<ObjectResponse<NeoQuestionResponse.NeoData>> getQuestionsNeo() {
        return questionsNeo;
    }

    public void setQuestionsNeo(ObjectResponse<NeoQuestionResponse.NeoData> value) {
        questionsNeo.setValue(value);
    }

    public MutableLiveData<ObjectResponse<RiasecQuestionResponse.RiasecData>> getQuestionsRiasec() {
        return questionsRiasec;
    }

    public void setQuestionsRiasec(ObjectResponse<RiasecQuestionResponse.RiasecData> value) {
        questionsRiasec.setValue(value);
    }

    public MutableLiveData<ObjectResponse<PsyQuestionResponse.PsyData>> getQuestionPsy() {
        return questionsPsy;
    }

    public void setQuestionsPsy(ObjectResponse<PsyQuestionResponse.PsyData> value) {
        questionsPsy.setValue(value);
    }

}
