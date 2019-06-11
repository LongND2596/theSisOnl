package longnd.thesis.ui.customer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import longnd.thesis.R;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Customer;
import longnd.thesis.databinding.FragmentCustomerBinding;
import longnd.thesis.di.OnOpenCustomer;
import longnd.thesis.network.pojo.login.ProfileResponse;
import longnd.thesis.network.pojo.login.UserResponse;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.ui.dialog.DialogEditCustomer;
import longnd.thesis.ui.dialog.DialogEditCustomerOff;
import longnd.thesis.ui.dialog.DialogSelectImageSource;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.PsyLoading;
import longnd.thesis.utils.SharedPrefs;
import longnd.thesis.utils.Utils;

/**
 * Show data customer
 */
public class CustomerFragment extends BaseFragment<CustomerViewModel, FragmentCustomerBinding>
        implements DialogSelectImageSource.OnSelectImage, DialogEditCustomer.OnDoneUpdate, DialogEditCustomerOff.OnDoneUpdateOff {

    private OnOpenCustomer onOpenCustomer;
    private ProfileResponse profileResponse;

    private Customer customer;

    @Override
    protected void initListenerOnClick() {
        binding.btnExit.setOnClickListener(this);
        binding.ivLogo.setOnClickListener(this);
        binding.btnEdit.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        Utils.hideKeyboard(getActivity());
    }

    @Override
    protected void initData() {
        initObserve();
        mainViewModel.setNumberBack(Fields.DONT_BACK);
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            initDataOnline();
        } else {
            initDataOffline();
        }
    }

    /**
     * Show data in Offline
     */
    private void initDataOffline() {
        customer = DataUtils.getInstance().getCustomer();
        binding.tvNameCustomer.setText(customer.getLastName());

        if (customer.getSchool() != null && !customer.getSchool().isEmpty()) {
            binding.tvSchool.setText(customer.getSchool());
        }
        if (customer.getSpecialized() != null && !customer.getSpecialized().isEmpty()) {
            binding.tvSpecialized.setText(customer.getSpecialized());
        }
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            binding.tvEmail.setText(customer.getEmail());
        }
        if (customer.getPhone() != null && !customer.getPhone().isEmpty()) {
            binding.tvPhone.setText(customer.getPhone());
        }
        if (customer.getGender() == 0) {
            binding.tvGender.setText("Nữ");
        } else if (customer.getGender() == 1) {
            binding.tvGender.setText("Nam");
        }
        if (customer.getBirthdate() != null && !customer.getBirthdate().isEmpty()) {
            binding.tvBirthDay.setText(customer.getBirthdate());
        }
        if (customer.getImage() != null && !customer.getImage().isEmpty()) {
            String path = Fields.ROOT_FOLDER + File.separator + customer.getImage();
            if (Utils.existsPathImage(path)) {
                Glide.with(getContext())
                        .load(new File(Fields.ROOT_FOLDER + File.separator + customer.getImage()))
                        .into(binding.ivLogo);
            } else {
                Toast.makeText(getContext(), "Đường dẫn hình ảnh có lỗi!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Show data in Online
     */
    private void initDataOnline() {
        UserResponse userResponse = DataUtils.getInstance().getUser();
        if (userResponse == null) {
            return;
        }
        profileResponse = DataUtils.getInstance().getProfile();
        if (profileResponse == null) {
            return;
        }
        if (profileResponse.getFullname() == null) {
            binding.tvNameCustomer.setText(userResponse.getName());
        } else {
            binding.tvNameCustomer.setText(profileResponse.getFullname());
        }
        if (profileResponse.getUniversity() != null && !profileResponse.getUniversity().isEmpty()) {
            binding.tvSchool.setText(profileResponse.getUniversity());
        }
        if (profileResponse.getSpeciality() != null && !profileResponse.getSpeciality().isEmpty()) {
            binding.tvSpecialized.setText(profileResponse.getSpeciality());
        }
        binding.tvEmail.setText(userResponse.getEmail());
        if (profileResponse.getPhoneNumber() != null && !profileResponse.getPhoneNumber().isEmpty()) {
            binding.tvPhone.setText(profileResponse.getPhoneNumber());
        }
        if (profileResponse.getSex() != null && !profileResponse.getSex().isEmpty()) {
            binding.tvGender.setText(profileResponse.getSex());
        }
        if (profileResponse.getBirthday() != null && !profileResponse.getBirthday().isEmpty()) {
            binding.tvBirthDay.setText(profileResponse.getBirthday());
        }
        showAvatar(true);
    }

    @SuppressLint("CheckResult")
    private void showAvatar(Boolean skipMemory) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_default);
        requestOptions.error(R.drawable.ic_default);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(skipMemory);

        String pathAvatar = Fields.PATH_AVATAR + profileResponse.getAvatarPath();

        Glide.with(binding.ivLogo)
                .setDefaultRequestOptions(requestOptions)
                .asBitmap()
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        PsyLoading.getInstance(getContext()).hidden();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        PsyLoading.getInstance(getContext()).hidden();
                        return false;
                    }
                })
                .load(pathAvatar)
                .into(binding.ivLogo);
    }


    @Override
    protected void initObserve() {
        viewModel.getIsUploadAvatar().observe(getViewLifecycleOwner(), this::observeUploadAvatar);
    }

    private void observeUploadAvatar(ObjectResponse<Boolean> booleanObjectResponse) {
        if (booleanObjectResponse == null) {
            return;
        }
        if (booleanObjectResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.getIsUploadAvatar().removeObservers(this);
            viewModel.setIsUploadAvatar(null);
            if (booleanObjectResponse.getData()) {
                PsyLoading.getInstance(getContext()).show();
                showAvatar(true);
            } else {
                PsyLoading.getInstance(getContext()).hidden();
                Snackbar.make(getView(), "Đã có lỗi xảy ra!", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public Class<CustomerViewModel> getModelClass() {
        return CustomerViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_customer;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExit:
                onExitCustomer();
                break;
            case R.id.ivLogo:
                if (!requestPermision()) {
                    showDialogSelect();
                }
                break;
            case R.id.btnEdit:
                if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
                    DialogEditCustomer editCustomer = new DialogEditCustomer();
                    editCustomer.setOnDoneUpdate(this);
                    editCustomer.show(getFragmentManager(), DialogEditCustomer.class.getName());
                } else {
                    DialogEditCustomerOff editCustomerOff = new DialogEditCustomerOff();
                    editCustomerOff.setOnDoneUpdateOff(this);
                    editCustomerOff.show(getFragmentManager(), DialogEditCustomer.class.getName());
                }
                break;
            default:
                break;
        }
    }

    private void showDialogSelect() {
        DialogSelectImageSource selectImageSource = new DialogSelectImageSource();
        selectImageSource.setOnSelectImage(this);
        selectImageSource.show(getFragmentManager(), DialogSelectImageSource.class.getName());
    }

    private boolean requestPermision() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        } else {
            if (isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                return false;
            } else {
                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        102
                );
                return true;
            }
        }
    }

    private boolean requestPermisionCamera() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        } else {
            if (isGranted(Manifest.permission.CAMERA)) {
                return false;
            } else {
                requestPermissions(
                        new String[]{
                                Manifest.permission.CAMERA,
                        },
                        103
                );
                return true;
            }
        }
    }

    private boolean isGranted(String permison) {
        return ActivityCompat.checkSelfPermission(getContext(), permison) == PackageManager.PERMISSION_GRANTED;
    }


    private void onExitCustomer() {
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            SharedPrefs.getInstance().putString(Define.SharedPref.KEY_TOKEN, Define.SharedPref.VALUE_DEFAULT);
            DataUtils.getInstance().setUser(null);
            DataUtils.getInstance().setProfile(null);
        } else {
            SharedPrefs.getInstance().putString(Fields.KEY_EMAIL, Fields.DEFAULT_VALUE);
            SharedPrefs.getInstance().putString(Fields.KEY_PASS, Fields.DEFAULT_VALUE);
            DataUtils.getInstance().setCustomer(null);
        }
        onOpenCustomer.openSignInCustomer();
    }

    @Override
    public void onSelectFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Fields.PICK_IMAGE);
    }

    @Override
    public void onSelectTakePicture() {
        if (!requestPermisionCamera()) {
            openCamera();
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, Fields.TAKE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode && requestCode == Fields.PICK_IMAGE) {
            if (!Utils.existsPsyFolder()) {
                return;
            }
            if (data == null) {
                return;
            }
            PsyLoading.getInstance(getContext()).show();
            // save avatar local
            viewModel.saveImageToMemory(getActivity(), data.getData());
            // save avatar server
            viewModel.updateAvatarToServer(DataUtils.getInstance().getToken());
        }
        if (Activity.RESULT_OK == resultCode && requestCode == Fields.TAKE_IMAGE) {
            Utils.existsPsyFolder();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            String filePath = "";
            try {
                filePath = Fields.ROOT_FOLDER + "/" + System.currentTimeMillis() + Fields.FORMAT_IMAGE;
                File file = new File(filePath);
                FileOutputStream fOut = new FileOutputStream(file);
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                fOut.flush();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewModel.pathImage = filePath;
            viewModel.updateAvatarToServer(DataUtils.getInstance().getToken());
        }
    }

    public void setOnOpenCustomer(OnOpenCustomer onOpenCustomer) {
        this.onOpenCustomer = onOpenCustomer;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                showDialogSelect();
            }
        } else if (requestCode == 103) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    @Override
    public void successUpdate() {
        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        initData();
    }

    @Override
    public void faildUpdate() {
        Toast.makeText(getContext(), "Có lỗi cập nhật thông tin người dùng", Toast.LENGTH_SHORT).show();
    }
}
