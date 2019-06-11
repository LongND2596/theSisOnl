package longnd.thesis.utils;

import android.os.Environment;

import java.io.File;

public class Fields {
    public static final int NO_DATA = 0;
    public static final int HAVE_DATA = 1;
    public static final String FONT_NABILA = "Nabila.ttf";
    public static final String FONT_TIMES = "times.ttf";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASS = "pass";
    public static final String DEFAULT_VALUE = "admin";
    public static final int ON_BACK = 0;
    public static final int DONT_BACK = 1;
    public static final int PICK_IMAGE = 100;
    public static final int TAKE_IMAGE = 101;
    public static final String KEY_TYPE = "type";

    public static final int MAX_LOGIN_ERROR = 5;
    public static final long TIME_LOCK_LOGIN = 5 * 60 * 1000;

    public static final String FORMAT_IMAGE = ".jpg";
    public static final String ROOT_FOLDER = Environment
            .getExternalStorageDirectory() + File.separator + "psychological";

    public static final String NEO_A = "neo_a";
    public static final String NEO_C = "neo_c";
    public static final String NEO_N = "neo_n";
    public static final String NEO_O = "neo_o";
    public static final String NEO_E = "neo_e";

    public static final String RIASEC_RULE = "rule"; // quy tắc
    public static final String RIASEC_SOCIETY = "society"; // xã hội
    public static final String RIASEC_DISCOVER = "discover"; // khám phá
    public static final String RIASEC_REALITY = "reality"; // thực tế
    public static final String RIASEC_ART = "art"; // nghệ thuật
    public static final String RIASEC_CONVINCE = "convince"; // thuyết phục
    public static final String KEY_VALUE = "value_result";
    public static final String PATH_AVATAR = "https://danhgiatamly.edu.vn/";
}
