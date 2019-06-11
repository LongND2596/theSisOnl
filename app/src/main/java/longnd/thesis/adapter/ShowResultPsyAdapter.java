package longnd.thesis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import longnd.thesis.R;
import longnd.thesis.utils.Fields;
import longnd.thesis.utils.Utils;

public class ShowResultPsyAdapter extends RecyclerView.Adapter<ShowResultPsyAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private String[] kinds;
    private String[] results;

    public ShowResultPsyAdapter(Context context, String[] kinds, String[] results) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.kinds = kinds;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_show_result_psycho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(kinds[position] + " :");
        holder.message.setText(results[position]);
    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView message;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            title.setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
            message = itemView.findViewById(R.id.message);
            message.setTypeface(Utils.getTypeFace(context, Fields.FONT_TIMES));
        }
    }
}
