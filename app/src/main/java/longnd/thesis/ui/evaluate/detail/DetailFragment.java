package longnd.thesis.ui.evaluate.detail;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import longnd.thesis.R;
import longnd.thesis.adapter.ShowResultPsyAdapter;
import longnd.thesis.databinding.FragmentDetailBinding;
import longnd.thesis.network.result.PsyResultResponse;
import longnd.thesis.ui.base.BaseFragment;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import longnd.thesis.utils.EvaluateUtils;

public class DetailFragment extends BaseFragment<DetailViewModel, FragmentDetailBinding> {
    private int type;

    @Override
    protected void initListenerOnClick() {

    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        int idImage = 0;
        type = evaluateViewModel.getType();
        switch (type) {
            case Define.Question.TYPE_NEO:
                binding.detailNeo.setVisibility(View.VISIBLE);
                idImage = R.drawable.image_neo_rectangle;
                break;
            case Define.Question.TYPE_RIASEC:
                binding.detailRiasec.setVisibility(View.VISIBLE);
                idImage = R.drawable.image_riasec_rectangle;
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                binding.detaiPsy.setVisibility(View.VISIBLE);
                idImage = R.drawable.image_psy_rectangle;
                break;
        }
        Glide.with(getContext())
                .load(idImage)
                .into(binding.ivLogo);
    }

    @Override
    protected void initData() {
        switch (type) {
            case Define.Question.TYPE_NEO:
                detailNeo();
                break;
            case Define.Question.TYPE_RIASEC:
                detailRiasec();
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                detailPsy();
                break;
        }
    }

    private void detailPsy() {
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            Map<Integer, String> typeListPsy = DataUtils.getInstance().typeListPsy;
            List<PsyResultResponse.ResultPsy> resultPsycho = DataUtils.getInstance().resultPsycho;
            // lo âu
            binding.tvp1.setText(typeListPsy.get(resultPsycho.get(0).typeId));
            binding.tvp11.setText(resultPsycho.get(0).typeResult);
            // tram cam
            binding.tvp2.setText(typeListPsy.get(resultPsycho.get(1).typeId));
            binding.tvp22.setText(resultPsycho.get(1).typeResult);
            // stress
            binding.tvp3.setText(typeListPsy.get(resultPsycho.get(2).typeId));
            binding.tvp33.setText(resultPsycho.get(2).typeResult);
            // kho tap trung
            binding.tvp4.setText(typeListPsy.get(resultPsycho.get(3).typeId));
            binding.tvp44.setText(resultPsycho.get(3).typeResult);
            // tang dong
            binding.tvp5.setText(typeListPsy.get(resultPsycho.get(4).typeId));
            binding.tvp55.setText(resultPsycho.get(4).typeResult);
            // giao tiep xa hoi
            binding.tvp6.setText(typeListPsy.get(resultPsycho.get(5).typeId));
            binding.tvp66.setText(resultPsycho.get(5).typeResult);
            // kho khan ve hoc tap
            binding.tvp7.setText(typeListPsy.get(resultPsycho.get(6).typeId));
            binding.tvp77.setText(resultPsycho.get(6).typeResult);
            // Khó khăn trong định hướng nghề nghiệp
            binding.tvp8.setText(typeListPsy.get(resultPsycho.get(7).typeId));
            binding.tvp88.setText(resultPsycho.get(7).typeResult);
            // Khó khăn trong mối quan hệ với cha mẹ
            binding.tvp9.setText(typeListPsy.get(resultPsycho.get(8).typeId));
            binding.tvp99.setText(resultPsycho.get(8).typeResult);
            // Khó khăn trong mối quan hệ với thầy cô
            binding.tvp10.setText(typeListPsy.get(resultPsycho.get(9).typeId));
            binding.tvp100.setText(resultPsycho.get(9).typeResult);
            // Khó khăn trong mối quan hệ với bạn bè
            binding.tvp111.setText(typeListPsy.get(resultPsycho.get(10).typeId));
            binding.tvp1111.setText(resultPsycho.get(10).typeResult);
            // Hành vi thách thức - chống đối
            binding.tvp12.setText(typeListPsy.get(resultPsycho.get(11).typeId));
            binding.tvp222.setText(resultPsycho.get(11).typeResult);
            // Rối loạn hành vi ứng xử
            binding.tvp13.setText(typeListPsy.get(resultPsycho.get(12).typeId));
            binding.tvp333.setText(resultPsycho.get(12).typeResult);
            // Gây hấn
            binding.tvp14.setText(typeListPsy.get(resultPsycho.get(13).typeId));
            binding.tvp444.setText(resultPsycho.get(13).typeResult);

            if (typeListPsy.isEmpty()) {
                binding.tvp1.setText(resultPsycho.get(0).trouble);
                binding.tvp2.setText(resultPsycho.get(1).trouble);
                binding.tvp3.setText(resultPsycho.get(2).trouble);
                binding.tvp4.setText(resultPsycho.get(3).trouble);
                binding.tvp5.setText(resultPsycho.get(4).trouble);
                binding.tvp6.setText(resultPsycho.get(5).trouble);
                binding.tvp7.setText(resultPsycho.get(6).trouble);
                binding.tvp8.setText(resultPsycho.get(7).trouble);
                binding.tvp9.setText(resultPsycho.get(8).trouble);
                binding.tvp10.setText(resultPsycho.get(9).trouble);
                binding.tvp111.setText(resultPsycho.get(10).trouble);
                binding.tvp12.setText(resultPsycho.get(11).trouble);
                binding.tvp13.setText(resultPsycho.get(12).trouble);
                binding.tvp14.setText(resultPsycho.get(13).trouble);
            }
        } else {
            binding.detailPsycho.setVisibility(View.VISIBLE);
            String[] resultPsy = EvaluateUtils.evaluatePsycho(DataUtils.getInstance().resultPsychological);
            String[] kindPsy = getContext().getResources().getStringArray(R.array.kind_title_psycho);
            binding.recyclerPsycho.setLayoutManager(new LinearLayoutManager(getContext()));
            ShowResultPsyAdapter adapter = new ShowResultPsyAdapter(getContext(), kindPsy, resultPsy);
            binding.recyclerPsycho.setAdapter(adapter);
        }
    }

    private void detailRiasec() {
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            if (DataUtils.getInstance().resultRiasec.highestFieldExplain == null) {
                return;
            }
            binding.tvr1.setText(DataUtils.getInstance().resultRiasec.highestFieldExplain.type.toUpperCase());
            binding.tvr11.setText(DataUtils.getInstance().resultRiasec.highestFieldExplain.content);
        } else {
            binding.detailRiasec.setVisibility(View.VISIBLE);
            binding.tvr1.setText(EvaluateUtils.resultRiasecTitle(getContext(), evaluateViewModel.getMaxInPosition()).toUpperCase());
            binding.tvr11.setText(EvaluateUtils.resultRiasecDetail(getContext(), evaluateViewModel.getMaxInPosition()));
        }
    }

    private void detailNeo() {
        if (DataUtils.getInstance().versionApp.equals(Define.VERSION_ONL)) {
            binding.tv1.setText("DỄ CHẤP NHẬN: " + DataUtils.getInstance().resultNeo.get(0).getLevel().toUpperCase());
            binding.tv11.setText(DataUtils.getInstance().resultNeo.get(0).getExplain());

            binding.tv2.setText("TẬN TÂM: " + DataUtils.getInstance().resultNeo.get(1).getLevel().toUpperCase());
            binding.tv21.setText(DataUtils.getInstance().resultNeo.get(1).getExplain());

            binding.tv3.setText("CỞI MỞ, HAM HỌC HỎI: " + DataUtils.getInstance().resultNeo.get(2).getLevel());
            binding.tv31.setText(DataUtils.getInstance().resultNeo.get(2).getExplain());

            binding.tv4.setText("NHIỄU TÂM: " + DataUtils.getInstance().resultNeo.get(3).getLevel());
            binding.tv41.setText(DataUtils.getInstance().resultNeo.get(3).getExplain());

            binding.tv5.setText("HƯỚNG NGOẠI: " + DataUtils.getInstance().resultNeo.get(4).getLevel());
            binding.tv51.setText(DataUtils.getInstance().resultNeo.get(4).getExplain());
        } else {
            binding.detailNeo.setVisibility(View.VISIBLE);
            int[] results = evaluateViewModel.getResults();
            int[] nResults = viewModel.getResultsnNeo(DataUtils.getInstance().getCustomer().getGender(), results);
            binding.tv1.setText("DỄ CHẤP NHẬN: " + EvaluateUtils.wordResults(nResults[0]).toUpperCase());
            binding.tv11.setText(getContext().getResources().getStringArray(R.array.neo_a)[nResults[0]]);
            binding.tv2.setText("TẬN TÂM: " + EvaluateUtils.wordResults(nResults[1]).toUpperCase());
            binding.tv21.setText(getContext().getResources().getStringArray(R.array.neo_c)[nResults[0]]);
            binding.tv3.setText("CỞI MỞ, HAM HỌC HỎI: " + EvaluateUtils.wordResults(nResults[2]).toUpperCase());
            binding.tv31.setText(getContext().getResources().getStringArray(R.array.neo_o)[nResults[0]]);
            binding.tv4.setText("NHIỄU TÂM: " + EvaluateUtils.wordResults(nResults[3]).toUpperCase());
            binding.tv41.setText(getContext().getResources().getStringArray(R.array.neo_n)[nResults[0]]);
            binding.tv5.setText("HƯỚNG NGOẠI: " + EvaluateUtils.wordResults(nResults[4]).toUpperCase());
            binding.tv51.setText(getContext().getResources().getStringArray(R.array.neo_e)[nResults[0]]);
        }
    }

    @Override
    public Class<DetailViewModel> getModelClass() {
        return DetailViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onClick(View v) {

    }
}
