package longnd.thesis.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import longnd.thesis.data.model.Question;
import longnd.thesis.utils.Define;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert()
    Completable insertListQuestion(List<Question> questions);

    @Query("SELECT * FROM " + Define.Question.TABLE_NAME)
    Flowable<List<Question>> getQuestions();

    @Query("SELECT * FROM " + Define.Question.TABLE_NAME + " WHERE " + Define.Question.TYPE + " = :type ORDER BY " + Define.Question.NUMBER_ID + " ASC")
    Flowable<List<Question>> getQuestionsByType(int type);
}