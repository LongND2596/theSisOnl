package longnd.thesis.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import longnd.thesis.data.converter.DateConverter;
import longnd.thesis.data.dao.CustomerDao;
import longnd.thesis.data.dao.QuestionDao;
import longnd.thesis.data.dao.ResultDao;
import longnd.thesis.data.model.Customer;
import longnd.thesis.data.model.Question;
import longnd.thesis.data.model.Result;
import longnd.thesis.data.model.ResultNeo;
import longnd.thesis.data.model.ResultRiasec;
import longnd.thesis.utils.Define;

@Database(entities = {
        Customer.class, Question.class, ResultNeo.class,
        Result.class, ResultRiasec.class
}, version = Define.DATABASE_VERSION, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AbstractAppDatabase extends RoomDatabase {
    public abstract CustomerDao customerDao();

    public abstract QuestionDao questionDao();

    public abstract ResultDao resultDao();

    private static AbstractAppDatabase INSTANCE;

    public static AbstractAppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AbstractAppDatabase.class, Define.DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
