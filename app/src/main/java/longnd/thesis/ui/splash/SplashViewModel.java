package longnd.thesis.ui.splash;

import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Question;
import longnd.thesis.data.repository.QuestionRepository;
import longnd.thesis.network.APIService;
import longnd.thesis.network.ApiClient;
import longnd.thesis.network.pojo.login.LoginResponse;
import longnd.thesis.utils.DataUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import longnd.thesis.utils.Define;

public class SplashViewModel extends ViewModel {
    private static final String TAG = "SplashViewModel";
    private CompositeDisposable compositeDisposable;
    private APIService mAPIService;
    private QuestionRepository questionRepository;

    private MutableLiveData<ObjectResponse<Boolean>> isGetDataProfileSuccess;


    // offline
    private MutableLiveData<ObjectResponse<Boolean>> isInsertListQuestionSuccess;
    private MutableLiveData<ListResponse<Question>> listQuestions;

    @Inject
    SplashViewModel(QuestionRepository questionRepository) {
        compositeDisposable = new CompositeDisposable();
        this.questionRepository = questionRepository;
        isGetDataProfileSuccess = new MutableLiveData<>();
        mAPIService = ApiClient.getAPIService();

        isInsertListQuestionSuccess = new MutableLiveData<>();
        listQuestions = new MutableLiveData<>();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    // region -> Online

    public void getProfileByToken(String token) {
        Disposable disposable = mAPIService.getProfileByToken(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);

        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {
        isGetDataProfileSuccess.setValue(new ObjectResponse<Boolean>().success(false));
    }

    private void handleResponse(LoginResponse loginResponse) {
        DataUtils.getInstance().setUser(loginResponse.getData().getUser());
        DataUtils.getInstance().setProfile(loginResponse.getData().getProfile());
        isGetDataProfileSuccess.setValue(new ObjectResponse<Boolean>().success(true));
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsGetDataProfileSuccess() {
        return isGetDataProfileSuccess;
    }

    public void setIsGetDataProfileSuccess(ObjectResponse<Boolean> value) {
        isGetDataProfileSuccess.setValue(value);
    }

    // endregion

    // region -> Offline

    /**
     * Read data Neo question set from txt
     *
     * @param context
     * @param type
     * @return
     */
    public List<Question> readDataNeo(Context context, int type) {
        String data;
        List<Question> questions = new ArrayList<>();
        int[] reverse = new int[]{2, 3, 4, 7, 8, 9, 12, 14, 17, 22, 23, 27, 32, 37, 38, 42, 43, 47, 48, 49, 50, 52, 53, 54, 57};
        try {
            InputStream in = context.getAssets().open("data/" + type + ".txt");
            InputStreamReader inreader = new InputStreamReader(in);
            BufferedReader bufreader = new BufferedReader(inreader);
            if (in != null) {
                while ((data = bufreader.readLine()) != null) {
                    String sNumber = data.substring(0, data.indexOf("@")).trim().replace(" ", "");
                    int number = Integer.parseInt(sNumber);
                    int isReverse = 0;
                    for (int i = 0; i < reverse.length; i++) {
                        if (number == reverse[i]) {
                            isReverse = 1;
                        }
                    }
                    Question question = new Question(type, number, data.substring(data.indexOf("@") + 2, data.length()).trim(), isReverse, 0);
                    questions.add(question);
                }
                in.close();
            }
        } catch (IOException e) {
            Log.d(TAG, "readData: " + e);
        }
        return questions;
    }

    /**
     * Read data Riasec question set from txt
     *
     * @param context
     * @param type
     * @return
     */
    public List<Question> readDataRiasec(Context context, int type) {
        String data;
        List<Question> questions = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("data/" + type + ".txt");
            InputStreamReader inreader = new InputStreamReader(in);
            BufferedReader bufreader = new BufferedReader(inreader);
            if (in != null) {
                while ((data = bufreader.readLine()) != null) {
                    String sNumber = data.substring(0, data.indexOf("@")).trim().replace(" ", "");
                    int number = Integer.parseInt(sNumber);
                    Question question = new Question(type, number, data.substring(data.indexOf("@") + 2, data.length()).trim(), 0, 0);
                    questions.add(question);
                }
                in.close();
            }
        } catch (IOException e) {
            Log.d(TAG, "readData: " + e);
        }
        return questions;
    }

    /**
     * Read data Psy question set from txt
     *
     * @param context
     * @param type
     * @return
     */
    public List<Question> readDataPsy(Context context, int type) {
        String data;
        List<Question> questions = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("data/" + type + ".txt");
            InputStreamReader inreader = new InputStreamReader(in);
            BufferedReader bufreader = new BufferedReader(inreader);
            while ((data = bufreader.readLine()) != null) {
                int kind = Integer.parseInt(data.substring(0, data.indexOf("@")));
                int number = Integer.parseInt(data.substring(data.indexOf("@") + 1, data.lastIndexOf("@")).trim().replace(" ", "")) + 1;
                String content = data.substring(data.lastIndexOf("@") + 2);
                Question question = new Question(type, number, content, 0, kind);
                questions.add(question);
            }
            in.close();
        } catch (IOException e) {
            Log.d(TAG, "readData: " + e);
        }
        Log.d(TAG, "readDataPsy: " + questions.get(0).getNumberId() + " " + questions.get(0).getType() + " "
                + questions.get(0).getKind());
        return questions;
    }

    /**
     * Insert list question in database
     */
    public void saveDataInDB() {
        List<Question> questions;
        questions = DataUtils.getInstance().getPsychological();
        questions.addAll(DataUtils.getInstance().getNeos());
        questions.addAll(DataUtils.getInstance().getRiasec());
        if (questions.size() != 0) {
            compositeDisposable.add(
                    questionRepository.insertListQuestionss(questions)
                            .doOnSubscribe(dispose -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().loading()))
                            .subscribe(() -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().success(true))
                                    , throwable -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().error(throwable)))
            );
        }
    }

    /**
     * Select list question in database
     */
    public void loadQuestionDataInDB() {
        compositeDisposable.add(
                questionRepository.getQuestions()
                        .doOnSubscribe(dispose -> listQuestions.setValue(new ListResponse<Question>().loading()))
                        .subscribe(data -> listQuestions.setValue(new ListResponse<Question>().success(data))
                                , throwable -> listQuestions.setValue(new ListResponse<Question>().error(throwable)))
        );
    }

    /**
     * Scattered according to each type of question
     *
     * @param data
     */
    public void classifyTypeQuestion(List<Question> data) {
        if (DataUtils.getInstance().getNeos() != null) return;

        List<Question> neo = new ArrayList<>();
        List<Question> riasec = new ArrayList<>();
        List<Question> psy = new ArrayList<>();
        for (Question q : data) {
            if (q.getType() == Define.Question.TYPE_NEO) {
                neo.add(q);
            } else if (q.getType() == Define.Question.TYPE_RIASEC) {
                riasec.add(q);
            } else {
                psy.add(q);
            }
        }

        DataUtils.getInstance().setNeos(neo);
        DataUtils.getInstance().setRiasec(riasec);
        DataUtils.getInstance().setPsychological(psy);
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsInsertListQuestionSuccess() {
        return isInsertListQuestionSuccess;
    }

    public void setIsInsertListQuestionSuccess(ObjectResponse<Boolean> value) {
        this.isInsertListQuestionSuccess.setValue(value);
    }

    public MutableLiveData<ListResponse<Question>> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(ListResponse<Question> value) {
        this.listQuestions.setValue(value);
    }

    // endregion
}
