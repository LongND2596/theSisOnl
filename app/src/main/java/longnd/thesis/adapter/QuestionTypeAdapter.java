package longnd.thesis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import longnd.thesis.R;
import longnd.thesis.network.pojo.login.DataQuestionType;
import longnd.thesis.utils.Define;

public class QuestionTypeAdapter extends RecyclerView.Adapter<QuestionTypeAdapter.QuestionTypeHolder> {
    private LayoutInflater inflater;
    private List<DataQuestionType.BaseTypeQuestion> questionsType;

    public QuestionTypeAdapter(Context context, List<DataQuestionType.BaseTypeQuestion> questionsType) {
        this.questionsType = questionsType;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public QuestionTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_question_type, parent, false);
        return new QuestionTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionTypeHolder holder, int position) {
        switch (position) {
            case Define.QuestionsType.NEO:
                holder.ivLogo.setImageResource(R.drawable.image_neo_rectangle);
                break;
            case Define.QuestionsType.RIASEC:
                holder.ivLogo.setImageResource(R.drawable.image_riasec_rectangle);
                break;
            case Define.QuestionsType.PSY:
                holder.ivLogo.setImageResource(R.drawable.image_psy_rectangle);
                break;
            default:
                break;
        }
        holder.titleLabel.setText(questionsType.get(position).getTitle());
        holder.contentLabel.setText(questionsType.get(position).getDescription());
        holder.content.setOnClickListener((v) -> {
            chooseQuestionsType.openQuestionType(position);
        });
    }

    @Override
    public int getItemCount() {
        return questionsType.size();
    }

    class QuestionTypeHolder extends RecyclerView.ViewHolder {
        View content;
        ImageView ivLogo;
        TextView titleLabel;
        TextView contentLabel;

        public QuestionTypeHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            ivLogo = itemView.findViewById(R.id.logo);
            titleLabel = itemView.findViewById(R.id.titleLabel);
            titleLabel.setSelected(true);
            contentLabel = itemView.findViewById(R.id.contentLabel);
        }
    }

    private ChooseQuestionsType chooseQuestionsType;

    public void setChooseQuestionsType(ChooseQuestionsType chooseQuestionsType) {
        this.chooseQuestionsType = chooseQuestionsType;
    }

    public interface ChooseQuestionsType {
        void openQuestionType(int position);
    }
}
