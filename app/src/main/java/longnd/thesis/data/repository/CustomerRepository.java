package longnd.thesis.data.repository;

import longnd.thesis.data.AbstractAppDatabase;
import longnd.thesis.data.model.Customer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;

public class CustomerRepository {
    private AbstractAppDatabase abstractAppDatabase;

    @Inject
    public CustomerRepository(AbstractAppDatabase abstractAppDatabase) {
        this.abstractAppDatabase = abstractAppDatabase;
    }

    public Single<Customer> getCustomerByEmail(String email, String pass) {
        return abstractAppDatabase.customerDao().getCustomerByEmail(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> existsCustomerByEmail(String email, String pass) {
        return abstractAppDatabase.customerDao().existsCustomerByEmail(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> getCount(){
        return abstractAppDatabase.customerDao().getCountCustomer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> duplicateEmailCustomer(Customer customer){
        return abstractAppDatabase.customerDao().existsEmailCustomer(customer.getEmail())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> insertCustomerReturnId(Customer customer){
        return abstractAppDatabase.customerDao().insertCustomerReturnId(customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Integer> updateCustomer(Customer customer){
        return abstractAppDatabase.customerDao().updateCustomer(customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Customer> signInCustomerByEmail(String email, String pass) {
        return abstractAppDatabase.customerDao().signInCustomer(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
