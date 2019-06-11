package longnd.thesis.ui.tutorial;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import dagger.android.AndroidInjector;
import longnd.thesis.R;
import longnd.thesis.databinding.ActivityTutorialBinding;
import longnd.thesis.offline.main.MainOffActivity;
import longnd.thesis.ui.base.BaseActivity;
import longnd.thesis.ui.main.MainActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.SharedPrefs;

public class TutorialActivity extends BaseActivity<TutorialViewModel, ActivityTutorialBinding> {
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        SharedPrefs.getInstance().putBoolean(Define.SharedPref.KEY_IS_FIRST, true);
        binding.btnStartApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
                    intent = new Intent(TutorialActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(TutorialActivity.this, MainOffActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tutorial;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }

    @Override
    protected void initListenerOnClick() {

    }

    @Override
    public void onClick(View v) {

    }


}
