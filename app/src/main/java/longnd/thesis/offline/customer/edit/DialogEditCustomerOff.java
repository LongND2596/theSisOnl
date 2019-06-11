package longnd.thesis.offline.customer.edit;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import longnd.thesis.R;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.databinding.DialogEditBinding;
import longnd.thesis.ui.base.BaseDialog;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.ToastUtils;
import longnd.thesis.utils.Utils;

public class DialogEditCustomerOff extends BaseDialog<EditCustomerOffViewModel, DialogEditBinding>
        implements AdapterView.OnItemSelectedListener  {
    private Customer customer;
    private DatePickerDialog datePickerDialog;
    private OnDoneUpdateOff onDoneUpdate;
    private String school;
    private String specialized;
    private String gender;
    private String birthday;
    private int yyyy;
    private int dd;
    private int mm;

    private ArrayAdapter<CharSequence> spin_school;
    private ArrayAdapter<CharSequence> spin_specialized_0;
    private ArrayAdapter<CharSequence> spin_specialized_1;
    private ArrayAdapter<CharSequence> spin_specialized_2;
    private ArrayAdapter<CharSequence> spin_gender;

    protected void initView(View view) {
    }

    @Override
    protected Class<EditCustomerOffViewModel> getModelClass() {
        return EditCustomerOffViewModel.class;
    }

    @Override
    protected void initOnClick() {
        binding.ivClose.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
        binding.tvBirthday.setOnClickListener(this);

        binding.spinnerSchool.setOnItemSelectedListener(this);
        binding.spinnerSpecialized.setOnItemSelectedListener(this);
        binding.spinnerGender.setOnItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        spin_school = ArrayAdapter.createFromResource(getContext(),
                R.array.school_array, android.R.layout.simple_spinner_item);
        spin_specialized_0 = ArrayAdapter.createFromResource(getContext(),
                R.array.specialized_array_0, android.R.layout.simple_spinner_item);
        spin_specialized_1 = ArrayAdapter.createFromResource(getContext(),
                R.array.specialized_array_1, android.R.layout.simple_spinner_item);
        spin_specialized_2 = ArrayAdapter.createFromResource(getContext(),
                R.array.specialized_array_2, android.R.layout.simple_spinner_item);
        spin_gender = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        spin_school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_specialized_0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_specialized_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_specialized_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerSchool.setAdapter(spin_school);
        binding.spinnerGender.setAdapter(spin_gender);

        customer = DataUtils.getInstance().getCustomer();
        if (customer == null) {
            return;
        }
        if (customer.getLastName() != null) {
            binding.editFullName.setText(customer.getLastName());
            binding.editFullName.setSelection(customer.getLastName().length());
        }
        String sc = customer.getSchool();
        String sp = customer.getSpecialized();

        if (customer.getSchool() != null) {
            if (sc.contains("Bách Khoa")) {
                binding.spinnerSchool.setSelection(1);
                binding.spinnerSpecialized.setAdapter(spin_specialized_1);
                if (sp.contains("Công nghệ")) {
                    binding.spinnerSpecialized.setSelection(0);
                } else {
                    binding.spinnerSpecialized.setSelection(1);
                }
            } else {
                binding.spinnerSchool.setSelection(2);
                binding.spinnerSpecialized.setAdapter(spin_specialized_2);
            }
        } else {
            binding.spinnerSpecialized.setAdapter(spin_specialized_0);
        }
        if (customer.getPhone() != null) {
            binding.editPhone.setText(customer.getPhone());
        }
        if (customer.getGender() == 0) {
            binding.spinnerGender.setSelection(1);
        }
        if (customer.getBirthdate() != null) {
            birthday = customer.getBirthdate();
            binding.tvBirthday.setText(birthday);
            yyyy = viewModel.getYear(birthday);
            dd = viewModel.getDay(birthday);
            mm = viewModel.getMonth(birthday);
        }
        viewModel.getIsUpdateCustomer().observe(getViewLifecycleOwner(), this::observeUpdateCustomer);
    }

    private void observeUpdateCustomer(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        switch (booleanObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:

                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getIsUpdateCustomer().removeObservers(getViewLifecycleOwner());
                viewModel.setIsUpdateCustomer(null);
                PsyLoading.getInstance(getContext()).hidden();
                DataUtils.getInstance().setCustomer(customer);
                onDoneUpdate.successUpdate();
                dismiss();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getIsUpdateCustomer().removeObservers(getViewLifecycleOwner());
                viewModel.setIsUpdateCustomer(null);
                PsyLoading.getInstance(getContext()).hidden();
                onDoneUpdate.faildUpdate();
                break;
            default:
                break;
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
                if (specialized.contains("Chọn chuyên ngành")) {
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
                if (viewModel.aValidData(fullname, school, specialized, phone, gender, birthday)) {
                    customer.setLastName(fullname);
                    customer.setSchool(school);
                    customer.setSpecialized(specialized);
                    customer.setPhone(phone);
                    if (gender.equals("Nam")) {
                        customer.setGender(1);
                    } else {
                        customer.setGender(0);
                    }
                    customer.setBirthdate(birthday);
                }
                viewModel.updateCustoemr(customer);
                break;
            case R.id.tvBirthday:
                PsyLoading.getInstance(getContext()).show();
                openDialogDatePicker();
                break;
        }
    }

    private void openDialogDatePicker() {
        if (datePickerDialog == null) {
            if (yyyy == 0) {
                yyyy = 1990;
                mm = 0;
                dd = 1;
            }
            datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                birthday = "";
                if (dayOfMonth < 10) {
                    birthday = birthday.concat("0" + dayOfMonth + "/");
                } else {
                    birthday = birthday.concat(String.valueOf(dayOfMonth + "/"));
                }
                month++;
                if (month < 10) {
                    birthday = birthday.concat("0" + month + "/");
                } else {
                    birthday = birthday.concat(String.valueOf(month) + "/");
                }
                birthday = birthday.concat(String.valueOf(year));
                binding.tvBirthday.setText(birthday);
            }, yyyy, mm, dd);
        }
        datePickerDialog.show();
        PsyLoading.getInstance(getContext()).hidden();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerSchool:
                school = spin_school.getItem(position).toString();
                if (position == 0) {
                    binding.spinnerSpecialized.setAdapter(spin_specialized_0);
                } else if (position == 1) {
                    binding.spinnerSpecialized.setAdapter(spin_specialized_1);
                } else {
                    binding.spinnerSpecialized.setAdapter(spin_specialized_2);
                }
                break;
            case R.id.spinnerSpecialized:
                if (school.contains("Bách Khoa")) {
                    specialized = spin_specialized_1.getItem(position).toString();
                } else {
                    specialized = spin_specialized_2.getItem(position).toString();
                }
                break;
            case R.id.spinnerGender:
                gender = spin_gender.getItem(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setOnDoneUpdate(OnDoneUpdateOff onDoneUpdate) {
        this.onDoneUpdate = onDoneUpdate;
    }

    public interface OnDoneUpdateOff {
        void successUpdate();

        void faildUpdate();
    }
}
