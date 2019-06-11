package longnd.thesis.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;
import longnd.thesis.di.ViewModelFactory;
import longnd.thesis.offline.test.TestOffViewModel;
import longnd.thesis.ui.evaluate.EvaluateViewModel;
import longnd.thesis.ui.main.MainViewModel;
import longnd.thesis.ui.test.TestViewModel;

import javax.inject.Inject;

public abstract class BaseFragment<T extends ViewModel, V extends ViewDataBinding> extends Fragment
        implements View.OnClickListener {
    @Inject
    ViewModelFactory viewModelFactory;

    protected T viewModel;

    protected V binding;

    protected MainViewModel mainViewModel;
    protected TestViewModel testViewModel;
    protected TestOffViewModel testOffViewModel;
    protected EvaluateViewModel evaluateViewModel;

    private static long lastClickTime = System.currentTimeMillis();

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        binding.setLifecycleOwner(this);
        initView();
        initData();
        initListenerOnClick();
        return binding.getRoot();
    }

    protected abstract void initListenerOnClick();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(getModelClass());
            mainViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(MainViewModel.class);
            testViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(TestViewModel.class);
            testOffViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(TestOffViewModel.class);
            evaluateViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(EvaluateViewModel.class);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initObserve();
    }

    protected abstract void initObserve();


    protected abstract void initView();

    protected abstract void initData();

    public abstract Class<T> getModelClass();

    public abstract int getLayoutId();

    protected void showInputError(EditText editText, String message) {
        editText.setError(message);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

}
