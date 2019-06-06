package longnd.thesis.ui.home;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.DataQuestionType;
import longnd.thesis.network.pojo.login.question.QuestionTypeResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private APIService mAPIService;
    private MutableLiveData<ObjectResponse<DataQuestionType>> questionsType;

    @Inject
    HomeViewModel() {
        compositeDisposable = new CompositeDisposable();
        mAPIService = ApiClient.getAPIService();

        questionsType = new MutableLiveData<>();
    }

    public void getListQuestionType() {
        Disposable disposable = mAPIService.getQuestionsType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);

        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {

    }

    private void handleResponse(QuestionTypeResponse response) {
        questionsType.setValue(new ObjectResponse<DataQuestionType>().success(response.getData()));
    }

    public MutableLiveData<ObjectResponse<DataQuestionType>> getObserveQuestionsType() {
        return questionsType;
    }

    public void setObserveQuestionsType(ObjectResponse<DataQuestionType> value) {
        questionsType.setValue(value);
    }
}
