package longnd.thesis.offline.customer.signin;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.repository.CustomerRepository;

public class SignInOffViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;

    private MutableLiveData<ObjectResponse<Customer>> customerByEmail;

    @Inject
    SignInOffViewModel(CustomerRepository repository) {
        compositeDisposable = new CompositeDisposable();
        this.repository = repository;

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

    public MutableLiveData<ObjectResponse<Customer>> getObserveCustomerByEmail() {
        return customerByEmail;
    }

    public void setCustomerByEmail(ObjectResponse<Customer> value) {
        customerByEmail.setValue(value);
    }

}
