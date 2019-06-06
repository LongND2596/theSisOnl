package longnd.thesis.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import longnd.thesis.R;

public class DialogSelectImageSource extends DialogFragment implements View.OnClickListener {
    private OnSelectImage onSelectImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_image, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.layout_gallery).setOnClickListener(this);
        view.findViewById(R.id.layout_camera).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_gallery:
                onSelectImage.onSelectFromGallery();
                break;
            case R.id.layout_camera:
                onSelectImage.onSelectTakePicture();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void setOnSelectImage(OnSelectImage onSelectImage) {
        this.onSelectImage = onSelectImage;
    }

    public interface OnSelectImage {
        void onSelectFromGallery();

        void onSelectTakePicture();
    }
}
