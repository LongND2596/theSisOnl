package longnd.thesis.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import longnd.thesis.R;
import longnd.thesis.data.model.Question;
import longnd.thesis.ui.test.test_step_two.TestStepTwoViewModel;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.Utils;

public class QuestionPagerAdapterOff extends PagerAdapter {
    private List<Question> questions;
    private Context context;
    private SaveResult saveResult;
    private RadioGroup rGroup;
    private TextView tvContent;
    private TextView tvTheme;
    private TestStepTwoViewModel viewModel;
    private int type;

    private RadioButton[] btnRadio;
    private View[] seperator;

    public QuestionPagerAdapterOff(Context context, List<Question> questions, TestStepTwoViewModel viewModel, int type) {
        this.context = context;
        this.questions = questions;
        this.viewModel = viewModel;
        this.type = type;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("LongND", "instantiateItem: " + position);
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
        tvContent.setText(context.getResources().getString(R.string.content_question, questions.get(position).getNumberId(), questions.get(position).getContent()));
        String[] answer;
        if (type == 1) {
            answer = context.getResources().getStringArray(R.array.answer_neo);
        } else if (type == 2) {
            answer = context.getResources().getStringArray(R.array.answer_riasec);
        } else {
            int kind = questions.get(position).getKind();
            tvTheme.setVisibility(View.VISIBLE);
            tvTheme.setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
            tvTheme.setText("Chủ đề : " + context.getResources().getStringArray(R.array.kind_psycho)[kind - 1]);
            switch (kind) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    answer = context.getResources().getStringArray(R.array.answer_psycho_kind_weak);
                    break;
                case 14:
                    answer = context.getResources().getStringArray(R.array.answer_psycho_kind_normal);
                    break;
                case 8:
                    answer = context.getResources().getStringArray(R.array.answer_psycho_kind_rather);
                    break;
                default:
                    answer = context.getResources().getStringArray(R.array.answer_psycho_kind_weak);
                    break;
            }
        }
        for (int i = 0; i < answer.length; i++) {
            btnRadio[i].setVisibility(View.VISIBLE);
            btnRadio[i].setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
            seperator[i].setVisibility(View.VISIBLE);
            btnRadio[i].setText(answer[i]);
        }

        container.addView(view);
        rGroup = view.findViewById(R.id.layoutResult);

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
            if (type == Define.Question.TYPE_PSY_POCHOLIGICAL) {
                saveResult.onSavePsycho(position, questions.get(position).getKind(), questions.get(position).getNumberId(), selected);
            } else {
                saveResult.onSaveDataResult(position, selected);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return questions.size();
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
        Log.d("LongND", "notifyDataSetChanged: vào đây");
        super.notifyDataSetChanged();
    }

    public void setSaveResult(SaveResult saveResult) {
        this.saveResult = saveResult;
    }

    public interface SaveResult {
        void onSaveDataResult(int position, int result);

        void onSavePsycho(int position, int kind, int numberId, int result);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position + 1);
    }
}
