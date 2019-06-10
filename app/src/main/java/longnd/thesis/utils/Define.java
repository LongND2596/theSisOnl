package longnd.thesis.utils;

public class Define {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "psy.db";
    public static final long CLICK_TIME_INTERVAL = 300L;
    public static final String TYPE_QUESTION = "type_question";
    public static final int DEFAULT_VALUE = -1;
    public static final String VERSION_ONL = "TheSISOnline";
    public static final String VERSION_OFF = "TheSISOffline";

    public class ResponseStatus {
        public static final int LOADING = 1;
        public static final int SUCCESS = 2;
        public static final int ERROR = 0;
    }

    public class QuestionsType {
        public static final int NEO = 0;
        public static final int RIASEC = 1;
        public static final int PSY = 2;
    }

    public class Customer {
        public static final String TABLE_NAME = "customer";
        public static final String ID = "id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String SCHOLL = "school";
        public static final String SPECIALIZED = "specialized";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String BIRTHDATE = "birthdate";
        public static final String GENDER = "gender";
        public static final String IMAGE = "image";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
        public static final String DEL_FLG = "del_flg";
        public static final String PASS = "pass";
    }

    public class Question {
        public static final String TABLE_NAME = "question";
        public static final String ID = "id";
        public static final String TYPE = "type";
        public static final String NUMBER_ID = "numberId";
        public static final String CONTENT = "content";
        public static final String IS_REVERSE = "reverse";
        public static final String KIND = "kind";
        public static final int TYPE_NEO = 1;
        public static final int TYPE_RIASEC = 2;
        public static final int TYPE_PSY_POCHOLIGICAL = 3;
    }

    public static class SharedPref {
        public static final String VALUE_DEFAULT = "none";
        public static final String KEY_IS_FIRST = "is_first_lauch_app";
        public static final String KEY_SAVE_QUESTION = "save_question";
        public static final String KEY_NEXT_TAP = "next_tap";
        public static final String KEY_TOKEN = "ass_token";
        public static final String KEY_SELECT_SYNC = "sync";
        public static final String VALUE_DEFAULT_SELECT_SYNC = "none";
    }

    public class Result {
        public static final String TABLE_NAME = "result";
        public static final String ID = "id";
        public static final String ID_CUSTOMER = "id_customer";
        public static final String ID_MULTIPLE_CHOICE = "id_multiple_choice";
        public static final String TYPE = "type";
        public static final String TIME = "time";
    }

    public class ResultNeo {
        public static final String TABLE_NAME = "result_neo";
        public static final String ID = "id";
        public static final String NEUROTICISM = "neuroticism"; // nhiễu tâm
        public static final String EXTRAVERSION = "extraversion"; // hướng ngoại
        public static final String OPENNESS = "openness"; // cởi mở
        public static final String AGREEABLENESS = "agreeableness"; // dễ chấp nhận
        public static final String CONSCIENTIOUSNESS = "conscientiousness"; // tận tâm
    }

    public class ResultRiasec {
        public static final String TABLE_NAME = "result_riasec";
        public static final String ID = "id";
        public static final String RULE = "rule"; // quy tắc
        public static final String SOCIETY = "society"; // xã hội
        public static final String DISCOVER = "discover"; // khám phá
        public static final String REALITY = "reality"; // thực tế
        public static final String ART = "art"; // nghệ thuật
        public static final String CONVINCE = "convince"; // thuyết phục
    }
}
