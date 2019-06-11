package longnd.thesis.ui.customer.signup;

import android.util.Log;
import android.util.Patterns;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.repository.CustomerRepository;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.DataError;
import longnd.thesis.network.pojo.login.DataSuccess;
import longnd.thesis.network.pojo.login.LoginResponse;
import longnd.thesis.utils.DataUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignUpViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;
    private MutableLiveData<ObjectResponse<Long>> isInsertCustomer;
    private MutableLiveData<ObjectResponse<Customer>> customerByEmail;
    private APIService mAPIService;

    private MutableLiveData<ObjectResponse<DataError>> isRegisterFail;
    private MutableLiveData<ObjectResponse<DataSuccess>> isRegisterSuccess;

    @Inject
    SignUpViewModel(CustomerRepository repository) {
        compositeDisposable = new CompositeDisposable();
        this.repository = repository;
        isInsertCustomer = new MutableLiveData<>();
        isDuplicateEmail = new MutableLiveData<>();
        customerByEmail = new MutableLiveData<>();

        isRegisterFail = new MutableLiveData<>();
        isRegisterSuccess = new MutableLiveData<>();
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

    public void insertCustomer(Customer customer) {
        compositeDisposable.add(
                repository.insertCustomerReturnId(customer)
                        .doOnSubscribe(dispose -> isInsertCustomer.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isInsertCustomer.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isInsertCustomer.setValue(new ObjectResponse<Long>().error(throwable)))
        );
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

    /**
     * used to login user
     *
     * @param fullname
     * @param email
     * @param pass
     */
    public void regiterServer(String fullname, String email, String pass) {
        Disposable disposable = mAPIService.registerServer(email, fullname, pass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);

        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {
        Log.d("TAGs", "error");
    }

    private void handleResponse(LoginResponse loginResponse) {
        if (loginResponse.isSuccess()) {
            if (loginResponse.getData().getToken() != null) {
                DataUtils.getInstance().setToken("Bearer " + loginResponse.getData().getToken());
            }
            isRegisterSuccess.setValue(new ObjectResponse<DataSuccess>().success(loginResponse.getData()));
        } else {
            isRegisterFail.setValue(new ObjectResponse<DataError>().success(loginResponse.getErrors()));
        }
    }

    public MutableLiveData<ObjectResponse<Long>> getIsInsertCustomer() {
        return isInsertCustomer;
    }

    public void setIsInsertCustomer(ObjectResponse<Long> value) {
        isInsertCustomer.setValue(value);
    }

    public MutableLiveData<ObjectResponse<DataError>> getIsRegisterFail() {
        return isRegisterFail;
    }

    public void setIsRegisterFail(ObjectResponse<DataError> value) {
        isRegisterFail.setValue(value);
    }

    public MutableLiveData<ObjectResponse<DataSuccess>> getIsRegisterSuccess() {
        return isRegisterSuccess;
    }

    public void setIsRegisterSuccess(ObjectResponse<DataSuccess> value) {
        isRegisterSuccess.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Customer>> getObserveCustomerByEmail() {
        return customerByEmail;
    }

    public void setCustomerByEmail(ObjectResponse<Customer> value) {
        customerByEmail.setValue(value);
    }

    // region -> offline

    private MutableLiveData<ObjectResponse<Long>> isDuplicateEmail;

    /**
     * Check for duplicate emails
     *
     * @param customer
     */
    public void duplicateEmail(Customer customer) {
        compositeDisposable.add(
                repository.duplicateEmailCustomer(customer)
                        .doOnSubscribe(dispose -> isDuplicateEmail.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isDuplicateEmail.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isDuplicateEmail.setValue(new ObjectResponse<Long>().error(throwable)))
        );
    }

    public MutableLiveData<ObjectResponse<Long>> getIsDuplicateEmail() {
        return isDuplicateEmail;
    }

    public void setIsDuplicateEmail(ObjectResponse<Long> value) {
        isDuplicateEmail.setValue(value);
    }

    // endregion

}
