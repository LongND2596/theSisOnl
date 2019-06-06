package longnd.thesis.ui.dialog;


import android.app.DatePickerDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import longnd.thesis.R;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.databinding.DialogEditBinding;
import longnd.thesis.network.pojo.login.ProfileResponse;
import longnd.thesis.network.pojo.login.UserResponse;
import longnd.thesis.ui.base.BaseDialog;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Helpers;
import longnd.thesis.utils.NetworkUtils;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.SharedPrefs;
import longnd.thesis.utils.ToastUtils;
import longnd.thesis.utils.Utils;

public class DialogEditCustomer extends BaseDialog<EditCustomerViewModel, DialogEditBinding>
        implements AdapterView.OnItemSelectedListener {
    private ProfileResponse profile;
    private UserResponse user;

    private DatePickerDialog datePickerDialog;
    private OnDoneUpdate onDoneUpdate;
    private String school;
    private int universityId;
    private String specialized;
    private String gender;
    private String birthday;
    private int yyyy;
    private int dd;
    private int mm;
    private String token;

    private ArrayAdapter<String> spin_school;
    private ArrayAdapter<String> spin_specialized;
    private ArrayAdapter<CharSequence> spin_gender;
    private DialogBuilder.NoticeDialog mRetryDialog;

    protected void initView(View view) {
    }

    @Override
    protected Class<EditCustomerViewModel> getModelClass() {
        return EditCustomerViewModel.class;
    }

    @Override
    protected void initOnClick() {
        binding.ivClose.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.tvBirthday.setOnClickListener(this);
        binding.container.setOnClickListener(this);
        binding.spinnerSchool.setOnItemSelectedListener(this);
        binding.spinnerSpecialized.setOnItemSelectedListener(this);
        binding.spinnerGender.setOnItemSelectedListener(this);
}

    @Override
    public void onStart() {
        super.onStart();
        onRetryConnectionOk();
        viewModel.getIsGetDataProfile().observe(getViewLifecycleOwner(), this::observeDataProfile);
        viewModel.getIsGetSpecialities().observe(getViewLifecycleOwner(), this::observeGetSpecialities);
        viewModel.getIsUpdateProfile().observe(getViewLifecycleOwner(), this::observeUpdateProfile);
    }

    private void observeUpdateProfile(ObjectResponse<ProfileResponse> profileResponse) {
        if (profileResponse == null) {
            return;
        }
        if (profileResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            PsyLoading.getInstance(getContext()).hidden();
            viewModel.getIsUpdateProfile().removeObservers(this);
            viewModel.setIsUpdateProfile(null);
            if (profileResponse.getData() != null) {
                DataUtils.getInstance().setProfile(profileResponse.getData());
                dismiss();
                onDoneUpdate.successUpdate();
            } else {
                onDoneUpdate.faildUpdate();
            }
        }

    }

    private void showRetryConnectionDialog() {
        boolean dialogShown = mRetryDialog != null && mRetryDialog.getDialog() != null
                && mRetryDialog.getDialog().isShowing();
        if (!dialogShown) {
            mRetryDialog = DialogBuilder.NoticeDialog.newInstance(
                    getResources().getString(R.string.can_not_connect_to_server),
                    getResources().getString(R.string.retry),
                    false);
            mRetryDialog.setOnClickListener(new DialogBuilder.OnClickListener() {
                @Override
                public void onOkClick(Object object) {
                    mRetryDialog.dismiss();
                    onRetryConnectionOk();
                }

                @Override
                public void onCancelClick() {

                }
            });

            mRetryDialog.setOnDialogBackPress(
                    () -> onRetryConnectionOk()
            );
            Helpers.showDialogFragment(getFragmentManager(), mRetryDialog);
        }
    }

    private void onRetryConnectionOk() {
        if (!NetworkUtils.hasConnection(getContext())) {
            showRetryConnectionDialog();
        }
    }

    private void observeGetSpecialities(ListResponse<String> stringListResponse) {
        if (stringListResponse == null) {
            return;
        }
        if (stringListResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getIsGetSpecialities().removeObservers(this);
            viewModel.setIsGetSpecialities(null);
            showSpecialities(stringListResponse.getData());
        }
    }


    private void showSpecialities(List<String> data) {
        int selectionSpecialiti = 0;
        if (specialized != null && !TextUtils.isEmpty(specialized)) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).equals(specialized)) {
                    selectionSpecialiti = i;
                }
            }
        }
        spin_specialized = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, data);
        spin_specialized.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSpecialized.setAdapter(spin_specialized);
        binding.spinnerSpecialized.setSelection(selectionSpecialiti);
        PsyLoading.getInstance(getContext()).hidden();
    }

    private void observeDataProfile(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        PsyLoading.getInstance(getContext()).hidden();
        if (booleanObjectResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getIsGetDataProfile().removeObservers(this);
            viewModel.setIsGetDataProfile(null);
            if (booleanObjectResponse.getData()) {
                showData();
            } else {
                Snackbar.make(getView(), "Đã xảy ra lỗi, không thể kết nối đc máy chủ!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void initData() {
        token = SharedPrefs.getInstance().getString(Define.SharedPref.KEY_TOKEN, Define.SharedPref.VALUE_DEFAULT);
        if (DataUtils.getInstance().getUniversities() == null) {
            PsyLoading.getInstance(getContext()).show();
            viewModel.getUniversities(getResources().getString(R.string.send_token, token));
        } else {
            showData();
        }
    }

    private void showData() {
        profile = DataUtils.getInstance().getProfile();
        user = DataUtils.getInstance().getUser();
        if (profile == null || user == null) {
            return;
        }

        // full name
        if (profile.getFullname() == null){
            binding.editFullName.setText(user.getName());
        }else {
            binding.editFullName.setText(profile.getFullname());
        }

        // phone
        if (profile.getPhoneNumber() != null) {
            binding.editPhone.setText(profile.getPhoneNumber());
        }

        // school
        List<String> listSchool = new ArrayList<>();
        listSchool.add("Chọn trường...");
        int selectionSchool = 0;
        for (int i = 0; i < DataUtils.getInstance().getUniversities().size(); i++) {
            String schoolName = DataUtils.getInstance().getUniversities().get(i).getName();
            if (profile.getUniversity() != null && profile.getUniversity().equals(schoolName)) {
                selectionSchool = i + 1;
            }
            listSchool.add(schoolName);
        }

        spin_school = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listSchool);
        spin_gender = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        spin_school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSchool.setAdapter(spin_school);
        binding.spinnerSchool.setSelection(selectionSchool);

        binding.spinnerGender.setAdapter(spin_gender);
        if (profile.getSex() != null && profile.getSex().contains("Nữ")) {
            binding.spinnerGender.setSelection(1);
        }

        if (profile.getSpeciality() != null && !profile.getSpeciality().isEmpty()) {
            specialized = profile.getSpeciality();
        }
        // birthday
        if (profile.getBirthday() != null && !profile.getBirthday().isEmpty()) {
            birthday = profile.getBirthday();
            binding.tvBirthday.setText(birthday);
            String[] splits = birthday.split("-");
            yyyy = Integer.parseInt(splits[0]);
            mm = Integer.parseInt(splits[1]);
            dd = Integer.parseInt(splits[2]);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_edit;
    }

    @Override
    public void onClick(View v) {
        avoidDulicateClick(v);
        Utils.hideKeyboardFrom(getContext(), v);
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btnUpdate:
                String fullname = binding.editFullName.getText().toString().trim();
                String phone = binding.editPhone.getText().toString().trim();
                if (school.contains("Chọn trường...")) {
                    ToastUtils.showToastNotification(getContext(), "Chọn thông tin trường");
                    return;
                }
                if (specialized.contains("Chọn ngành...")) {
                    ToastUtils.showToastNotification(getContext(), "Chọn thông tin chuyên ngành");
                    return;
                }
                if (phone.isEmpty()) {
                    ToastUtils.showToastNotification(getContext(), "Thiếu thông tin số điện thoại");
                    return;
                }
                if (birthday == null || birthday.isEmpty()) {
                    ToastUtils.showToastNotification(getContext(), "Thiếu thông tin ngày sinh");
                    return;
                }
                PsyLoading.getInstance(getContext()).show();

                viewModel.updateProfile(getResources().getString(R.string.send_token, token), fullname, universityId, specialized, fullname, gender, phone, birthday);
                break;
            case R.id.tvBirthday:
                PsyLoading.getInstance(getContext()).show();
                openDialogDatePicker();
                break;
            default:
                Utils.hideKeyboardFrom(getContext(), v);
                break;
        }
    }

    private void openDialogDatePicker() {
        if (datePickerDialog == null) {
            if (yyyy == 0) {
                yyyy = 1990;
                mm = 1;
                dd = 1;
            }
            datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                birthday = "";
                birthday = birthday.concat(String.valueOf(year) + "-");
                month++;
                if (month < 10) {
                    birthday = birthday.concat("0" + month + "-");
                } else {
                    birthday = birthday.concat(String.valueOf(month) + "-");
                }
                if (dayOfMonth < 10) {
                    birthday = birthday.concat("0" + dayOfMonth);
                } else {
                    birthday = birthday.concat(String.valueOf(dayOfMonth));
                }
                binding.tvBirthday.setText(birthday);
            }, yyyy, mm - 1, dd);
        }
        datePickerDialog.show();
        PsyLoading.getInstance(getContext()).hidden();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Utils.hideKeyboardFrom(getContext(), view);
        switch (parent.getId()) {
            case R.id.spinnerSchool:
                school = spin_school.getItem(position);

                PsyLoading.getInstance(getContext()).show();
                if (position == 0) {
                    viewModel.getSpecialitiesByUniversityId(-1);
                } else {
                    universityId = DataUtils.getInstance().getUniversities().get(position - 1).getId();
                    viewModel.getSpecialitiesByUniversityId(universityId);
                }
                break;
            case R.id.spinnerSpecialized:
                specialized = spin_specialized.getItem(position);
                break;
            case R.id.spinnerGender:
                gender = spin_gender.getItem(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void setOnDoneUpdate(OnDoneUpdate onDoneUpdate) {
        this.onDoneUpdate = onDoneUpdate;
    }

    public interface OnDoneUpdate {
        void successUpdate();

        void faildUpdate();
    }
}
