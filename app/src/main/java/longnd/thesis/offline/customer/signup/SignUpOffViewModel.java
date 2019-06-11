package longnd.thesis.offline.customer.signup;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.repository.CustomerRepository;

public class SignUpOffViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;
    private MutableLiveData<ObjectResponse<Long>> isInsertCustomer;
    private MutableLiveData<ObjectResponse<Long>> isDuplicateEmail;
    private MutableLiveData<ObjectResponse<Customer>> customerByEmail;

    @Inject
    SignUpOffViewModel(CustomerRepository repository) {
        compositeDisposable = new CompositeDisposable();
        this.repository = repository;
        isInsertCustomer = new MutableLiveData<>();
        isDuplicateEmail = new MutableLiveData<>();
        customerByEmail = new MutableLiveData<>();
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

    public void duplicateEmail(Customer customer){
        compositeDisposable.add(
                repository.duplicateEmailCustomer(customer)
                        .doOnSubscribe(dispose -> isDuplicateEmail.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isDuplicateEmail.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isDuplicateEmail.setValue(new ObjectResponse<Long>().error(throwable)))
        );
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

    public MutableLiveData<ObjectResponse<Long>> getIsInsertCustomer() {
        return isInsertCustomer;
    }

    public void setIsInsertCustomer(ObjectResponse<Long> value) {
        isInsertCustomer.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Customer>> getObserveCustomerByEmail() {
        return customerByEmail;
    }

    public void setCustomerByEmail(ObjectResponse<Customer> value) {
        customerByEmail.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Long>> getIsDuplicateEmail() {
        return isDuplicateEmail;
    }

    public void setIsDuplicateEmail(ObjectResponse<Long> value) {
        isDuplicateEmail.setValue(value);
    }
}
