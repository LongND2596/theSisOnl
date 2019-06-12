package longnd.thesis.offline.test.test_step_two;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Result;
import longnd.thesis.data.model.ResultNeo;
import longnd.thesis.data.model.ResultPsychological;
import longnd.thesis.data.model.ResultRiasec;
import longnd.thesis.data.repository.ResultRespository;

public class TestStepTwoOffViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private ResultRespository resultRespository;
    private MutableLiveData<ObjectResponse<Long>> isInsertResultNeo;
    private MutableLiveData<ObjectResponse<Long>> isInsertResultRiasec;
    private MutableLiveData<ObjectResponse<Long>> isInsertResultPsycho;
    private MutableLiveData<ObjectResponse<Long>> isInsertResult;
    private int[] results;

    public int[] resultsLoAu = new int[20];
    public int[] resultsTramCam = new int[20];
    public int[] resultsStress = new int[20];
    public int[] resultsKhoTapTrung = new int[20];
    public int[] resultsTangDong = new int[20];
    public int[] resultsKKGiaoTiepXaHoi = new int[20];
    public int[] resultsKKHocTap = new int[20];
    public int[] resultsKKDinhHuongNgheNghiep = new int[20];
    public int[] resultsKKQuanHeChaMe = new int[20];
    public int[] resultsKKQuanHeThayCo = new int[20];
    public int[] resultsKKQuanHeBanBe = new int[20];
    public int[] resultsHanhViChongDoi = new int[20];
    public int[] resultsRoiLoanHanhVi = new int[20];
    public int[] resultsGayHan = new int[20];

    @Inject
    TestStepTwoOffViewModel(ResultRespository resultRespository) {
        this.resultRespository = resultRespository;
        compositeDisposable = new CompositeDisposable();
        isInsertResultNeo = new MutableLiveData<>();
        isInsertResult = new MutableLiveData<>();
        isInsertResultRiasec = new MutableLiveData<>();
        isInsertResultPsycho = new MutableLiveData<>();
    }

    public void insertResultNeo(ResultNeo resultNeo) {
        compositeDisposable.add(
                resultRespository.insertResultNeo(resultNeo)
                        .doOnSubscribe(dispose -> isInsertResultNeo.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isInsertResultNeo.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isInsertResultNeo.setValue(new ObjectResponse<Long>().error(throwable)))
        );
    }

    public void insertResult(Result result) {
        compositeDisposable.add(
                resultRespository.insertResult(result)
                        .doOnSubscribe(dispose -> isInsertResult.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isInsertResult.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isInsertResult.setValue(new ObjectResponse<Long>().error(throwable)))
        );
    }

    public void insertResultRiasec(ResultRiasec resultRiasec) {
        compositeDisposable.add(
                resultRespository.insertResultRiasec(resultRiasec)
                        .doOnSubscribe(dispose -> isInsertResultRiasec.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isInsertResultRiasec.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isInsertResultRiasec.setValue(new ObjectResponse<Long>().error(throwable)))
        );
    }

    /**
     * used to save the record psychological
     *
     * @param resultPsychological
     */
    public void insertResultPsycho(ResultPsychological resultPsychological) {
        compositeDisposable.add(
                resultRespository.insertResultPsycho(resultPsychological)
                        .doOnSubscribe(dispose -> isInsertResultPsycho.setValue(new ObjectResponse<Long>().loading()))
                        .subscribe(response -> isInsertResultPsycho.setValue(new ObjectResponse<Long>().success(response))
                                , throwable -> isInsertResultPsycho.setValue(new ObjectResponse<Long>().error(throwable)))
        );
    }

    public void initResults(int size) {
        results = new int[size];
        for (int i = 0; i < size; i++) {
            results[i] = -1;
        }
    }

    public void setDataResults(int position, int result) {
        results[position] = result;
    }

    public int getDataResults(int position) {
        return results[position];
    }

    public int[] getResuls() {
        return results;
    }


    public MutableLiveData<ObjectResponse<Long>> getIsInsertResultNeo() {
        return isInsertResultNeo;
    }

    public void setIsInsertResultNeo(ObjectResponse<Long> value) {
        isInsertResultNeo.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Long>> getIsInsertResultRiasec() {
        return isInsertResultRiasec;
    }

    public void setIsInsertResultRiasec(ObjectResponse<Long> value) {
        isInsertResultRiasec.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Long>> getIsInsertResultPsycho() {
        return isInsertResultPsycho;
    }

    public void setIsInsertResultPsycho(ObjectResponse<Long> value) {
        isInsertResultPsycho.setValue(value);
    }

    public MutableLiveData<ObjectResponse<Long>> getIsInsertResult() {
        return isInsertResult;
    }

    public void setIsInsertResult(ObjectResponse<Long> value) {
        isInsertResult.setValue(value);
    }

    public ResultNeo getResulNeo() {
        int c = 0;
        int a = 0;
        int o = 0;
        int n = 0;
        int e = 0;
        for (int i = 0; i < results.length; i++) {
            switch (i % 5) {
                case 0:
                    c += results[i];
                    break;
                case 1:
                    a += results[i];
                    break;
                case 2:
                    o += results[i];
                    break;
                case 3:
                    n += results[i];
                    break;
                case 4:
                    e += results[i];
                    break;
            }
        }
        return new ResultNeo(n, e, o, a, c);
    }

    public ResultRiasec getResultRiasec() {
        int reality = 0;
        int discover = 0;
        int art = 0;
        int society = 0;
        int convince = 0;
        int rule = 0;
        for (int i = 0; i < results.length; i++) {
            int point = 0;
            switch (results[i]) {
                case 0:
                case 1:
                    point = 0;
                    break;
                case 2:
                    point = 5;
                    break;
                case 3:
                case 4:
                    point = 10;
                    break;
            }
            switch (i % 6) {
                case 0:
                    /**
                     * thực tế
                     */
                    reality += point;
                    break;
                case 1:
                    /**
                     * khám phá
                     */
                    discover += point;
                    break;
                case 2:
                    /**
                     * nghệ thuật
                     */
                    art += point;
                    break;
                case 3:
                    /**
                     * xã hội
                     */
                    society += point;
                    break;
                case 4:
                    /**
                     * thuyết phục
                     */
                    convince += point;
                    break;
                case 5:
                    /**
                     * quy tắc
                     */
                    rule += point;
                    break;
            }
        }
        return new ResultRiasec(rule, society, discover, reality, art, convince);
    }

    private int calculateScore(int[] result) {
        int score = 0;
        for (int i : result) {
            score += i;
        }
        return score;
    }

    public ResultPsychological getResultPsycho() {
        int loAu = calculateScore(resultsLoAu);
        int tramCam = calculateScore(resultsTramCam);
        int stress = calculateScore(resultsStress);
        int khoTapTrung = calculateScore(resultsKhoTapTrung);
        int tangDong = calculateScore(resultsTangDong);
        int giaoTiepXaHoi = calculateScore(resultsKKGiaoTiepXaHoi);
        int hocTap = calculateScore(resultsKKHocTap);
        int dinhHuongNgheNghiep = calculateScore(resultsKKDinhHuongNgheNghiep);
        int quanHeChaMe = calculateScore(resultsKKQuanHeChaMe);
        int quanHeThayCo = calculateScore(resultsKKQuanHeThayCo);
        int quanHeBanBe = calculateScore(resultsKKQuanHeBanBe);
        int hanhViChongDoi = calculateScore(resultsHanhViChongDoi);
        int roiLoanHanhVi = calculateScore(resultsRoiLoanHanhVi);
        int gayHan = calculateScore(resultsGayHan);
        return new ResultPsychological(loAu, tramCam, stress, khoTapTrung, tangDong, giaoTiepXaHoi, hocTap, dinhHuongNgheNghiep, quanHeChaMe, quanHeThayCo, quanHeBanBe, hanhViChongDoi, roiLoanHanhVi, gayHan);
    }
}
