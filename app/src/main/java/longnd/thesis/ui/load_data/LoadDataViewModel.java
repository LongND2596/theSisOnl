package longnd.thesis.ui.load_data;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ObjectResponse;
import longnd.thesis.data.model.Question;
import longnd.thesis.data.repository.QuestionRepository;
import longnd.thesis.utils.Define;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoadDataViewModel extends ViewModel {
    private static final String TAG = "LoadDataViewModel";
    private CompositeDisposable compositeDisposable;
    private QuestionRepository repository;

    private MutableLiveData<ObjectResponse<Boolean>> isInsertListQuestionSuccess;

    @Inject
    LoadDataViewModel(QuestionRepository questionRepository) {
        this.repository = questionRepository;
        compositeDisposable = new CompositeDisposable();
        isInsertListQuestionSuccess = new MutableLiveData<>();
    }

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

    public List<Question> readDataPsy(Context context, int type) {
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
                    int kind = Integer.parseInt(data.substring(data.indexOf("@") + 1, data.lastIndexOf("@")).trim().replace(" ", ""));
                    String content = data.substring(data.lastIndexOf("@") + 2);
                    Question question = new Question(type, number, content, 0, kind);
                    questions.add(question);
                }
                in.close();
            }
        } catch (IOException e) {
            Log.d(TAG, "readData: " + e);
        }
        Log.d(TAG, "readDataPsy: " + questions.get(0).getNumberId() + " " + questions.get(0).getType() + " "
                + questions.get(0).getKind());
        return questions;
    }

    public void saveData(Context context, int type) {
        List<Question> questions;
        if (type == Define.Question.TYPE_PSY_POCHOLIGICAL) {
            questions = readDataPsy(context, type);
        } else if (type == Define.Question.TYPE_NEO) {
            questions = readDataNeo(context, type);
        } else {
            questions = readDataRiasec(context, type);
        }
        if (questions.size() != 0) {
            compositeDisposable.add(
                    repository.insertListQuestionss(questions)
                            .doOnSubscribe(dispose -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().loading()))
                            .subscribe(() -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().success(true))
                                    , throwable -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().error(throwable)))
            );
        }
    }

    public void saveData(Context context) {
        List<Question> questions;
        questions = readDataPsy(context, Define.Question.TYPE_PSY_POCHOLIGICAL);
        for (Question question : readDataNeo(context, Define.Question.TYPE_NEO)) {
            questions.add(question);
        }
        for (Question question : readDataRiasec(context, Define.Question.TYPE_RIASEC)) {
            questions.add(question);
        }
        if (questions.size() != 0) {
            compositeDisposable.add(
                    repository.insertListQuestionss(questions)
                            .doOnSubscribe(dispose -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().loading()))
                            .subscribe(() -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().success(true))
                                    , throwable -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().error(throwable)))
            );
        }
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsInsertListQuestionSuccess() {
        return isInsertListQuestionSuccess;
    }

    public void setIsInsertListQuestionSuccess(ObjectResponse<Boolean> value) {
        this.isInsertListQuestionSuccess.setValue(value);
    }
}
