package longnd.thesis.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import longnd.thesis.R;
import longnd.thesis.adapter.QuestionTypeAdapter;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.databinding.FramgmentHomeBinding;
import longnd.thesis.network.pojo.login.DataQuestionType;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.ui.custom.ViewPagerCustom;
import longnd.thesis.ui.test.TestActivity;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;

public class HomeFragment extends BaseFragment<HomeViewModel, FramgmentHomeBinding> implements QuestionTypeAdapter.ChooseQuestionsType {
    private ViewPagerCustom mPager;
    private List<DataQuestionType.BaseTypeQuestion> questionsType;

    @Override
    public Class<HomeViewModel> getModelClass() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.framgment_home;
    }

    @Override
    protected void initListenerOnClick() {

    }

    @Override
    protected void initObserve() {
        viewModel.getObserveQuestionsType().observe(getViewLifecycleOwner(), this::observeQuestionsType);
    }

    private void observeQuestionsType(ObjectResponse<DataQuestionType> objectResponse) {
        if (objectResponse == null) return;
        if (objectResponse.getStatus() == Define.ResponseStatus.SUCCESS) {
            viewModel.setObserveQuestionsType(null);
            viewModel.getObserveQuestionsType().removeObservers(this);
            if (objectResponse.getData() != null) {
                questionsType = new ArrayList<>();
                questionsType.add(objectResponse.getData().getNeo());
                questionsType.add(objectResponse.getData().getRiasec());
                questionsType.add(objectResponse.getData().getPsychology());
                DataUtils.getInstance().setQuestionsType(questionsType);
                showQuestionsType();
            }
        }

    }

    private void showQuestionsType() {
        questionsType = DataUtils.getInstance().getQuestionsType();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        QuestionTypeAdapter adapter = new QuestionTypeAdapter(getContext(), questionsType);
        adapter.setChooseQuestionsType(this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        ArrayList<Drawable> array = new ArrayList<>();
        array.add(getContext().getDrawable(R.drawable.flipper_one));
        array.add(getContext().getDrawable(R.drawable.flipper_two));
        array.add(getContext().getDrawable(R.drawable.flipper_three));
        for (int i = 0; i < array.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext())
                    .load(array.get(i))
                    .into(imageView);
            binding.viewFlipper.addView(imageView);
        }

        binding.viewFlipper.setAutoStart(true);
        binding.viewFlipper.setFlipInterval(2000);
        Animation animation_in = AnimationUtils.loadAnimation(getContext(), R.anim.image_in);
        Animation animation_out = AnimationUtils.loadAnimation(getContext(), R.anim.image_out);
        binding.viewFlipper.setInAnimation(animation_in);
        binding.viewFlipper.setOutAnimation(animation_out);
    }

    @Override
    protected void initData() {
        mainViewModel.setNumberBack(Fields.ON_BACK);
        if (DataUtils.getInstance().getQuestionsType() == null) {
            viewModel.getListQuestionType();
        } else {
            showQuestionsType();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void openQuestionType(int position) {
        if (position == Define.QuestionsType.NEO) {
            Intent intentNeo = new Intent(getContext(), TestActivity.class);
            intentNeo.putExtra(Define.TYPE_QUESTION, Define.Question.TYPE_NEO);
            startActivity(intentNeo);
        } else if (position == Define.QuestionsType.RIASEC) {
            Intent intentRisec = new Intent(getContext(), TestActivity.class);
            intentRisec.putExtra(Define.TYPE_QUESTION, Define.Question.TYPE_RIASEC);
            startActivity(intentRisec);
        } else {
            Intent intentPsy = new Intent(getContext(), TestActivity.class);
            intentPsy.putExtra(Define.TYPE_QUESTION, Define.Question.TYPE_PSY_POCHOLIGICAL);
            startActivity(intentPsy);
        }
    }
}
