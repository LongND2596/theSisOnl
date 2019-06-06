package longnd.thesis.utils;

import android.content.Context;

import longnd.thesis.R;

public class EvaluateUtils {
    /**
     * used to evaluate personality scales for men
     * type: 0 - Thấp, 1 - Trung bình, 2 - Cao
     *
     * @return
     */
    public static int[] evaluateNeoMale(int[] results) {
        int[] sResults = new int[5];
        // a
        if (results[0] < 18.1) {
            sResults[0] = 0;
        } else if (results[0] < 35.4) {
            sResults[0] = 1;
        } else {
            sResults[0] = 2;
        }
        // c
        if (results[1] < 20.3) {
            sResults[1] = 0;
        } else if (results[1] < 33.7) {
            sResults[1] = 1;
        } else {
            sResults[1] = 2;
        }
        // o
        if (results[2] < 23.5) {
            sResults[2] = 0;
        } else if (results[2] < 33.7) {
            sResults[2] = 1;
        } else {
            sResults[2] = 2;
        }
        // n
        if (results[3] < 19.8) {
            sResults[3] = 0;
        } else if (results[3] < 34.8) {
            sResults[3] = 1;
        } else {
            sResults[3] = 2;
        }
        // e
        if (results[4] < 23.5) {
            sResults[4] = 0;
        } else if (results[4] < 37.8) {
            sResults[4] = 1;
        } else {
            sResults[4] = 2;
        }
        return sResults;
    }

    /**
     * used to assess personality scales for women
     * type: 0 - Thấp, 1 - Trung bình, 2 - Cao
     *
     * @return
     */
    public static int[] evaluateNeoFemale(int[] results) {
        int[] sResults = new int[5];
        // a
        if (results[0] < 19.1) {
            sResults[0] = 0;
        } else if (results[0] < 35.4) {
            sResults[0] = 1;
        } else {
            sResults[0] = 2;
        }
        // c
        if (results[1] < 22.4) {
            sResults[1] = 0;
        } else if (results[1] < 32.5) {
            sResults[1] = 1;
        } else {
            sResults[1] = 2;
        }
        // o
        if (results[2] < 22.4) {
            sResults[2] = 0;
        } else if (results[2] < 33.8) {
            sResults[2] = 1;
        } else {
            sResults[2] = 2;
        }
        // n
        if (results[3] < 22.6) {
            sResults[3] = 0;
        } else if (results[3] < 38.2) {
            sResults[3] = 1;
        } else {
            sResults[3] = 2;
        }
        // e
        if (results[4] < 25.7) {
            sResults[4] = 0;
        } else if (results[4] < 39.8) {
            sResults[4] = 1;
        } else {
            sResults[4] = 2;
        }
        return sResults;
    }

    /**
     * used to convert numbers from numbers to corresponding words
     *
     * @param number
     * @return
     */
    public static String wordResults(int number) {
        switch (number) {
            case 0:
                return "Thấp";
            case 1:
                return "Trung bình";
            case 2:
                return "Cao";
            default:
                return "Thấp";
        }
    }

    public static String resultRiasecTitle(Context context, int number) {
        return context.getResources().getStringArray(R.array.results_riasec)[number];
    }

    public static String resultRiasecDetail(Context context, int number) {
        return context.getResources().getStringArray(R.array.results_riasec_array)[number];
    }
}
