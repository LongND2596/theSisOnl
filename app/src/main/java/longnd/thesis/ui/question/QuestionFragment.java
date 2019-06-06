package longnd.thesis.ui.question;

import android.view.View;

import androidx.fragment.app.Fragment;
import longnd.thesis.R;
import longnd.thesis.databinding.DetailQuestionBinding;
import longnd.thesis.ui.base.BaseFragment;

public class QuestionFragment extends BaseFragment<QuestionViewModel, DetailQuestionBinding> {
    @Override
    protected void initListenerOnClick() {

    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public Class<QuestionViewModel> getModelClass() {
        return QuestionViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.detail_question;
    }

    @Override
    public void onClick(View v) {

    }

    public Fragment getData(int position){

        return null;
    }
}
