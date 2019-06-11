package longnd.thesis.offline.test;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.model.Question;
import longnd.thesis.data.repository.QuestionRepository;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;

public class TestOffViewModel extends ViewModel {
    private int numberBack;
    private int type;

    private CompositeDisposable compositeDisposable;
    private QuestionRepository questionRepository;
    private MutableLiveData<ListResponse<Question>> questions;

    private List<Question> listQuestion;

    @Inject
    TestOffViewModel(QuestionRepository questionRepository) {
        compositeDisposable = new CompositeDisposable();
        this.questionRepository = questionRepository;

        questions = new MutableLiveData<>();
        listQuestion = new ArrayList<>();
    }

    public void getDataQuestions() {
        compositeDisposable.add(
                questionRepository.getQuestionsByType(type)
                        .doOnSubscribe(dispose ->
                                questions.setValue(new ListResponse<Question>().loading())
                        )
                        .subscribe(respone -> questions.setValue(new ListResponse<Question>().success(respone))
                                , throwable -> questions.setValue(new ListResponse<Question>().error(throwable)))
        );
    }

    public MutableLiveData<ListResponse<Question>> getObserveQuestions() {
        return questions;
    }

    public void setObserveQuestions(ListResponse<Question> questions) {
        this.questions.setValue(questions);
    }

    private String detail;

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public int getNumberBack() {
        return numberBack;
    }

    public void setNumberBack(int numberBack) {
        this.numberBack = numberBack;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public List<Question> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion() {
        switch (type) {
            case Define.Question.TYPE_NEO:
                listQuestion = DataUtils.getInstance().getNeos();
                break;
            case Define.Question.TYPE_RIASEC:
                listQuestion = DataUtils.getInstance().getRiasec();
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                listQuestion = DataUtils.getInstance().getPsychological();
                break;
        }
    }
}
