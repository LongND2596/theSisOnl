package longnd.thesis.data.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import longnd.thesis.data.model.Result;
import longnd.thesis.data.model.ResultNeo;
import longnd.thesis.data.model.ResultPsychological;
import longnd.thesis.data.model.ResultRiasec;
import longnd.thesis.utils.Define;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ResultDao {
    @Insert
    Single<Long> insertResultNeo(ResultNeo resultNeo);

    @Insert
    Single<Long> insertResultRiasec(ResultRiasec resultRiasec);

    @Insert
    Single<Long> insertResultPsycho(ResultPsychological resultPsychological);

    @Insert
    Single<Long> insertResult(Result result);

    @Query("SELECT * FROM " + Define.Result.TABLE_NAME + " WHERE " + Define.Result.ID_CUSTOMER + " = :idCustomer ORDER BY " + Define.Result.TIME + " DESC")
    Flowable<List<Result>> getListResult(int idCustomer);

    @Query("SELECT * FROM " + Define.ResultNeo.TABLE_NAME + " WHERE " + Define.Result.ID + " = :id")
    Flowable<ResultNeo> getNeo(int id);

    @Query("SELECT * FROM " + Define.ResultRiasec.TABLE_NAME + " WHERE " + Define.Result.ID + " = :id")
    Flowable<ResultRiasec> getRiasec(int id);

    @Query("SELECT * FROM " + Define.ResultPsycho.TABLE_NAME + " WHERE " + Define.ResultPsycho.ID + " = :id")
    Flowable<ResultPsychological> getPsycho(int id);
}
