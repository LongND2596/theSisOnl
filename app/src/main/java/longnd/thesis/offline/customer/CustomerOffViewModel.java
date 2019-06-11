package longnd.thesis.offline.customer;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.repository.CustomerRepository;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.Utils;

public class CustomerOffViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;

    private MutableLiveData<ObjectResponse<Long>> isInsertCustomer;
    private MutableLiveData<ObjectResponse<Long>> isExistsCustomer;
    private MutableLiveData<ObjectResponse<Customer>> customerByEmail;
    private MutableLiveData<ObjectResponse<Boolean>> isUpdateCustomer;
    public String pathImage;

    @Inject
    CustomerOffViewModel(CustomerRepository customerRepository) {
        this.repository = customerRepository;
        compositeDisposable = new CompositeDisposable();
        isInsertCustomer = new MutableLiveData<>();
        customerByEmail = new MutableLiveData<>();
        isExistsCustomer = new MutableLiveData<>();
        isUpdateCustomer = new MutableLiveData<>();
    }

    /**
     * used to save an image
     *
     * @param context
     */
    public boolean saveImageToMemory(Context context, Uri data) {
        String path = Utils.getPathFromUri(context, data);
        File srcFile = new File(path);
        String nameFile = "IMG_" + System.currentTimeMillis() + Fields.FORMAT_IMAGE;
        pathImage = nameFile;
        String dst = Fields.ROOT_FOLDER + "/" + nameFile;
        File dstFile = new File(dst);
        return Utils.onSaveImageFileToMemory(srcFile, dstFile);
    }

    public void insertCustomer(Customer customer) {
        compositeDisposable.add(
                repository.insertCustomerReturnId(customer)
                        .doOnSubscribe(dispose -> isInsertCustomer.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isInsertCustomer.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isInsertCustomer.setValue(new ObjectResponse<Long>().error(throwable)))
        );
    }

    public void updateCustoemr(Customer customer) {
        compositeDisposable.add(
                repository.updateCustomer(customer)
                        .doOnSubscribe(disposable -> isUpdateCustomer.setValue(new ObjectResponse<Boolean>().loading()))
                        .subscribe(response -> isUpdateCustomer.setValue(new ObjectResponse<Boolean>().success(true))
                                , throwable -> isUpdateCustomer.setValue(new ObjectResponse<Boolean>().error(throwable)))
        );
    }

    public boolean isValidData(String fullName, String email, String pass, String cofirmPass) {
        if (fullName.isEmpty() || email.isEmpty() || pass.isEmpty() || cofirmPass.isEmpty()) {
            return false;
        }
        if (!pass.equals(cofirmPass)) {
            return false;
        }
        return true;
    }

    public void existsCustomerByEmail(String email, String pass) {
        compositeDisposable.add(
                repository.existsCustomerByEmail(email, pass)
                        .doOnSubscribe(dispose -> {
                            isExistsCustomer.setValue(new ObjectResponse<Long>().loading());
                        })
                        .subscribe(response -> isExistsCustomer.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isExistsCustomer.setValue(new ObjectResponse<Long>().error(throwable)))
        );
    }

    public void getCustomerByEmail(String email, String pass) {
        compositeDisposable.add(
                repository.getCustomerByEmail(email, pass)
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

    public void setIsExistsCustomer(ObjectResponse<Long> value) {
        isExistsCustomer.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsUpdateCustomer() {
        return isUpdateCustomer;
    }

    public void setIsUpdateCustomer(ObjectResponse<Boolean> value) {
        isUpdateCustomer.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Long>> getIsExistsCustomer() {
        return isExistsCustomer;
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
}
