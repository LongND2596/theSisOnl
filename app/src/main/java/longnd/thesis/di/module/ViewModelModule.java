package longnd.thesis.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import longnd.thesis.di.ViewModelFactory;
import longnd.thesis.ui.customer.CustomerViewModel;
import longnd.thesis.ui.customer.signin.SignInViewModel;
import longnd.thesis.ui.customer.signup.SignUpViewModel;
import longnd.thesis.ui.dialog.EditCustomerOffViewModel;
import longnd.thesis.ui.dialog.EditCustomerViewModel;
import longnd.thesis.ui.evaluate.EvaluateViewModel;
import longnd.thesis.ui.evaluate.charts.ChartsViewModel;
import longnd.thesis.ui.evaluate.detail.DetailViewModel;
import longnd.thesis.ui.history.HistoryViewModel;
import longnd.thesis.ui.home.HomeViewModel;
import longnd.thesis.ui.load_data.LoadDataViewModel;
import longnd.thesis.ui.main.MainViewModel;
import longnd.thesis.ui.question.QuestionViewModel;
import longnd.thesis.ui.splash.SplashViewModel;
import longnd.thesis.ui.test.TestViewModel;
import longnd.thesis.ui.test.test_step_one.TestStepOneViewModel;
import longnd.thesis.ui.test.test_step_two.TestStepTwoViewModel;
import longnd.thesis.ui.tutorial.TutorialViewModel;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel.class)
    abstract ViewModel bindTutorialViewModel(TutorialViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CustomerViewModel.class)
    abstract ViewModel bindCustomerViewModel(CustomerViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel.class)
    abstract ViewModel bindSignInCustomerViewModel(SignInViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpCustomerViewModel(SignUpViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditCustomerViewModel.class)
    abstract ViewModel bindEditCustomerViewModel(EditCustomerViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditCustomerOffViewModel.class)
    abstract ViewModel bindEditCustomerOffViewModel(EditCustomerOffViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel.class)
    abstract ViewModel bindQuestionViewModel(QuestionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoadDataViewModel.class)
    abstract ViewModel bindLoadDataViewModel(LoadDataViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel.class)
    abstract ViewModel bindTestViewModel(TestViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestStepOneViewModel.class)
    abstract ViewModel bindTestStepOneViewModel(TestStepOneViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestStepTwoViewModel.class)
    abstract ViewModel bindTestStepTwoViewModel(TestStepTwoViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EvaluateViewModel.class)
    abstract ViewModel bindEvaluateViewModel(EvaluateViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel.class)
    abstract ViewModel bindChartsViewModel(ChartsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryViewModel(HistoryViewModel viewModel);
}
