package longnd.thesis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import longnd.thesis.R;
import longnd.thesis.network.entity.QuestionShow;
import longnd.thesis.ui.test.test_step_two.TestStepTwoViewModel;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.Utils;

public class QuestionPagerAdapter extends PagerAdapter {
    private List<QuestionShow> question;
    private Context context;
    private SaveResult saveResult;
    private RadioGroup rGroup;
    private TextView tvContent;
    private TextView tvTheme;
    private TestStepTwoViewModel viewModel;
    private int type;

    private RadioButton[] btnRadio;
    private View[] seperator;

    public QuestionPagerAdapter(Context context, List<QuestionShow> question, TestStepTwoViewModel viewModel, int type) {
        this.context = context;
        this.question = question;
        this.viewModel = viewModel;
        this.type = type;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_question, container, false);
        tvContent = view.findViewById(R.id.tvContent);
        tvContent.setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
        tvTheme = view.findViewById(R.id.tvTheme);


        btnRadio = new RadioButton[]{
                view.findViewById(R.id.btnResult01),
                view.findViewById(R.id.btnResult02),
                view.findViewById(R.id.btnResult03),
                view.findViewById(R.id.btnResult04),
                view.findViewById(R.id.btnResult05),
                view.findViewById(R.id.btnResult06),
                view.findViewById(R.id.btnResult07),
                view.findViewById(R.id.btnResult08),
                view.findViewById(R.id.btnResult09),
                view.findViewById(R.id.btnResult10),
                view.findViewById(R.id.btnResult11)
        };

        seperator = new View[]{
                view.findViewById(R.id.seperator1),
                view.findViewById(R.id.seperator2),
                view.findViewById(R.id.seperator3),
                view.findViewById(R.id.seperator4),
                view.findViewById(R.id.seperator5),
                view.findViewById(R.id.seperator6),
                view.findViewById(R.id.seperator7),
                view.findViewById(R.id.seperator8),
                view.findViewById(R.id.seperator9),
                view.findViewById(R.id.seperator10),
                view.findViewById(R.id.seperator11)
        };

        tvContent.setText(context.getResources().getString(R.string.content_question, question.get(position).getStt(), question.get(position).getContent()));

        for (int i = 0; i < question.get(position).getAnswers().size(); i++) {
            btnRadio[i].setVisibility(View.VISIBLE);
            btnRadio[i].setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
            seperator[i].setVisibility(View.VISIBLE);
            btnRadio[i].setText(question.get(position).getAnswers().get(i));
        }

        container.addView(view);
        rGroup = view.findViewById(R.id.layoutResult);

        if (type == Define.Question.TYPE_NEO || type == Define.Question.TYPE_RIASEC) {
            tvTheme.setVisibility(View.GONE);
        } else {
            tvTheme.setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
            tvTheme.setText(context.getResources().getString(R.string.theme_question, question.get(position).getTitleQuestion()));
            if (question.get(position).getTitleQuestion().contains("Khó khăn trong định hướng nghề nghiệp")){
                tvTheme.setText(context.getResources().getString(R.string.theme_question, question.get(position).getTitleQuestion()) + "\n(Thang điểm 1-10)");
            }
        }

        if (viewModel.getDataResults(position) != -1) {
            btnRadio[viewModel.getDataResults(position)].setChecked(true);
        }
        rGroup.setOnCheckedChangeListener((radioGroub, checkedId) -> {
            int selected;
            switch (checkedId) {
                case R.id.btnResult01:
                    selected = 0;
                    break;
                case R.id.btnResult02:
                    selected = 1;
                    break;
                case R.id.btnResult03:
                    selected = 2;
                    break;
                case R.id.btnResult04:
                    selected = 3;
                    break;
                case R.id.btnResult05:
                    selected = 4;
                    break;
                case R.id.btnResult06:
                    selected = 5;
                    break;
                case R.id.btnResult07:
                    selected = 6;
                    break;
                case R.id.btnResult08:
                    selected = 7;
                    break;
                case R.id.btnResult09:
                    selected = 8;
                    break;
                case R.id.btnResult10:
                    selected = 9;
                    break;
                case R.id.btnResult11:
                    selected = 10;
                    break;
                default:
                    selected = -1;
                    break;
            }
            saveResult.onSaveDataResult(position, question.get(position).getId(), question.get(position).getTypeId(), selected);
        });


        return view;
    }

    @Override
    public int getCount() {
        return question.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void setSaveResult(SaveResult saveResult) {
        this.saveResult = saveResult;
    }

    public interface SaveResult {
        void onSaveDataResult(int position, int questionId,int typeId, int result);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position + 1);
    }
}
