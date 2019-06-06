package longnd.thesis.ui.test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import longnd.thesis.data.base.ListResponse;
import longnd.thesis.data.model.Question;
import longnd.thesis.data.repository.QuestionRepository;
import longnd.thesis.network.entity.QuestionShow;
import longnd.thesis.utils.DataUtils;
import longnd.thesis.utils.Define;
import io.reactivex.disposables.CompositeDisposable;

public class TestViewModel extends ViewModel {
    private int numberBack;
    private int type;

    private CompositeDisposable compositeDisposable;
    private QuestionRepository questionRepository;
    private MutableLiveData<ListResponse<Question>> questions;

    private List<Question> listQuestion;
    private List<QuestionShow> questionShows;

    private List<Boolean> reverseScore;
    private List<Integer> scoreNeo;
    private List<Double> scoreRiasec;

    @Inject
    TestViewModel(QuestionRepository questionRepository) {
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

    public void setQuestionShow(List<QuestionShow> questionShows) {
        this.questionShows = questionShows;
    }

    public List<QuestionShow> getQuestionShows() {
        return questionShows;
    }

    public List<Boolean> getReverseScore() {
        return reverseScore;
    }

    public void setReverseScore(List<Boolean> reverseScore) {
        this.reverseScore = reverseScore;
    }

    public List<Integer> getScoreNeo() {
        return scoreNeo;
    }

    public void setScoreNeo(List<Integer> scoreNeo) {
        this.scoreNeo = scoreNeo;
    }

    public List<Double> getScoreRiasec() {
        return scoreRiasec;
    }

    public void setScoreRiasec(List<Double> scoreRiasec) {
        this.scoreRiasec = scoreRiasec;
    }
}
