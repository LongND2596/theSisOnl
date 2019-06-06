package longnd.thesis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import longnd.thesis.R;
import longnd.thesis.network.pojo.login.history.HistoryResponse;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.Utils;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<HistoryResponse.DataHistory> results;
    private OpenHistoryEvaluate openHistoryEvaluate;

    public HistoryAdapter(Context context, List<HistoryResponse.DataHistory> results) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.results = results;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.tvTitle.setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
        int type = 0;
        // set data
        switch (results.get(position).type) {
            case "neo":
                type = Define.Question.TYPE_NEO;
                Glide.with(context)
                        .load(R.drawable.image_neo_rectangle)
                        .into(holder.ivLogo);
                break;
            case "riasec":
                type = Define.Question.TYPE_RIASEC;
                Glide.with(context)
                        .load(R.drawable.image_riasec_rectangle)
                        .into(holder.ivLogo);
                break;
            case "psychology":
                type = Define.Question.TYPE_PSY_POCHOLIGICAL;
                Glide.with(context)
                        .load(R.drawable.image_psy_rectangle)
                        .into(holder.ivLogo);
                break;
        }
        holder.tvTitle.setText(results.get(position).title);
        holder.tvTime.setText(results.get(position).created_at);
        int finalType = type;
        holder.btnView.setOnClickListener(view -> {
            openHistoryEvaluate.onOpenEvaluate(finalType, results.get(position).id);
        });
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        ImageView ivLogo;
        TextView tvTime;
        TextView tvTitle;
        Button btnView;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_logo);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
            btnView = itemView.findViewById(R.id.btn_view);
        }
    }

    public void setOpenHistoryEvaluate(OpenHistoryEvaluate openHistoryEvaluate) {
        this.openHistoryEvaluate = openHistoryEvaluate;
    }

    public interface OpenHistoryEvaluate {
        void onOpenEvaluate(int type, int id);
    }
}
