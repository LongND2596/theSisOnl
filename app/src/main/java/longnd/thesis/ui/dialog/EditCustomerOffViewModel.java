package longnd.thesis.ui.dialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.repository.CustomerRepository;

public class EditCustomerOffViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;
    private MutableLiveData<ObjectResponse<Boolean>> isUpdateCustomer;

    @Inject
    EditCustomerOffViewModel(CustomerRepository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
        isUpdateCustomer = new MutableLiveData<>();
    }

    public void updateCustoemr(Customer customer) {
        compositeDisposable.add(
                repository.updateCustomer(customer)
                        .doOnSubscribe(disposable -> isUpdateCustomer.setValue(new ObjectResponse<Boolean>().loading()))
                        .subscribe(response -> isUpdateCustomer.setValue(new ObjectResponse<Boolean>().success(true))
                                , throwable -> isUpdateCustomer.setValue(new ObjectResponse<Boolean>().error(throwable)))
        );
    }

    public boolean aValidData(String fullname, String school, String specialized, String phone, String gender, String birthday) {
        if (fullname.isEmpty() || school.equals("Chọn trường...") || specialized.equals("Chọn chuyên ngành") || phone.isEmpty() || birthday.isEmpty()) {
            return false;
        }
        return true;
    }

    public int getDay(String birthDay) {
        return Integer.parseInt(birthDay.substring(0, birthDay.indexOf("/")));
    }

    public int getMonth(String birthDay) {
        return Integer.parseInt(birthDay.substring(birthDay.indexOf("/") + 1, birthDay.lastIndexOf("/")));
    }

    public int getYear(String birthDay) {
        return Integer.parseInt(birthDay.substring(birthDay.lastIndexOf("/") + 1));
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsUpdateCustomer() {
        return isUpdateCustomer;
    }

    public void setIsUpdateCustomer(ObjectResponse<Boolean> value) {
        isUpdateCustomer.setValue(value);
    }
}
