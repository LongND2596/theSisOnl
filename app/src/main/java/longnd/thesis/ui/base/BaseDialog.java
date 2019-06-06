package longnd.thesis.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;
import longnd.thesis.di.ViewModelFactory;

public abstract class BaseDialog<T extends ViewModel, V extends ViewDataBinding> extends DialogFragment implements View.OnClickListener {

    @Inject
    ViewModelFactory viewModelFactory;
    
    protected T viewModel;
    protected V binding;
    protected Handler handler;

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        binding.setLifecycleOwner(this);
        setCancelable(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(getModelClass());
        initView(view);
        initOnClick();
        initData();
        handler = new Handler();
    }

    protected abstract Class<T> getModelClass();

    protected abstract void initOnClick();

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void avoidDulicateClick(View view){
        view.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        },1000);
    }
}
