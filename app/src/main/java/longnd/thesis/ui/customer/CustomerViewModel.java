package longnd.thesis.ui.customer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.repository.CustomerRepository;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.ProfileResponse;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.Utils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class CustomerViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;
    private APIService mAPIService;
    private MutableLiveData<ObjectResponse<Long>> isInsertCustomer;
    private MutableLiveData<ObjectResponse<Long>> isExistsCustomer;
    private MutableLiveData<ObjectResponse<Customer>> customerByEmail;
    private MutableLiveData<ObjectResponse<Boolean>> isUpdateCustomer;
    public String pathImage;

    private MutableLiveData<ObjectResponse<Boolean>> isUploadAvatar;

    @Inject
    CustomerViewModel(CustomerRepository customerRepository) {
        this.repository = customerRepository;
        compositeDisposable = new CompositeDisposable();
        isInsertCustomer = new MutableLiveData<>();
        customerByEmail = new MutableLiveData<>();
        isExistsCustomer = new MutableLiveData<>();
        isUpdateCustomer = new MutableLiveData<>();
        isUploadAvatar = new MutableLiveData<>();

        mAPIService = ApiClient.getAPIService();
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
        String dst = Fields.ROOT_FOLDER + "/" + nameFile;
        pathImage = dst;
        File dstFile = new File(dst);
        return Utils.onSaveImageFileToMemory(srcFile, dstFile);
    }

    /**
     * used to upload avatar
     *
     * @param token
     */
    public void updateAvatarToServer(String token) {
        if (pathImage == null) {
            return;
        }
        File file = new File(pathImage);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        Disposable disposable = mAPIService.updateAvatar(token, body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::uploadAvatarResponse, this::uploadAvatarError);

        compositeDisposable.add(disposable);
    }

    private void uploadAvatarError(Throwable throwable) {
        Log.d("TAGs", "Error!");
        isUploadAvatar.setValue(new ObjectResponse<Boolean>().success(false));
    }

    private void uploadAvatarResponse(ProfileResponse profileResponse) {
        Log.d("TAGs", "Success!");
        if (profileResponse != null) {
            DataUtils.getInstance().setProfile(profileResponse);
            isUploadAvatar.setValue(new ObjectResponse<Boolean>().success(true));
        }
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

    public MutableLiveData<ObjectResponse<Boolean>> getIsUploadAvatar() {
        return isUploadAvatar;
    }

    public void setIsUploadAvatar(ObjectResponse<Boolean> value) {
        isUploadAvatar.setValue(value);
    }

}
