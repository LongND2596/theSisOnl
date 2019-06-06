package longnd.thesis.data.repository;

import longnd.thesis.data.AbstractAppDatabase;
import longnd.thesis.data.model.Question;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import java.util.List;

public class QuestionRepository {
    private AbstractAppDatabase abstractAppDatabase;

    @Inject
    public QuestionRepository(AbstractAppDatabase abstractAppDatabase) {
        this.abstractAppDatabase = abstractAppDatabase;
    }

    public Completable insertListQuestionss(List<Question> questions) {
        return abstractAppDatabase.questionDao()
                .insertListQuestion(questions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Question>> getQuestionsByType(int type) {
        return abstractAppDatabase.questionDao()
                .getQuestionsByType(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
