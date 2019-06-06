package longnd.thesis.ui.splash;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.LoginResponse;
import longnd.thesis.utils.DataUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends ViewModel {
    private static final String TAG = "SplashViewModel";
    private CompositeDisposable compositeDisposable;
    private APIService mAPIService;


    private MutableLiveData<ObjectResponse<Boolean>> isGetDataProfileSuccess;


    @Inject
    SplashViewModel() {
        compositeDisposable = new CompositeDisposable();

        isGetDataProfileSuccess = new MutableLiveData<>();
        mAPIService = ApiClient.getAPIService();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void getProfileByToken(String token) {
        Disposable disposable = mAPIService.getProfileByToken(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);

        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {
        isGetDataProfileSuccess.setValue(new ObjectResponse<Boolean>().success(false));
    }

    private void handleResponse(LoginResponse loginResponse) {
        DataUtils.getInstance().setUser(loginResponse.getData().getUser());
        DataUtils.getInstance().setProfile(loginResponse.getData().getProfile());
//        DataUtils.getInstance().setUniversities(loginResponse.getData().getUniversities());
        isGetDataProfileSuccess.setValue(new ObjectResponse<Boolean>().success(true));
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsGetDataProfileSuccess() {
        return isGetDataProfileSuccess;
    }

    public void setIsGetDataProfileSuccess(ObjectResponse<Boolean> value) {
        isGetDataProfileSuccess.setValue(value);
    }
}
