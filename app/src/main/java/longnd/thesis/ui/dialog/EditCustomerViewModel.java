package longnd.thesis.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.LoginResponse;
import longnd.thesis.network.pojo.login.ProfileResponse;
import longnd.thesis.network.pojo.login.SpecialitieResponse;
import longnd.thesis.utils.DataUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditCustomerViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private APIService mAPIService;
    private MutableLiveData<ObjectResponse<Boolean>> isGetDataProfile;
    private MutableLiveData<ListResponse<String>> isGetSpecialities;

    private MutableLiveData<ObjectResponse<ProfileResponse>> isUpdateProfile;

    @Inject
    EditCustomerViewModel() {
        compositeDisposable = new CompositeDisposable();
        isGetDataProfile = new MutableLiveData<>();
        isUpdateProfile = new MutableLiveData<>();
        isGetSpecialities = new MutableLiveData<>();

        mAPIService = ApiClient.getAPIService();
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

    // lấy thông tin các trường đại học có trong CSDL
    public void getUniversities(String token) {
        Disposable disposable = mAPIService.getProfileByToken(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);

        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {
        isGetDataProfile.setValue(new ObjectResponse<Boolean>().success(false));
    }

    private void handleResponse(LoginResponse loginResponse) {
        DataUtils.getInstance().setUniversities(loginResponse.getData().getUniversities());
        isGetDataProfile.setValue(new ObjectResponse<Boolean>().success(true));
    }

    // lấy thông tin chuyên ngành theo id trường đại học
    public void getSpecialitiesByUniversityId(int universityId) {
        if (universityId == -1) {
            List<String> list = new ArrayList<>();
            list.add("Chọn ngành...");
            isGetSpecialities.setValue(new ListResponse<String>().success(list));
            return;
        }
        Disposable disposable = mAPIService.getSpecialitiesByUniversityId(universityId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::specialitieResponse, this::specialitieError);

        compositeDisposable.add(disposable);
    }

    private void specialitieError(Throwable throwable) {

    }

    private void specialitieResponse(SpecialitieResponse specialitieResponse) {
        if (specialitieResponse.isSuccess()) {
            isGetSpecialities.setValue(new ListResponse<String>().success(specialitieResponse.getData().getSpecialities()));
        }
    }

    // cập nhật thông tin người dùng
    public void updateProfile(String token, String username, int universityId, String speciality, String fullname, String sex, String phoneNumber, String birthday) {
        Disposable disposable = mAPIService.updateProfileByToken(token, username, universityId, speciality, fullname, sex, phoneNumber, birthday)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::updateProfileResponse, this::updateProfileError);

        compositeDisposable.add(disposable);
    }

    private void updateProfileError(Throwable throwable) {
        //TODO
    }

    private void updateProfileResponse(ProfileResponse profileResponse) {
        isUpdateProfile.setValue(new ObjectResponse<ProfileResponse>().success(profileResponse));
    }

    public MutableLiveData<ObjectResponse<ProfileResponse>> getIsUpdateProfile() {
        return isUpdateProfile;
    }

    public void setIsUpdateProfile(ObjectResponse<ProfileResponse> value) {
        isUpdateProfile.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsGetDataProfile() {
        return isGetDataProfile;
    }

    public void setIsGetDataProfile(ObjectResponse<Boolean> value) {
        isGetDataProfile.setValue(value);
    }

    public MutableLiveData<ListResponse<String>> getIsGetSpecialities() {
        return isGetSpecialities;
    }

    public void setIsGetSpecialities(ListResponse<String> value) {
        isGetSpecialities.setValue(value);
    }
}
