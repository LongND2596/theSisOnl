package longnd.thesis.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import longnd.thesis.offline.customer.CustomerOffFragment;
import longnd.thesis.offline.customer.edit.DialogEditCustomerOff;
import longnd.thesis.offline.customer.signin.SignInOffFragment;
import longnd.thesis.offline.customer.signup.SignUpOffFragment;
import longnd.thesis.offline.history.HistoryOffFragment;
import longnd.thesis.offline.home.HomeOffFragment;
import longnd.thesis.offline.main.MainOffActivity;
import longnd.thesis.offline.test.TestOffActivity;
import longnd.thesis.offline.test.test_step_one.TestStepOneOffFragment;
import longnd.thesis.offline.test.test_step_two.TestStepTwoOffFragment;
import longnd.thesis.ui.customer.CustomerFragment;
import longnd.thesis.ui.customer.signin.SignInFragment;
import longnd.thesis.ui.customer.signup.SignUpFragment;
import longnd.thesis.ui.dialog.DialogEditCustomer;
import longnd.thesis.ui.evaluate.EvaluateActivity;
import longnd.thesis.ui.evaluate.charts.ChartsFragment;
import longnd.thesis.ui.evaluate.detail.DetailFragment;
import longnd.thesis.ui.history.HistoryFragment;
import longnd.thesis.ui.home.HomeFragment;
import longnd.thesis.ui.load_data.LoadDataFragment;
import longnd.thesis.ui.main.MainActivity;
import longnd.thesis.ui.question.QuestionFragment;
import longnd.thesis.ui.splash.SplashActivity;
import longnd.thesis.ui.test.TestActivity;
import longnd.thesis.ui.test.test_step_one.TestStepOneFragment;
import longnd.thesis.ui.test.test_step_two.TestStepTwoFragment;
import longnd.thesis.ui.tutorial.TutorialActivity;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract TutorialActivity bindTutorialActivity();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector
    abstract CustomerFragment bindCustomerFragment();

    @ContributesAndroidInjector
    abstract DialogEditCustomer bindEditCustomerDialog();

    @ContributesAndroidInjector
    abstract SignInFragment bindSignInCustomerFragment();

    @ContributesAndroidInjector
    abstract SignUpFragment bindSignUpCustomerFragment();

    @ContributesAndroidInjector
    abstract QuestionFragment bindQuestionFragment();

    @ContributesAndroidInjector
    abstract LoadDataFragment bindLoadDataFragment();

    @ContributesAndroidInjector
    abstract TestActivity bindTestActivity();

    @ContributesAndroidInjector
    abstract TestStepOneFragment bindTestStepOneFragment();

    @ContributesAndroidInjector
    abstract TestStepTwoFragment bindTestStepTwoFragment();

    @ContributesAndroidInjector
    abstract EvaluateActivity bindEvaluateActivity();

    @ContributesAndroidInjector
    abstract ChartsFragment bindChartsFragment();

    @ContributesAndroidInjector
    abstract DetailFragment bindDetailFragment();

    @ContributesAndroidInjector
    abstract HistoryFragment bindHistoryFragment();

    // region -> Offline

    @ContributesAndroidInjector
    abstract MainOffActivity bindMainOffActivity();

    @ContributesAndroidInjector
    abstract CustomerOffFragment bindCustomerOffFragment();

    @ContributesAndroidInjector
    abstract HomeOffFragment bindHomeOffFragment();

    @ContributesAndroidInjector
    abstract SignInOffFragment bindSignInOffFragment();

    @ContributesAndroidInjector
    abstract SignUpOffFragment bindSignUpOffFragment();

    @ContributesAndroidInjector
    abstract HistoryOffFragment bindHistoryOffFragment();

    @ContributesAndroidInjector
    abstract TestOffActivity bindTestOffFragment();

    @ContributesAndroidInjector
    abstract TestStepOneOffFragment bindTestStepOneOffFragment();

    @ContributesAndroidInjector
    abstract TestStepTwoOffFragment bindTestStepTwoOffFragment();

    @ContributesAndroidInjector
    abstract DialogEditCustomerOff bindEditCustomerOffDialog();

    // endregion
}
