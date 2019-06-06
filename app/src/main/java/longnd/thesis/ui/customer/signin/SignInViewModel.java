package longnd.thesis.ui.customer.signin;

import android.util.Patterns;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.repository.CustomerRepository;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.LoginResponse;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.SharedPrefs;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignInViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;
    private APIService mAPIService;

    private MutableLiveData<ObjectResponse<Customer>> customerByEmail;

    private MutableLiveData<ObjectResponse<Boolean>> isLoginSuccess;
    private MutableLiveData<ObjectResponse<String>> connectError;

    @Inject
    SignInViewModel(CustomerRepository repository) {
        compositeDisposable = new CompositeDisposable();
        this.repository = repository;

        customerByEmail = new MutableLiveData<>();

        connectError = new MutableLiveData<>();
        isLoginSuccess = new MutableLiveData<>();
        mAPIService = ApiClient.getAPIService();
    }

    /**
     * used to check email is valid
     *
     * @param email
     * @return
     */
    public boolean isValidDEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void signInCustomer(String email, String pass) {
        compositeDisposable.add(
                repository.signInCustomerByEmail(email, pass)
                        .doOnSubscribe(dispose -> {
                            customerByEmail.setValue(new ObjectResponse<Customer>().loading());
                        })
                        .subscribe(response -> customerByEmail.setValue(new ObjectResponse<Customer>().success(response))
                                , throwable -> customerByEmail.setValue(new ObjectResponse<Customer>().error(throwable)))
        );
    }

    public void loginServer(String email, String pass) {
        Disposable disposable = mAPIService.loginServer(email, pass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);

        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {

    }

    private void handleResponse(LoginResponse loginResponse) {
        isLoginSuccess.setValue(new ObjectResponse<Boolean>().success(loginResponse.isSuccess()));
        if (loginResponse.isSuccess()) {
            SharedPrefs.getInstance().putString(Define.SharedPref.KEY_TOKEN, loginResponse.getData().getToken());
            if (loginResponse.getData().getUser() != null) {
                DataUtils.getInstance().setUser(loginResponse.getData().getUser());
            }
            if (loginResponse.getData().getProfile() != null) {
                DataUtils.getInstance().setProfile(loginResponse.getData().getProfile());
            }
            if (loginResponse.getData().getToken() != null) {
                DataUtils.getInstance().setToken("Bearer " + loginResponse.getData().getToken());
            }
        }
    }

    public MutableLiveData<ObjectResponse<Customer>> getObserveCustomerByEmail() {
        return customerByEmail;
    }

    public void setCustomerByEmail(ObjectResponse<Customer> value) {
        customerByEmail.setValue(value);
    }


    public MutableLiveData<ObjectResponse<Boolean>> getIsLoginSuccess() {
        return isLoginSuccess;
    }

    public void setIsLoginSuccess(ObjectResponse<Boolean> value) {
        isLoginSuccess.setValue(value);
    }
}
